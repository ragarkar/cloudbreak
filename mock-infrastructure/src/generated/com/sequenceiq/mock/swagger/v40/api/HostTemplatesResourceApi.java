/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.16).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.sequenceiq.mock.swagger.v40.api;

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
import com.sequenceiq.mock.swagger.model.ApiCommand;
import com.sequenceiq.mock.swagger.model.ApiHostRefList;
import com.sequenceiq.mock.swagger.model.ApiHostTemplate;
import com.sequenceiq.mock.swagger.model.ApiHostTemplateList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-11-16T21:48:33.802+01:00")

@Api(value = "HostTemplatesResource", description = "the HostTemplatesResource API")
@RequestMapping(value = "/{mockUuid}/api/v40")
public interface HostTemplatesResourceApi {

    Logger log = LoggerFactory.getLogger(HostTemplatesResourceApi.class);

    default Optional<ObjectMapper> getObjectMapper() {
        return Optional.empty();
    }

    default Optional<HttpServletRequest> getRequest() {
        return Optional.empty();
    }

    default Optional<String> getAcceptHeader() {
        return getRequest().map(r -> r.getHeader("Accept"));
    }

    @ApiOperation(value = "Applies a host template to a collection of hosts.", nickname = "applyHostTemplate", notes = "Applies a host template to a collection of hosts. This will create a role for each role config group on each of the hosts. <p> The provided hosts must not have any existing roles on them and if the cluster is not using parcels, the hosts must have a CDH version matching that of the cluster version. <p> Available since API v3.", response = ApiCommand.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "HostTemplatesResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Success", response = ApiCommand.class) })
    @RequestMapping(value = "/clusters/{clusterName}/hostTemplates/{hostTemplateName}/commands/applyHostTemplate",
        produces = { "application/json" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    default ResponseEntity<ApiCommand> applyHostTemplate(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "",required=true) @PathVariable("clusterName") String clusterName,@ApiParam(value = "Host template to apply.",required=true) @PathVariable("hostTemplateName") String hostTemplateName,@ApiParam(value = "Whether to start the newly created roles or not.") @Valid @RequestParam(value = "startRoles", required = false) Boolean startRoles,@ApiParam(value = "List of hosts to apply the host template to."  )  @Valid @RequestBody ApiHostRefList body) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"id\" : 12345,  \"name\" : \"...\",  \"startTime\" : \"...\",  \"endTime\" : \"...\",  \"active\" : true,  \"success\" : true,  \"resultMessage\" : \"...\",  \"resultDataUrl\" : \"...\",  \"clusterRef\" : {    \"clusterName\" : \"...\",    \"displayName\" : \"...\"  },  \"serviceRef\" : {    \"peerName\" : \"...\",    \"clusterName\" : \"...\",    \"serviceName\" : \"...\",    \"serviceDisplayName\" : \"...\",    \"serviceType\" : \"...\"  },  \"roleRef\" : {    \"clusterName\" : \"...\",    \"serviceName\" : \"...\",    \"roleName\" : \"...\"  },  \"hostRef\" : {    \"hostId\" : \"...\",    \"hostname\" : \"...\"  },  \"parent\" : {    \"id\" : 12345,    \"name\" : \"...\",    \"startTime\" : \"...\",    \"endTime\" : \"...\",    \"active\" : true,    \"success\" : true,    \"resultMessage\" : \"...\",    \"resultDataUrl\" : \"...\",    \"clusterRef\" : {      \"clusterName\" : \"...\",      \"displayName\" : \"...\"    },    \"serviceRef\" : {      \"peerName\" : \"...\",      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"serviceDisplayName\" : \"...\",      \"serviceType\" : \"...\"    },    \"roleRef\" : {      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"roleName\" : \"...\"    },    \"hostRef\" : {      \"hostId\" : \"...\",      \"hostname\" : \"...\"    },    \"parent\" : { },    \"children\" : {      \"items\" : [ { }, { } ]    },    \"canRetry\" : true  },  \"children\" : {    \"items\" : [ {      \"id\" : 12345,      \"name\" : \"...\",      \"startTime\" : \"...\",      \"endTime\" : \"...\",      \"active\" : true,      \"success\" : true,      \"resultMessage\" : \"...\",      \"resultDataUrl\" : \"...\",      \"clusterRef\" : { },      \"serviceRef\" : { },      \"roleRef\" : { },      \"hostRef\" : { },      \"parent\" : { },      \"children\" : { },      \"canRetry\" : true    }, {      \"id\" : 12345,      \"name\" : \"...\",      \"startTime\" : \"...\",      \"endTime\" : \"...\",      \"active\" : true,      \"success\" : true,      \"resultMessage\" : \"...\",      \"resultDataUrl\" : \"...\",      \"clusterRef\" : { },      \"serviceRef\" : { },      \"roleRef\" : { },      \"hostRef\" : { },      \"parent\" : { },      \"children\" : { },      \"canRetry\" : true    } ]  },  \"canRetry\" : true}", ApiCommand.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default HostTemplatesResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Creates new host templates.", nickname = "createHostTemplates", notes = "Creates new host templates. <p> Host template names must be unique across clusters. <p> Available since API v3.", response = ApiHostTemplateList.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "HostTemplatesResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Success", response = ApiHostTemplateList.class) })
    @RequestMapping(value = "/clusters/{clusterName}/hostTemplates",
        produces = { "application/json" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    default ResponseEntity<ApiHostTemplateList> createHostTemplates(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "",required=true) @PathVariable("clusterName") String clusterName,@ApiParam(value = "The list of host templates to create."  )  @Valid @RequestBody ApiHostTemplateList body) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"items\" : [ {    \"name\" : \"...\",    \"clusterRef\" : {      \"clusterName\" : \"...\",      \"displayName\" : \"...\"    },    \"roleConfigGroupRefs\" : [ {      \"roleConfigGroupName\" : \"...\"    }, {      \"roleConfigGroupName\" : \"...\"    } ]  }, {    \"name\" : \"...\",    \"clusterRef\" : {      \"clusterName\" : \"...\",      \"displayName\" : \"...\"    },    \"roleConfigGroupRefs\" : [ {      \"roleConfigGroupName\" : \"...\"    }, {      \"roleConfigGroupName\" : \"...\"    } ]  } ]}", ApiHostTemplateList.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default HostTemplatesResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Deletes a host template.", nickname = "deleteHostTemplate", notes = "Deletes a host template. <p> Available since API v3.", response = ApiHostTemplate.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "HostTemplatesResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Success", response = ApiHostTemplate.class) })
    @RequestMapping(value = "/clusters/{clusterName}/hostTemplates/{hostTemplateName}",
        produces = { "application/json" },
        method = RequestMethod.DELETE)
    default ResponseEntity<ApiHostTemplate> deleteHostTemplate(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "",required=true) @PathVariable("clusterName") String clusterName,@ApiParam(value = "Host template to delete.",required=true) @PathVariable("hostTemplateName") String hostTemplateName) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"name\" : \"...\",  \"clusterRef\" : {    \"clusterName\" : \"...\",    \"displayName\" : \"...\"  },  \"roleConfigGroupRefs\" : [ {    \"roleConfigGroupName\" : \"...\"  }, {    \"roleConfigGroupName\" : \"...\"  } ]}", ApiHostTemplate.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default HostTemplatesResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Retrieves information about a host template.", nickname = "readHostTemplate", notes = "Retrieves information about a host template. <p> Available since API v3.", response = ApiHostTemplate.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "HostTemplatesResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = ApiHostTemplate.class) })
    @RequestMapping(value = "/clusters/{clusterName}/hostTemplates/{hostTemplateName}",
        produces = { "application/json" },
        method = RequestMethod.GET)
    default ResponseEntity<ApiHostTemplate> readHostTemplate(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "",required=true) @PathVariable("clusterName") String clusterName,@ApiParam(value = "",required=true) @PathVariable("hostTemplateName") String hostTemplateName) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"name\" : \"...\",  \"clusterRef\" : {    \"clusterName\" : \"...\",    \"displayName\" : \"...\"  },  \"roleConfigGroupRefs\" : [ {    \"roleConfigGroupName\" : \"...\"  }, {    \"roleConfigGroupName\" : \"...\"  } ]}", ApiHostTemplate.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default HostTemplatesResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Lists all host templates in a cluster.", nickname = "readHostTemplates", notes = "Lists all host templates in a cluster. <p> Available since API v3.", response = ApiHostTemplateList.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "HostTemplatesResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = ApiHostTemplateList.class) })
    @RequestMapping(value = "/clusters/{clusterName}/hostTemplates",
        produces = { "application/json" },
        method = RequestMethod.GET)
    default ResponseEntity<ApiHostTemplateList> readHostTemplates(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "",required=true) @PathVariable("clusterName") String clusterName) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"items\" : [ {    \"name\" : \"...\",    \"clusterRef\" : {      \"clusterName\" : \"...\",      \"displayName\" : \"...\"    },    \"roleConfigGroupRefs\" : [ {      \"roleConfigGroupName\" : \"...\"    }, {      \"roleConfigGroupName\" : \"...\"    } ]  }, {    \"name\" : \"...\",    \"clusterRef\" : {      \"clusterName\" : \"...\",      \"displayName\" : \"...\"    },    \"roleConfigGroupRefs\" : [ {      \"roleConfigGroupName\" : \"...\"    }, {      \"roleConfigGroupName\" : \"...\"    } ]  } ]}", ApiHostTemplateList.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default HostTemplatesResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Updates an existing host template.", nickname = "updateHostTemplate", notes = "Updates an existing host template. <p> Can be used to update the role config groups in a host template or rename it. <p> Available since API v3.", response = ApiHostTemplate.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "HostTemplatesResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Success", response = ApiHostTemplate.class) })
    @RequestMapping(value = "/clusters/{clusterName}/hostTemplates/{hostTemplateName}",
        produces = { "application/json" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    default ResponseEntity<ApiHostTemplate> updateHostTemplate(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "",required=true) @PathVariable("clusterName") String clusterName,@ApiParam(value = "Host template with updated fields.",required=true) @PathVariable("hostTemplateName") String hostTemplateName,@ApiParam(value = ""  )  @Valid @RequestBody ApiHostTemplate body) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"name\" : \"...\",  \"clusterRef\" : {    \"clusterName\" : \"...\",    \"displayName\" : \"...\"  },  \"roleConfigGroupRefs\" : [ {    \"roleConfigGroupName\" : \"...\"  }, {    \"roleConfigGroupName\" : \"...\"  } ]}", ApiHostTemplate.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default HostTemplatesResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
