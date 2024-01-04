/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.16).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.sequenceiq.mock.swagger.v45.api;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sequenceiq.mock.swagger.model.ApiProcess;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-12-10T21:24:30.629+01:00")

@Api(value = "ProcessResource", description = "the ProcessResource API")
@RequestMapping(value = "/{mockUuid}/api/v45")
public interface ProcessResourceApi {

    Logger log = LoggerFactory.getLogger(ProcessResourceApi.class);

    default Optional<ObjectMapper> getObjectMapper() {
        return Optional.empty();
    }

    default Optional<HttpServletRequest> getRequest() {
        return Optional.empty();
    }

    default Optional<String> getAcceptHeader() {
        return getRequest().map(r -> r.getHeader("Accept"));
    }

    @ApiOperation(value = "Returns the contents of the specified config file.", nickname = "getConfigFile", notes = "Returns the contents of the specified config file. A multi-level file name (e.g. hadoop-conf/hdfs-site.xml) is acceptable here.", response = Resource.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "ProcessResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Contents of the specified config file", response = Resource.class) })
    @RequestMapping(value = "/clusters/{clusterName}/services/{serviceName}/roles/{roleName}/process/configFiles/{configFileName}",
        produces = { "application/octet-stream" },
        method = RequestMethod.GET)
    default ResponseEntity<Resource> getConfigFile(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "",required=true) @PathVariable("clusterName") String clusterName,@ApiParam(value = "Name of the config file to get.",required=true) @PathVariable("configFileName") String configFileName,@ApiParam(value = "",required=true) @PathVariable("roleName") String roleName,@ApiParam(value = "",required=true) @PathVariable("serviceName") String serviceName) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("", Resource.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type ", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default ProcessResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "", nickname = "getProcess", notes = "", response = ApiProcess.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "ProcessResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The process associated with this resource.", response = ApiProcess.class) })
    @RequestMapping(value = "/clusters/{clusterName}/services/{serviceName}/roles/{roleName}/process",
        produces = { "application/json" },
        method = RequestMethod.GET)
    default ResponseEntity<ApiProcess> getProcess(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "",required=true) @PathVariable("clusterName") String clusterName,@ApiParam(value = "",required=true) @PathVariable("roleName") String roleName,@ApiParam(value = "",required=true) @PathVariable("serviceName") String serviceName) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"configFiles\" : [ \"...\", \"...\" ]}", ApiProcess.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default ProcessResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
