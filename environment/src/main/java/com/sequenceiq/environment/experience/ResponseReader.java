package com.sequenceiq.environment.experience;

import java.util.Optional;

import jakarta.ws.rs.core.Response;

public interface ResponseReader {

    <T> Optional<T> read(String target, Response response, Class<T> expectedType);

}
