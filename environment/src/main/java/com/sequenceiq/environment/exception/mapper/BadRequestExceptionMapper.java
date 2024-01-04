package com.sequenceiq.environment.exception.mapper;

import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.common.exception.BadRequestException;

@Provider
@Component
public class BadRequestExceptionMapper extends EnvironmentBaseExceptionMapper<BadRequestException> {

    @Override
    public Status getResponseStatus(BadRequestException exception) {
        return Status.BAD_REQUEST;
    }

    @Override
    public Class<BadRequestException> getExceptionType() {
        return BadRequestException.class;
    }

}
