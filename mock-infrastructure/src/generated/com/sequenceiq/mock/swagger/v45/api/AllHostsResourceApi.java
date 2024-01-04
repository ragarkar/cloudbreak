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
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sequenceiq.mock.swagger.model.ApiConfigList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-12-10T21:24:30.629+01:00")

@Api(value = "AllHostsResource", description = "the AllHostsResource API")
@RequestMapping(value = "/{mockUuid}/api/v45")
public interface AllHostsResourceApi {

    Logger log = LoggerFactory.getLogger(AllHostsResourceApi.class);

    default Optional<ObjectMapper> getObjectMapper() {
        return Optional.empty();
    }

    default Optional<HttpServletRequest> getRequest() {
        return Optional.empty();
    }

    default Optional<String> getAcceptHeader() {
        return getRequest().map(r -> r.getHeader("Accept"));
    }

    @ApiOperation(value = "Retrieve the default configuration for all hosts.", nickname = "readConfig", notes = "Retrieve the default configuration for all hosts. <p/> These values will apply to all hosts managed by CM unless overridden at the host level.", response = ApiConfigList.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "AllHostsResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "List of config values.", response = ApiConfigList.class) })
    @RequestMapping(value = "/cm/allHosts/config",
        produces = { "application/json" },
        method = RequestMethod.GET)
    default ResponseEntity<ApiConfigList> readConfig(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "The view of the data to materialize, either \"summary\" or \"full\".", allowableValues = "EXPORT, EXPORT_REDACTED, FULL, FULL_WITH_HEALTH_CHECK_EXPLANATION, SUMMARY", defaultValue = "summary") @Valid @RequestParam(value = "view", required = false, defaultValue="summary") String view) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"items\" : [ {    \"name\" : \"...\",    \"value\" : \"...\",    \"required\" : true,    \"default\" : \"...\",    \"displayName\" : \"...\",    \"description\" : \"...\",    \"relatedName\" : \"...\",    \"sensitive\" : true,    \"validationState\" : \"WARNING\",    \"validationMessage\" : \"...\",    \"validationWarningsSuppressed\" : true  }, {    \"name\" : \"...\",    \"value\" : \"...\",    \"required\" : true,    \"default\" : \"...\",    \"displayName\" : \"...\",    \"description\" : \"...\",    \"relatedName\" : \"...\",    \"sensitive\" : true,    \"validationState\" : \"OK\",    \"validationMessage\" : \"...\",    \"validationWarningsSuppressed\" : true  } ]}", ApiConfigList.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default AllHostsResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Update the default configuration values for all hosts.", nickname = "updateConfig", notes = "Update the default configuration values for all hosts. <p/> Note that this does not override values set at the host level. It just updates the default values that will be inherited by each host's configuration.", response = ApiConfigList.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "AllHostsResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Updated list of config values.", response = ApiConfigList.class) })
    @RequestMapping(value = "/cm/allHosts/config",
        produces = { "application/json" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    default ResponseEntity<ApiConfigList> updateConfig(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "Optional message describing the changes.") @Valid @RequestParam(value = "message", required = false) String message,@ApiParam(value = "The config values to update."  )  @Valid @RequestBody ApiConfigList body) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"items\" : [ {    \"name\" : \"...\",    \"value\" : \"...\",    \"required\" : true,    \"default\" : \"...\",    \"displayName\" : \"...\",    \"description\" : \"...\",    \"relatedName\" : \"...\",    \"sensitive\" : true,    \"validationState\" : \"WARNING\",    \"validationMessage\" : \"...\",    \"validationWarningsSuppressed\" : true  }, {    \"name\" : \"...\",    \"value\" : \"...\",    \"required\" : true,    \"default\" : \"...\",    \"displayName\" : \"...\",    \"description\" : \"...\",    \"relatedName\" : \"...\",    \"sensitive\" : true,    \"validationState\" : \"ERROR\",    \"validationMessage\" : \"...\",    \"validationWarningsSuppressed\" : true  } ]}", ApiConfigList.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default AllHostsResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
