package com.sequenceiq.periscope.api.endpoint.v1;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.validation.annotation.Validated;

import com.sequenceiq.periscope.api.model.DistroXAutoScaleYarnRecommendationResponse;
import com.sequenceiq.periscope.doc.ApiDescription;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Validated
@Path("/v1/yarn_recommendation")
@Tag(name = "/v1/yarn_recommendation")
public interface DistroXAutoScaleYarnRecommendationV1Endpoint {

    @GET
    @Path("cluster_crn/{clusterCrn}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = ApiDescription.ClusterOpDescription.YARN_RECOMMENDATION, description = ApiDescription.DistroXClusterNotes.NOTES,
            operationId = "getYarnRecommendation",
            responses = @ApiResponse(responseCode = "200", description = "successful operation", useReturnTypeSchema = true))
    DistroXAutoScaleYarnRecommendationResponse getYarnRecommendation(
            @PathParam("clusterCrn") @NotNull String clusterCrn) throws Exception;
}
