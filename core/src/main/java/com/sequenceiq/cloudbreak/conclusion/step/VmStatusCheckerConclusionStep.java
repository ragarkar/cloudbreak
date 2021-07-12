package com.sequenceiq.cloudbreak.conclusion.step;

import static com.sequenceiq.cloudbreak.cloud.model.HostName.hostName;
import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.cloud.model.CloudInstance;
import com.sequenceiq.cloudbreak.cloud.model.CloudVmInstanceStatus;
import com.sequenceiq.cloudbreak.cloud.model.HostName;
import com.sequenceiq.cloudbreak.cluster.api.ClusterApi;
import com.sequenceiq.cloudbreak.cluster.status.ExtendedHostStatuses;
import com.sequenceiq.cloudbreak.common.type.ClusterManagerState;
import com.sequenceiq.cloudbreak.common.type.ClusterManagerState.ClusterManagerStatus;
import com.sequenceiq.cloudbreak.converter.spi.InstanceMetaDataToCloudInstanceConverter;
import com.sequenceiq.cloudbreak.domain.stack.Stack;
import com.sequenceiq.cloudbreak.domain.stack.instance.InstanceMetaData;
import com.sequenceiq.cloudbreak.service.cluster.ClusterApiConnectors;
import com.sequenceiq.cloudbreak.service.stack.InstanceMetaDataService;
import com.sequenceiq.cloudbreak.service.stack.StackInstanceStatusChecker;
import com.sequenceiq.cloudbreak.service.stack.StackService;
import com.sequenceiq.cloudbreak.service.stack.flow.InstanceSyncState;

@Component
public class VmStatusCheckerConclusionStep extends ConclusionStep {

    private static final Logger LOGGER = LoggerFactory.getLogger(VmStatusCheckerConclusionStep.class);

    @Inject
    private StackService stackService;

    @Inject
    private ClusterApiConnectors clusterApiConnectors;

    @Inject
    private InstanceMetaDataService instanceMetaDataService;

    @Inject
    private InstanceMetaDataToCloudInstanceConverter cloudInstanceConverter;

    @Inject
    private StackInstanceStatusChecker stackInstanceStatusChecker;

    @Override
    public Conclusion check(Long resourceId) {
        Stack stack = stackService.getById(resourceId);
        ClusterApi connector = clusterApiConnectors.getConnector(stack);
        Set<InstanceMetaData> runningInstances = instanceMetaDataService.findNotTerminatedForStack(stack.getId());
        if (isClusterManagerRunning(stack, connector)) {
            return checkCMForInstanceStatuses(connector, runningInstances);
        } else {
            return checkProviderForInstanceStatuses(stack, runningInstances);
        }
    }

    private Conclusion checkCMForInstanceStatuses(ClusterApi connector, Set<InstanceMetaData> runningInstances) {
        ExtendedHostStatuses extendedHostStatuses = connector.clusterStatusService().getExtendedHostStatuses();
        Map<HostName, ClusterManagerState> hostStatuses = extendedHostStatuses.getHostHealth();
        Map<String, String> unhealthyHosts = hostStatuses.entrySet().stream()
                .filter(hostStatus -> ClusterManagerStatus.UNHEALTHY.equals(hostStatus.getValue().getClusterManagerStatus()))
                .collect(Collectors.toMap(hostStatus -> hostStatus.getKey().value(), hostStatus -> hostStatus.getValue().getStatusReason()));
        Set<String> noReportHosts = runningInstances.stream()
                .filter(i -> !hostStatuses.containsKey(hostName(i.getDiscoveryFQDN())))
                .map(InstanceMetaData::getDiscoveryFQDN)
                .collect(toSet());

        if (!unhealthyHosts.isEmpty() || !noReportHosts.isEmpty()) {
            String conclusion = String.format("Unhealthy and/or unknown VMs found based on CM status. Unhealthy VMs: %s, unknown VMs: %s. " +
                    "Please check the instances on your cloud provider for further details.", unhealthyHosts, noReportHosts);
            String details = String.format("Unhealthy and/or unknown VMs found based on CM status. Unhealthy VMs: %s, unknown VMs: %s",
                    unhealthyHosts, noReportHosts);
            LOGGER.warn(details);
            return failed(conclusion, details);
        } else {
            return succeeded();
        }
    }

    private Conclusion checkProviderForInstanceStatuses(Stack stack, Set<InstanceMetaData> runningInstances) {
        List<CloudInstance> cloudInstances = cloudInstanceConverter.convert(runningInstances, stack.getEnvironmentCrn(), stack.getStackAuthentication());
        List<CloudVmInstanceStatus> instanceStatuses = stackInstanceStatusChecker.queryInstanceStatuses(stack, cloudInstances);
        Map<String, InstanceSyncState> instanceSyncStates = instanceStatuses.stream()
                .collect(Collectors.toMap(i -> i.getCloudInstance().getInstanceId(), i -> InstanceSyncState.getInstanceSyncState(i.getStatus())));
        Set<String> notRunningInstances = runningInstances.stream()
                .filter(i -> !InstanceSyncState.RUNNING.equals(instanceSyncStates.getOrDefault(i.getInstanceId(), InstanceSyncState.UNKNOWN)))
                .map(i -> i.getDiscoveryFQDN())
                .collect(toSet());

        if (!notRunningInstances.isEmpty()) {
            String conclusion = String.format("Not running VMs found based on provider status: %s. " +
                    "Please check the instances on your cloud provider for further details.", notRunningInstances);
            String details = String.format("Not running VMs found based on provider status: %s", notRunningInstances);
            LOGGER.warn(details);
            return failed(conclusion, details);
        } else {
            return succeeded();
        }
    }

    private boolean isClusterManagerRunning(Stack stack, ClusterApi connector) {
        return !stack.isStopped()
                && !stack.isStackInDeletionOrFailedPhase()
                && isCMRunning(connector);
    }

    private boolean isCMRunning(ClusterApi connector) {
        return connector.clusterStatusService().isClusterManagerRunningQuickCheck();
    }
}