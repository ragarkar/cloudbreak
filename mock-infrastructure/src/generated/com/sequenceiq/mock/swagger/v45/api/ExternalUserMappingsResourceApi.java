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
import com.sequenceiq.mock.swagger.model.ApiExternalUserMapping;
import com.sequenceiq.mock.swagger.model.ApiExternalUserMappingList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-12-10T21:24:30.629+01:00")

@Api(value = "ExternalUserMappingsResource", description = "the ExternalUserMappingsResource API")
@RequestMapping(value = "/{mockUuid}/api/v45")
public interface ExternalUserMappingsResourceApi {

    Logger log = LoggerFactory.getLogger(ExternalUserMappingsResourceApi.class);

    default Optional<ObjectMapper> getObjectMapper() {
        return Optional.empty();
    }

    default Optional<HttpServletRequest> getRequest() {
        return Optional.empty();
    }

    default Optional<String> getAcceptHeader() {
        return getRequest().map(r -> r.getHeader("Accept"));
    }

    @ApiOperation(value = "Creates a list of external user mappings.", nickname = "createExternalUserMappings", notes = "Creates a list of external user mappings", response = ApiExternalUserMappingList.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "ExternalUserMappingsResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Information about created external user mappings.", response = ApiExternalUserMappingList.class) })
    @RequestMapping(value = "/externalUserMappings",
        produces = { "application/json" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    default ResponseEntity<ApiExternalUserMappingList> createExternalUserMappings(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "List of external user mappings to create."  )  @Valid @RequestBody ApiExternalUserMappingList body) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"items\" : [ {    \"name\" : \"...\",    \"type\" : \"SAML_SCRIPT\",    \"uuid\" : \"...\",    \"authRoles\" : [ {      \"displayName\" : \"...\",      \"name\" : \"...\",      \"uuid\" : \"...\"    }, {      \"displayName\" : \"...\",      \"name\" : \"...\",      \"uuid\" : \"...\"    } ]  }, {    \"name\" : \"...\",    \"type\" : \"LDAP\",    \"uuid\" : \"...\",    \"authRoles\" : [ {      \"displayName\" : \"...\",      \"name\" : \"...\",      \"uuid\" : \"...\"    }, {      \"displayName\" : \"...\",      \"name\" : \"...\",      \"uuid\" : \"...\"    } ]  } ]}", ApiExternalUserMappingList.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default ExternalUserMappingsResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Deletes an external user mapping from the system.", nickname = "deleteExternalUserMapping", notes = "Deletes an external user mapping from the system. <p/>", response = ApiExternalUserMapping.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "ExternalUserMappingsResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "The details of the deleted external user mapping role.", response = ApiExternalUserMapping.class) })
    @RequestMapping(value = "/externalUserMappings/{uuid}",
        produces = { "application/json" },
        method = RequestMethod.DELETE)
    default ResponseEntity<ApiExternalUserMapping> deleteExternalUserMapping(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "The uuid of the external user mapping to delete.",required=true) @PathVariable("uuid") String uuid) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"name\" : \"...\",  \"type\" : \"SAML_SCRIPT\",  \"uuid\" : \"...\",  \"authRoles\" : [ {    \"displayName\" : \"...\",    \"name\" : \"...\",    \"uuid\" : \"...\"  }, {    \"displayName\" : \"...\",    \"name\" : \"...\",    \"uuid\" : \"...\"  } ]}", ApiExternalUserMapping.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default ExternalUserMappingsResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Returns detailed information about an external user mapping.", nickname = "readExternalUserMapping", notes = "Returns detailed information about an external user mapping.", response = ApiExternalUserMapping.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "ExternalUserMappingsResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The external user mapping's information.", response = ApiExternalUserMapping.class) })
    @RequestMapping(value = "/externalUserMappings/{uuid}",
        produces = { "application/json" },
        method = RequestMethod.GET)
    default ResponseEntity<ApiExternalUserMapping> readExternalUserMapping(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "The external user mapping to read.",required=true) @PathVariable("uuid") String uuid) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"name\" : \"...\",  \"type\" : \"SAML_ATTRIBUTE\",  \"uuid\" : \"...\",  \"authRoles\" : [ {    \"displayName\" : \"...\",    \"name\" : \"...\",    \"uuid\" : \"...\"  }, {    \"displayName\" : \"...\",    \"name\" : \"...\",    \"uuid\" : \"...\"  } ]}", ApiExternalUserMapping.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default ExternalUserMappingsResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Returns a list of the external user mappings configured in the system.", nickname = "readExternalUserMappings", notes = "Returns a list of the external user mappings configured in the system.", response = ApiExternalUserMappingList.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "ExternalUserMappingsResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "A list of external user mappings.", response = ApiExternalUserMappingList.class) })
    @RequestMapping(value = "/externalUserMappings",
        produces = { "application/json" },
        method = RequestMethod.GET)
    default ResponseEntity<ApiExternalUserMappingList> readExternalUserMappings(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "", allowableValues = "EXPORT, EXPORT_REDACTED, FULL, FULL_WITH_HEALTH_CHECK_EXPLANATION, SUMMARY", defaultValue = "summary") @Valid @RequestParam(value = "view", required = false, defaultValue="summary") String view) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"items\" : [ {    \"name\" : \"...\",    \"type\" : \"LDAP\",    \"uuid\" : \"...\",    \"authRoles\" : [ {      \"displayName\" : \"...\",      \"name\" : \"...\",      \"uuid\" : \"...\"    }, {      \"displayName\" : \"...\",      \"name\" : \"...\",      \"uuid\" : \"...\"    } ]  }, {    \"name\" : \"...\",    \"type\" : \"LDAP\",    \"uuid\" : \"...\",    \"authRoles\" : [ {      \"displayName\" : \"...\",      \"name\" : \"...\",      \"uuid\" : \"...\"    }, {      \"displayName\" : \"...\",      \"name\" : \"...\",      \"uuid\" : \"...\"    } ]  } ]}", ApiExternalUserMappingList.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default ExternalUserMappingsResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Updates the given external user mapping's information.", nickname = "updateExternalUserMapping", notes = "Updates the given external user mapping's information.", response = ApiExternalUserMapping.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "ExternalUserMappingsResource", })
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "", response = ApiExternalUserMapping.class) })
    @RequestMapping(value = "/externalUserMappings/{uuid}",
        produces = { "application/json" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    default ResponseEntity<ApiExternalUserMapping> updateExternalUserMapping(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "Uuid of the external user mapping being updated.",required=true) @PathVariable("uuid") String uuid,@ApiParam(value = "The external user mapping information."  )  @Valid @RequestBody ApiExternalUserMapping body) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"name\" : \"...\",  \"type\" : \"SAML_ATTRIBUTE\",  \"uuid\" : \"...\",  \"authRoles\" : [ {    \"displayName\" : \"...\",    \"name\" : \"...\",    \"uuid\" : \"...\"  }, {    \"displayName\" : \"...\",    \"name\" : \"...\",    \"uuid\" : \"...\"  } ]}", ApiExternalUserMapping.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default ExternalUserMappingsResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
