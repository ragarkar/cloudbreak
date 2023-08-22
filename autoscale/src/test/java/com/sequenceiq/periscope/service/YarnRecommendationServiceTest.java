package com.sequenceiq.periscope.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sequenceiq.periscope.config.YarnConfig;
import com.sequenceiq.periscope.domain.Cluster;
import com.sequenceiq.periscope.domain.ClusterPertain;
import com.sequenceiq.periscope.domain.LoadAlert;
import com.sequenceiq.periscope.domain.ScalingPolicy;
import com.sequenceiq.periscope.model.yarn.YarnScalingServiceV1Response;
import com.sequenceiq.periscope.monitor.client.YarnMetricsClient;

@ExtendWith(MockitoExtension.class)
class YarnRecommendationServiceTest {

    private static final String TEST_FQDN_BASE = "testFqdn";

    private static final String TEST_HOSTGROUP_COMPUTE = "compute";

    private static final String TEST_HOSTGROUP_WORKER = "worker";

    private static final String TEST_CLUSTERCRN = "testCrn";

    private static final String TEST_MACHINE_USER_CRN = "testMachineUserCrn";

    private static final String TEST_ACCOUNT_ID = "accid";

    private static final Long TEST_WORKSPACE_ID = 100L;

    private static final Long TEST_USER_ID = 100L;

    private static final String TEST_USER_CRN = String.format("crn:cdp:iam:us-west-1:%s:user:mockuser@cloudera.com", TEST_ACCOUNT_ID);

    private static final String TEST_CLUSTER_CRN = "crn:cdp:datahub:us-west-1:autoscale:cluster:f7563fc1-e8ff-486a-9260-4e54ccabbaa0";

    private static final Integer CONNECTION_TIME = 5000;

    private static final Integer READ_TIME = 10000;

    private String tenant = "testTenant";

    @InjectMocks
    private YarnRecommendationService underTest;

    @Mock
    private YarnMetricsClient yarnMetricsClient;

    @Mock
    private ClusterService clusterService;

    @Mock
    private YarnConfig yarnConfig;

    @Mock
    private AutoscaleRestRequestThreadLocalService restRequestThreadLocalService;

    @Test
    void testGetRecommendationFromYarnWithOnlyComputeNodes() throws Exception {
        Cluster cluster = getACluster();
        YarnScalingServiceV1Response yarnScalingServiceV1Response = getMockYarnScalingResponse(3);
        doReturn(CONNECTION_TIME).when(yarnConfig).getConnectionTimeOutMs();
        doReturn(READ_TIME).when(yarnConfig).getReadTimeOutMs();
        when(yarnMetricsClient.getYarnMetricsForCluster(cluster, TEST_HOSTGROUP_COMPUTE, TEST_USER_CRN, Optional.of(5000), Optional.of(10000)))
                .thenReturn(yarnScalingServiceV1Response);
        when(restRequestThreadLocalService.getCloudbreakTenant()).thenReturn(tenant);

        when(clusterService.findOneByStackCrnAndTenant(TEST_CLUSTER_CRN, tenant)).thenReturn(Optional.of(cluster));
        List<String> trial = underTest.getRecommendationFromYarn(TEST_CLUSTER_CRN);
        List<String> expected = List.of("testFqdncompute1:8042", "testFqdncompute2:8042", "testFqdncompute3:8042");
        assertThat(trial).hasSameElementsAs(expected);
    }

