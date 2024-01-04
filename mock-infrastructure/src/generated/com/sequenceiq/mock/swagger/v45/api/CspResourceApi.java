/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.16).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.sequenceiq.mock.swagger.v45.api;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sequenceiq.mock.swagger.model.ApiCspArguments;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-12-10T21:24:30.629+01:00")

@Api(value = "CspResource", description = "the CspResource API")
@RequestMapping(value = "/{mockUuid}/api/v45")
public interface CspResourceApi {

    Logger log = LoggerFactory.getLogger(CspResourceApi.class);

    default Optional<ObjectMapper> getObjectMapper() {
        return Optional.empty();
    }

    default Optional<HttpServletRequest> getRequest() {
        return Optional.empty();
    }

    default Optional<String> getAcceptHeader() {
        return getRequest().map(r -> r.getHeader("Accept"));
    }

    @ApiOperation(value = "Upload CA certificates, client certificates and client key for Credential Storage Provider.", nickname = "uploadCspCert", notes = "Upload CA certificates, client certificates and client key for Credential Storage Provider", response = ApiCspArguments.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "CspResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Arguments to upload CA certificates, client certificates and client key for CSP", response = ApiCspArguments.class) })
    @RequestMapping(value = "/csp/uploadCspCert",
        produces = { "application/json" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    default ResponseEntity<ApiCspArguments> uploadCspCert(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "Arguments for the CSP"  )  @Valid @RequestBody ApiCspArguments body) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"caCertContent\" : \"...\",  \"certContent\" : \"...\",  \"keyContent\" : \"...\"}", ApiCspArguments.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default CspResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
