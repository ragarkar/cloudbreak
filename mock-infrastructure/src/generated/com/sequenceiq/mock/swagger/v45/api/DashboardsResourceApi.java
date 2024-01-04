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
import com.sequenceiq.mock.swagger.model.ApiDashboard;
import com.sequenceiq.mock.swagger.model.ApiDashboardList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-12-10T21:24:30.629+01:00")

@Api(value = "DashboardsResource", description = "the DashboardsResource API")
@RequestMapping(value = "/{mockUuid}/api/v45")
public interface DashboardsResourceApi {

    Logger log = LoggerFactory.getLogger(DashboardsResourceApi.class);

    default Optional<ObjectMapper> getObjectMapper() {
        return Optional.empty();
    }

    default Optional<HttpServletRequest> getRequest() {
        return Optional.empty();
    }

    default Optional<String> getAcceptHeader() {
        return getRequest().map(r -> r.getHeader("Accept"));
    }

    @ApiOperation(value = "Creates the list of dashboards.", nickname = "createDashboards", notes = "Creates the list of dashboards. If any of the dashboards already exist this whole command will fail and no dashboards will be created. <p> Available since API v6.", response = ApiDashboardList.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "DashboardsResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "The dashboards created.", response = ApiDashboardList.class) })
    @RequestMapping(value = "/timeseries/dashboards",
        produces = { "application/json" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    default ResponseEntity<ApiDashboardList> createDashboards(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "The list of dashboards to create."  )  @Valid @RequestBody ApiDashboardList body) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"items\" : [ {    \"name\" : \"...\",    \"json\" : \"...\"  }, {    \"name\" : \"...\",    \"json\" : \"...\"  } ]}", ApiDashboardList.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default DashboardsResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Deletes a dashboard.", nickname = "deleteDashboard", notes = "Deletes a dashboard.  <p> Available since API v6.", response = ApiDashboard.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "DashboardsResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "The deleted dashboard.", response = ApiDashboard.class) })
    @RequestMapping(value = "/timeseries/dashboards/{dashboardName}",
        produces = { "application/json" },
        method = RequestMethod.DELETE)
    default ResponseEntity<ApiDashboard> deleteDashboard(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "The name of the dashboard.",required=true) @PathVariable("dashboardName") String dashboardName) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"name\" : \"...\",  \"json\" : \"...\"}", ApiDashboard.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default DashboardsResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Returns a dashboard definition for the specified name.", nickname = "getDashboard", notes = "Returns a dashboard definition for the specified name. This dashboard can be imported with the createDashboards API. <p> Available since API v6.", response = ApiDashboard.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "DashboardsResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "", response = ApiDashboard.class) })
    @RequestMapping(value = "/timeseries/dashboards/{dashboardName}",
        produces = { "application/json" },
        method = RequestMethod.GET)
    default ResponseEntity<ApiDashboard> getDashboard(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "The name of the dashboard.",required=true) @PathVariable("dashboardName") String dashboardName) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"name\" : \"...\",  \"json\" : \"...\"}", ApiDashboard.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default DashboardsResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Returns the list of all user-customized dashboards.", nickname = "getDashboards", notes = "Returns the list of all user-customized dashboards. This includes both the new dashboards created by users as well as any user customizations to built-in dashboards. <p> Available since API v6.", response = ApiDashboardList.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "DashboardsResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "", response = ApiDashboardList.class) })
    @RequestMapping(value = "/timeseries/dashboards",
        produces = { "application/json" },
        method = RequestMethod.GET)
    default ResponseEntity<ApiDashboardList> getDashboards(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"items\" : [ {    \"name\" : \"...\",    \"json\" : \"...\"  }, {    \"name\" : \"...\",    \"json\" : \"...\"  } ]}", ApiDashboardList.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default DashboardsResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