    @Test
    void testGetRecommendationFromYarnWithComputeAndWorkerNodes() throws Exception {
        Cluster cluster = getACluster();
        YarnScalingServiceV1Response yarnScalingServiceV1Response = getMockYarnScalingResponse(3, 3);
        doReturn(CONNECTION_TIME).when(yarnConfig).getConnectionTimeOutMs();
        doReturn(READ_TIME).when(yarnConfig).getReadTimeOutMs();
        when(yarnMetricsClient.getYarnMetricsForCluster(cluster, TEST_HOSTGROUP_COMPUTE, TEST_USER_CRN, Optional.of(5000), Optional.of(10000)))
                .thenReturn(yarnScalingServiceV1Response);
        when(restRequestThreadLocalService.getCloudbreakTenant()).thenReturn(tenant);

        when(clusterService.findOneByStackCrnAndTenant(TEST_CLUSTER_CRN, tenant)).thenReturn(Optional.of(cluster));
        List<String> trial = underTest.getRecommendationFromYarn(TEST_CLUSTER_CRN);
        List<String> expected = List.of("testFqdncompute1:8042", "testFqdncompute2:8042", "testFqdncompute3:8042");
        assertThat(trial).hasSameElementsAs(expected);
    }

    private YarnScalingServiceV1Response getMockYarnScalingResponse(int yarnGivenDecommissionCount) {
        YarnScalingServiceV1Response yarnScalingReponse = new YarnScalingServiceV1Response();
        List decommissionCandidates = new ArrayList();
        for (int i = 1; i <= yarnGivenDecommissionCount; i++) {
            YarnScalingServiceV1Response.DecommissionCandidate decommissionCandidate = new YarnScalingServiceV1Response.DecommissionCandidate();
            decommissionCandidate.setAmCount(2);
            decommissionCandidate.setNodeId(TEST_FQDN_BASE + TEST_HOSTGROUP_COMPUTE + i + ":8042");
            decommissionCandidates.add(decommissionCandidate);
        }
        yarnScalingReponse.setDecommissionCandidates(Map.of("candidates", decommissionCandidates));
        return yarnScalingReponse;
    }

    private YarnScalingServiceV1Response getMockYarnScalingResponse(int yarnGivenDecommissionCountForCompute, int yarnGivenDecommissionCountForWorker) {
        YarnScalingServiceV1Response yarnScalingReponse = new YarnScalingServiceV1Response();
        List decommissionCandidates = new ArrayList();
        for (int i = 1; i <= yarnGivenDecommissionCountForCompute; i++) {
            YarnScalingServiceV1Response.DecommissionCandidate decommissionCandidate = new YarnScalingServiceV1Response.DecommissionCandidate();
            decommissionCandidate.setAmCount(2);
            decommissionCandidate.setNodeId(TEST_FQDN_BASE + TEST_HOSTGROUP_COMPUTE + i + ":8042");
            decommissionCandidates.add(decommissionCandidate);
        }
        for (int i = 1; i <= yarnGivenDecommissionCountForWorker; i++) {
            YarnScalingServiceV1Response.DecommissionCandidate decommissionCandidate = new YarnScalingServiceV1Response.DecommissionCandidate();
            decommissionCandidate.setAmCount(2);
            decommissionCandidate.setNodeId(TEST_FQDN_BASE + TEST_HOSTGROUP_WORKER + i + ":8042");
            decommissionCandidates.add(decommissionCandidate);
        }
        yarnScalingReponse.setDecommissionCandidates(Map.of("candidates", decommissionCandidates));
        return yarnScalingReponse;
    }

    private Cluster getACluster() {
        Cluster cluster = new Cluster();
        cluster.setStackCrn(TEST_CLUSTERCRN);
        cluster.setMachineUserCrn(TEST_MACHINE_USER_CRN);

        ClusterPertain clusterPertain = new ClusterPertain();
        clusterPertain.setTenant(TEST_ACCOUNT_ID);
        clusterPertain.setWorkspaceId(TEST_WORKSPACE_ID);
        clusterPertain.setUserId(TEST_USER_ID.toString());
        clusterPertain.setUserCrn(TEST_USER_CRN);
        cluster.setClusterPertain(clusterPertain);

        ScalingPolicy scalingPolicy = new ScalingPolicy();
        scalingPolicy.setHostGroup(TEST_HOSTGROUP_COMPUTE);
        LoadAlert loadAlert = new LoadAlert();
        loadAlert.setScalingPolicy(scalingPolicy);
        cluster.setLoadAlerts(Set.of(loadAlert));
        return cluster;
    }

}