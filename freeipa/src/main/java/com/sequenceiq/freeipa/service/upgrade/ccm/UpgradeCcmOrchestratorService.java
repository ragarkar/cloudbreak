package com.sequenceiq.freeipa.service.upgrade.ccm;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sequenceiq.cloudbreak.orchestrator.exception.CloudbreakOrchestratorException;
import com.sequenceiq.cloudbreak.orchestrator.host.HostOrchestrator;
import com.sequenceiq.cloudbreak.orchestrator.host.OrchestratorStateParams;
import com.sequenceiq.freeipa.service.orchestrator.OrchestratorStateParamsProvider;

@Service
public class UpgradeCcmOrchestratorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpgradeCcmOrchestratorService.class);

    private static final String UPGRADE_CCM_STATE = "upgradeccm";

    private static final String NGINX_STATE = "nginx";

    private static final String DISABLE_MINA_STATE = "upgradeccm/disable-ccmv1";

    private static final String FINALIZE = "upgradeccm/finalize";

    @Inject
    private OrchestratorStateParamsProvider orchestratorStateParamsProvider;

    @Inject
    private HostOrchestrator hostOrchestrator;

    public void applyUpgradeState(Long stackId) throws CloudbreakOrchestratorException {
        OrchestratorStateParams stateParams = orchestratorStateParamsProvider.createStateParams(stackId, UPGRADE_CCM_STATE);
        LOGGER.debug("Calling applyUpgradeState with state params '{}'", stateParams);
        hostOrchestrator.runOrchestratorState(stateParams);
    }

    public void reconfigureNginx(Long stackId) throws CloudbreakOrchestratorException {
        OrchestratorStateParams stateParams = orchestratorStateParamsProvider.createStateParams(stackId, NGINX_STATE);
        LOGGER.debug("Calling reconfigureNginx with state params '{}'", stateParams);
        hostOrchestrator.runOrchestratorState(stateParams);
    }

    public void finalizeConfiguration(Long stackId) throws CloudbreakOrchestratorException {
        OrchestratorStateParams stateParams = orchestratorStateParamsProvider.createStateParams(stackId, FINALIZE);
        LOGGER.debug("Calling finalize with state params '{}'", stateParams);
        hostOrchestrator.runOrchestratorState(stateParams);
    }

    public void disableMina(Long stackId) throws CloudbreakOrchestratorException {
        OrchestratorStateParams stateParams = orchestratorStateParamsProvider.createStateParams(stackId, DISABLE_MINA_STATE);
        LOGGER.debug("Calling disableMina with state params '{}'", stateParams);
        hostOrchestrator.runOrchestratorState(stateParams);
    }
}
