package com.sequenceiq.cloudbreak.service;

import java.util.Optional;

import jakarta.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sequenceiq.cloudbreak.domain.stack.Stack;
import com.sequenceiq.cloudbreak.polling.SimpleStatusCheckerTask;
import com.sequenceiq.cloudbreak.service.stack.StackService;

public abstract class StackBasedStatusCheckerTask<T extends StackContext> extends SimpleStatusCheckerTask<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StackBasedStatusCheckerTask.class);

    @Inject
    private StackService stackService;

    @Override
    public boolean exitPolling(T t) {
        try {
            Optional<Stack> stack = stackService.findById(t.getStack().getId());
            return !stack.isPresent() || stack.get().isDeleteInProgress() || stack.get().isDeleteCompleted();
        } catch (Exception ex) {
            LOGGER.debug("Error occurred when check status checker exit criteria: ");
            return true;
        }
    }

}
