/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.16).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.sequenceiq.mock.swagger.v43.api;

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
import com.sequenceiq.mock.swagger.model.ApiBatchRequest;
import com.sequenceiq.mock.swagger.model.ApiBatchResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-04-23T12:05:48.864+02:00")

@Api(value = "BatchResource", description = "the BatchResource API")
@RequestMapping(value = "/{mockUuid}/api/v43")
public interface BatchResourceApi {

    Logger log = LoggerFactory.getLogger(BatchResourceApi.class);

    default Optional<ObjectMapper> getObjectMapper() {
        return Optional.empty();
    }

    default Optional<HttpServletRequest> getRequest() {
        return Optional.empty();
    }

    default Optional<String> getAcceptHeader() {
        return getRequest().map(r -> r.getHeader("Accept"));
    }

    @ApiOperation(value = "Executes a batch of API requests in one database transaction.", nickname = "execute", notes = "Executes a batch of API requests in one database transaction. If any request fails, execution halts and the transaction is rolled back.", response = ApiBatchResponse.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "BatchResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Batch response, containing one element for each executed request element. If a request element was skipped (i.e. a previous request element failed), it will not have a corresponding response element.", response = ApiBatchResponse.class) })
    @RequestMapping(value = "/batch",
        produces = { "application/json" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    default ResponseEntity<ApiBatchResponse> execute(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "Batch of request to execute."  )  @Valid @RequestBody ApiBatchRequest body) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"items\" : [ {    \"statusCode\" : 12345,    \"response\" : { }  }, {    \"statusCode\" : 12345,    \"response\" : { }  } ],  \"success\" : true}", ApiBatchResponse.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default BatchResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
