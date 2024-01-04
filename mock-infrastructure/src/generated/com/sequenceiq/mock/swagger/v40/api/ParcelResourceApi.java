/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.16).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.sequenceiq.mock.swagger.v40.api;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sequenceiq.mock.swagger.model.ApiCommand;
import com.sequenceiq.mock.swagger.model.ApiParcel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-11-16T21:48:33.802+01:00")

@Api(value = "ParcelResource", description = "the ParcelResource API")
@RequestMapping(value = "/{mockUuid}/api/v40")
public interface ParcelResourceApi {

    Logger log = LoggerFactory.getLogger(ParcelResourceApi.class);

    default Optional<ObjectMapper> getObjectMapper() {
        return Optional.empty();
    }

    default Optional<HttpServletRequest> getRequest() {
        return Optional.empty();
    }

    default Optional<String> getAcceptHeader() {
        return getRequest().map(r -> r.getHeader("Accept"));
    }

    @ApiOperation(value = "A synchronous command that activates the parcel on the cluster.", nickname = "activateCommand", notes = "A synchronous command that activates the parcel on the cluster. <p> Since it is synchronous, the result is known immediately upon return.", response = ApiCommand.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "ParcelResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Success", response = ApiCommand.class) })
    @RequestMapping(value = "/clusters/{clusterName}/parcels/products/{product}/versions/{version}/commands/activate",
        produces = { "application/json" },
        method = RequestMethod.POST)
    default ResponseEntity<ApiCommand> activateCommand(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "",required=true) @PathVariable("clusterName") String clusterName,@ApiParam(value = "the product",required=true) @PathVariable("product") String product,@ApiParam(value = "the version",required=true) @PathVariable("version") String version) {
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
            log.warn("ObjectMapper or HttpServletRequest not configured in default ParcelResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "A synchronous command that cancels the parcel distribution.", nickname = "cancelDistributionCommand", notes = "A synchronous command that cancels the parcel distribution. <p> Since it is synchronous, the result is known immediately upon return.", response = ApiCommand.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "ParcelResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Success", response = ApiCommand.class) })
    @RequestMapping(value = "/clusters/{clusterName}/parcels/products/{product}/versions/{version}/commands/cancelDistribution",
        produces = { "application/json" },
        method = RequestMethod.POST)
    default ResponseEntity<ApiCommand> cancelDistributionCommand(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "",required=true) @PathVariable("clusterName") String clusterName,@ApiParam(value = "the product",required=true) @PathVariable("product") String product,@ApiParam(value = "the version",required=true) @PathVariable("version") String version) {
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
            log.warn("ObjectMapper or HttpServletRequest not configured in default ParcelResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "A synchronous command that cancels the parcel download.", nickname = "cancelDownloadCommand", notes = "A synchronous command that cancels the parcel download. <p> Since it is synchronous, the result is known immediately upon return.", response = ApiCommand.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "ParcelResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Success", response = ApiCommand.class) })
    @RequestMapping(value = "/clusters/{clusterName}/parcels/products/{product}/versions/{version}/commands/cancelDownload",
        produces = { "application/json" },
        method = RequestMethod.POST)
    default ResponseEntity<ApiCommand> cancelDownloadCommand(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "",required=true) @PathVariable("clusterName") String clusterName,@ApiParam(value = "the product",required=true) @PathVariable("product") String product,@ApiParam(value = "the version",required=true) @PathVariable("version") String version) {
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
            log.warn("ObjectMapper or HttpServletRequest not configured in default ParcelResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "A synchronous command that deactivates the parcel on the cluster.", nickname = "deactivateCommand", notes = "A synchronous command that deactivates the parcel on the cluster. <p> Since it is synchronous, the result is known immediately upon return.", response = ApiCommand.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "ParcelResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Success", response = ApiCommand.class) })
    @RequestMapping(value = "/clusters/{clusterName}/parcels/products/{product}/versions/{version}/commands/deactivate",
        produces = { "application/json" },
        method = RequestMethod.POST)
    default ResponseEntity<ApiCommand> deactivateCommand(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "",required=true) @PathVariable("clusterName") String clusterName,@ApiParam(value = "the product",required=true) @PathVariable("product") String product,@ApiParam(value = "the version",required=true) @PathVariable("version") String version) {
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
            log.warn("ObjectMapper or HttpServletRequest not configured in default ParcelResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Retrieves detailed information about a parcel.", nickname = "readParcel", notes = "Retrieves detailed information about a parcel.", response = ApiParcel.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "ParcelResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = ApiParcel.class) })
    @RequestMapping(value = "/clusters/{clusterName}/parcels/products/{product}/versions/{version}",
        produces = { "application/json" },
        method = RequestMethod.GET)
    default ResponseEntity<ApiParcel> readParcel(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "",required=true) @PathVariable("clusterName") String clusterName,@ApiParam(value = "the product",required=true) @PathVariable("product") String product,@ApiParam(value = "the version",required=true) @PathVariable("version") String version) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"product\" : \"...\",  \"version\" : \"...\",  \"stage\" : \"...\",  \"state\" : {    \"progress\" : 12345,    \"totalProgress\" : 12345,    \"count\" : 12345,    \"totalCount\" : 12345,    \"errors\" : [ \"...\", \"...\" ],    \"warnings\" : [ \"...\", \"...\" ]  },  \"clusterRef\" : {    \"clusterName\" : \"...\",    \"displayName\" : \"...\"  },  \"displayName\" : \"...\",  \"description\" : \"...\"}", ApiParcel.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default ParcelResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "A synchronous command that removes the downloaded parcel.", nickname = "removeDownloadCommand", notes = "A synchronous command that removes the downloaded parcel. <p> Since it is synchronous, the result is known immediately upon return.", response = ApiCommand.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "ParcelResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Success", response = ApiCommand.class) })
    @RequestMapping(value = "/clusters/{clusterName}/parcels/products/{product}/versions/{version}/commands/removeDownload",
        produces = { "application/json" },
        method = RequestMethod.POST)
    default ResponseEntity<ApiCommand> removeDownloadCommand(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "",required=true) @PathVariable("clusterName") String clusterName,@ApiParam(value = "the product",required=true) @PathVariable("product") String product,@ApiParam(value = "the version",required=true) @PathVariable("version") String version) {
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
            log.warn("ObjectMapper or HttpServletRequest not configured in default ParcelResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "A synchronous command that starts the distribution of the parcel to the cluster.", nickname = "startDistributionCommand", notes = "A synchronous command that starts the distribution of the parcel to the cluster. <p> Since it is synchronous, the result is known immediately upon return. In order to see the progress of the distribution, a call to ParcelResource#readParcel() needs to be made.", response = ApiCommand.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "ParcelResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Success", response = ApiCommand.class) })
    @RequestMapping(value = "/clusters/{clusterName}/parcels/products/{product}/versions/{version}/commands/startDistribution",
        produces = { "application/json" },
        method = RequestMethod.POST)
    default ResponseEntity<ApiCommand> startDistributionCommand(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "",required=true) @PathVariable("clusterName") String clusterName,@ApiParam(value = "the product",required=true) @PathVariable("product") String product,@ApiParam(value = "the version",required=true) @PathVariable("version") String version) {
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
            log.warn("ObjectMapper or HttpServletRequest not configured in default ParcelResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "A synchronous command that starts the parcel download.", nickname = "startDownloadCommand", notes = "A synchronous command that starts the parcel download. <p> Since it is synchronous, the result is known immediately upon return. In order to see the progress of the download, a call to ParcelResource#readParcel() needs to be made.", response = ApiCommand.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "ParcelResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Success", response = ApiCommand.class) })
    @RequestMapping(value = "/clusters/{clusterName}/parcels/products/{product}/versions/{version}/commands/startDownload",
        produces = { "application/json" },
        method = RequestMethod.POST)
    default ResponseEntity<ApiCommand> startDownloadCommand(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "",required=true) @PathVariable("clusterName") String clusterName,@ApiParam(value = "the product",required=true) @PathVariable("product") String product,@ApiParam(value = "the version",required=true) @PathVariable("version") String version) {
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
            log.warn("ObjectMapper or HttpServletRequest not configured in default ParcelResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "A synchronous command that removes the distribution from the hosts in the cluster.", nickname = "startRemovalOfDistributionCommand", notes = "A synchronous command that removes the distribution from the hosts in the cluster. <p> Since it is synchronous, the result is known immediately upon return. In order to see the progress of the removal, a call to ParcelResource#readParcel() needs to be made.", response = ApiCommand.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "ParcelResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Success", response = ApiCommand.class) })
    @RequestMapping(value = "/clusters/{clusterName}/parcels/products/{product}/versions/{version}/commands/startRemovalOfDistribution",
        produces = { "application/json" },
        method = RequestMethod.POST)
    default ResponseEntity<ApiCommand> startRemovalOfDistributionCommand(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "",required=true) @PathVariable("clusterName") String clusterName,@ApiParam(value = "the product",required=true) @PathVariable("product") String product,@ApiParam(value = "the version",required=true) @PathVariable("version") String version) {
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
            log.warn("ObjectMapper or HttpServletRequest not configured in default ParcelResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
