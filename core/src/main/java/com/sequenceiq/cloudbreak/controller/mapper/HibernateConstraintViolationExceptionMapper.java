package com.sequenceiq.cloudbreak.controller.mapper;

import jakarta.ws.rs.core.Response.Status;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Component;

@Component
public class HibernateConstraintViolationExceptionMapper extends SendNotificationExceptionMapper<ConstraintViolationException> {

    @Override
    protected String getErrorMessage(ConstraintViolationException exception) {
        return exception.getLocalizedMessage();
    }

    @Override
    public Status getResponseStatus(ConstraintViolationException exception) {
        return Status.BAD_REQUEST;
    }

    @Override
    public Class<ConstraintViolationException> getExceptionType() {
        return ConstraintViolationException.class;
    }
}
