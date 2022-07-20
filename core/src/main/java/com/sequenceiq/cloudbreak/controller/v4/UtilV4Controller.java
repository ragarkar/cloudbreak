package com.sequenceiq.cloudbreak.controller.v4;

import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.sequenceiq.authorization.annotation.AccountIdNotNeeded;
import com.sequenceiq.authorization.annotation.CheckPermissionByAccount;
import com.sequenceiq.authorization.annotation.DisableCheckPermissions;
import com.sequenceiq.authorization.annotation.InternalOnly;
import com.sequenceiq.authorization.resource.AuthorizationResourceAction;
import com.sequenceiq.cloudbreak.api.endpoint.v4.util.UtilV4Endpoint;
import com.sequenceiq.cloudbreak.api.endpoint.v4.util.requests.RenewCertificateV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.util.requests.RepoConfigValidationV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.util.responses.CloudStorageSupportedV4Responses;
import com.sequenceiq.cloudbreak.api.endpoint.v4.util.responses.DeploymentPreferencesV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.util.responses.RepoConfigValidationV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.util.responses.ResourceEventResponse;
import com.sequenceiq.cloudbreak.api.endpoint.v4.util.responses.SecurityRulesV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.util.responses.StackMatrixV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.util.responses.SupportedExternalDatabaseServiceEntryV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.util.responses.UsedImagesListV4Response;
import com.sequenceiq.cloudbreak.common.user.CloudbreakUser;
import com.sequenceiq.cloudbreak.converter.SupportedExternalDatabaseServiceEntryToSupportedExternalDatabaseServiceEntryResponseConverter;
import com.sequenceiq.cloudbreak.event.ResourceEvent;
import com.sequenceiq.cloudbreak.notification.NotificationSender;
import com.sequenceiq.cloudbreak.service.StackMatrixService;
import com.sequenceiq.cloudbreak.service.account.PreferencesService;
import com.sequenceiq.cloudbreak.service.cluster.RepositoryConfigValidationService;
import com.sequenceiq.cloudbreak.service.filesystem.FileSystemSupportMatrixService;
import com.sequenceiq.cloudbreak.service.image.PlatformStringTransformer;
import com.sequenceiq.cloudbreak.service.image.UsedImagesProvider;
import com.sequenceiq.cloudbreak.service.securityrule.SecurityRuleService;
import com.sequenceiq.cloudbreak.service.stack.RemoteExecutionService;
import com.sequenceiq.cloudbreak.service.stack.flow.StackOperationService;
import com.sequenceiq.cloudbreak.structuredevent.CloudbreakRestRequestThreadLocalService;
import com.sequenceiq.cloudbreak.validation.externaldatabase.SupportedDatabaseProvider;
import com.sequenceiq.common.api.command.RemoteCommandsExecutionRequest;
import com.sequenceiq.common.api.command.RemoteCommandsExecutionResponse;
import com.sequenceiq.common.api.util.versionchecker.ClientVersionUtil;
import com.sequenceiq.common.api.util.versionchecker.VersionCheckResult;

@Controller
public class UtilV4Controller extends NotificationController implements UtilV4Endpoint {

    @Inject
    private StackMatrixService stackMatrixService;

    @Inject
    private FileSystemSupportMatrixService fileSystemSupportMatrixService;

    @Inject
    private RepositoryConfigValidationService validationService;

    @Inject
    private PreferencesService preferencesService;

    @Inject
    private SecurityRuleService securityRuleService;

    @Inject
    private CloudbreakRestRequestThreadLocalService restRequestThreadLocalService;

    @Inject
    private NotificationSender notificationSender;

    @Inject
    private StackOperationService stackOperationService;

    @Inject
    private SupportedExternalDatabaseServiceEntryToSupportedExternalDatabaseServiceEntryResponseConverter supportedExternalDatabaseServiceEntryResponseConverter;

    @Inject
    private UsedImagesProvider usedImagesProvider;

    @Inject
    private PlatformStringTransformer platformStringTransformer;

    @Inject
    private RemoteExecutionService remoteExecutionService;

    @Value("${info.app.version:}")
    private String cbVersion;

    @Override
    @DisableCheckPermissions
    public VersionCheckResult checkClientVersion(String version) {
        return ClientVersionUtil.checkClientVersion(cbVersion, version);
    }

    @Override
    @DisableCheckPermissions
    public StackMatrixV4Response getStackMatrix(String imageCatalogName, String platform, boolean govCloud) throws Exception {
        return stackMatrixService.getStackMatrix(restRequestThreadLocalService.getRequestedWorkspaceId(),
                platformStringTransformer.getPlatformStringForImageCatalog(platform, govCloud), imageCatalogName);
    }

    @Override
    @DisableCheckPermissions
    public CloudStorageSupportedV4Responses getCloudStorageMatrix(String stackVersion) {
        return new CloudStorageSupportedV4Responses(fileSystemSupportMatrixService.getCloudStorageMatrix(stackVersion));
    }

    @Override
    @DisableCheckPermissions
    public RepoConfigValidationV4Response repositoryConfigValidationRequest(RepoConfigValidationV4Request repoConfigValidationV4Request) {
        return validationService.validate(repoConfigValidationV4Request);
    }

    @Override
    @DisableCheckPermissions
    public SecurityRulesV4Response getDefaultSecurityRules() {
        return securityRuleService.getDefaultSecurityRules();
    }

    @Override
    @DisableCheckPermissions
    public DeploymentPreferencesV4Response deployment() {
        DeploymentPreferencesV4Response response = new DeploymentPreferencesV4Response();
        response.setFeatureSwitchV4s(preferencesService.getFeatureSwitches());
        Set<SupportedExternalDatabaseServiceEntryV4Response> supportedExternalDatabases =
                SupportedDatabaseProvider.supportedExternalDatabases().stream()
                .map(s -> supportedExternalDatabaseServiceEntryResponseConverter.convert(s))
                .collect(Collectors.toSet());
        response.setSupportedExternalDatabases(supportedExternalDatabases);
        response.setPlatformSelectionDisabled(preferencesService.isPlatformSelectionDisabled());
        response.setPlatformEnablement(preferencesService.platformEnablement());
        response.setGovPlatformEnablement(preferencesService.govPlatformEnablement());
        return response;
    }

    @Override
    @CheckPermissionByAccount(action = AuthorizationResourceAction.POWERUSER_ONLY)
    public ResourceEventResponse postNotificationTest() {
        CloudbreakUser cloudbreakUser = restRequestThreadLocalService.getCloudbreakUser();
        notificationSender.sendTestNotification(cloudbreakUser.getUserId());
        ResourceEventResponse response = new ResourceEventResponse();
        response.setEvent(ResourceEvent.CREDENTIAL_CREATED);
        return response;
    }

    @Override
    @CheckPermissionByAccount(action = AuthorizationResourceAction.POWERUSER_ONLY)
    public Response renewCertificate(RenewCertificateV4Request renewCertificateV4Request) {
        String accountId = restRequestThreadLocalService.getAccountId();
        stackOperationService.renewCertificate(renewCertificateV4Request.getStackName(), accountId);
        return Response.ok().build();
    }

    @Override
    @InternalOnly
    @AccountIdNotNeeded
    public UsedImagesListV4Response usedImages(Integer thresholdInDays) {
        return usedImagesProvider.getUsedImages(thresholdInDays);
    }

    @Override
    @InternalOnly
    @AccountIdNotNeeded
    public RemoteCommandsExecutionResponse remoteCommandExecution(String resourceCrn, RemoteCommandsExecutionRequest request) {
        return remoteExecutionService.remoteExec(resourceCrn, request);
    }
}
