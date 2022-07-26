package com.sequenceiq.freeipa.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;

import com.sequenceiq.authorization.annotation.AccountIdNotNeeded;
import com.sequenceiq.authorization.annotation.CheckPermissionByAccount;
import com.sequenceiq.authorization.annotation.InternalOnly;
import com.sequenceiq.authorization.resource.AuthorizationResourceAction;
import com.sequenceiq.cloudbreak.auth.security.internal.AccountId;
import com.sequenceiq.common.api.command.RemoteCommandsExecutionRequest;
import com.sequenceiq.common.api.command.RemoteCommandsExecutionResponse;
import com.sequenceiq.freeipa.api.v1.util.UtilV1Endpoint;
import com.sequenceiq.freeipa.api.v1.util.model.UsedImagesListV1Response;
import com.sequenceiq.freeipa.service.UsedImagesProvider;
import com.sequenceiq.freeipa.service.recipe.FreeIpaRecipeService;
import com.sequenceiq.freeipa.service.stack.RemoteExecutionService;

@Controller
public class UtilV1Controller implements UtilV1Endpoint {

    @Inject
    private UsedImagesProvider usedImagesProvider;

    @Inject
    private FreeIpaRecipeService freeIpaRecipeService;

    @Inject
    private RemoteExecutionService remoteExecutionService;

    @Override
    @InternalOnly
    @AccountIdNotNeeded
    public UsedImagesListV1Response usedImages(Integer thresholdInDays) {
        return usedImagesProvider.getUsedImages(thresholdInDays);
    }

    @Override
    @InternalOnly
    public List<String> usedRecipes(@AccountId String accountId) {
        return freeIpaRecipeService.getUsedRecipeNamesForAccount(accountId);
    }

    @Override
    @CheckPermissionByAccount(action = AuthorizationResourceAction.POWERUSER_ONLY)
    public RemoteCommandsExecutionResponse remoteCommandExecution(String environmentCrn, RemoteCommandsExecutionRequest request) {
        return remoteExecutionService.remoteExec(environmentCrn, request);
    }
}
