package com.sequenceiq.mock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sequenceiq.mock.clouderamanager.DataProviderService;
import com.sequenceiq.mock.model.YarnScalingServiceV1Request;
import com.sequenceiq.mock.model.YarnScalingServiceV1Response;
import com.sequenceiq.mock.swagger.model.ApiHostList;

@Controller
@RequestMapping(value = "/{mockUuid}/resourcemanager/v1/cluster")
public class YarnController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YarnController.class);

    private static final int UPSCALE_NODE_COUNT = 100;

    private Map<String, Boolean> stackToOperationMap = new HashMap<>();

    @Inject
    private DataProviderService dataProviderService;

    @PostMapping("/scaling")
    public ResponseEntity<YarnScalingServiceV1Response> getYarnMetrics(@PathVariable("mockUuid") String mockUuid,
            @RequestBody YarnScalingServiceV1Request yarnScalingServiceV1Request) {
        LOGGER.info("getYarnMetrics called with {} and {}", mockUuid, yarnScalingServiceV1Request);
        Boolean upscale = stackToOperationMap.getOrDefault(mockUuid, Boolean.FALSE);
        YarnScalingServiceV1Response yarnScalingServiceV1Response;
        if (upscale) {
            yarnScalingServiceV1Response = populateUpscale();
        } else {
            yarnScalingServiceV1Response = populateDownscale(mockUuid);
        }
        stackToOperationMap.put(mockUuid, !upscale);
        return new ResponseEntity<>(yarnScalingServiceV1Response, HttpStatus.OK);
    }

    private YarnScalingServiceV1Response populateUpscale() {
        YarnScalingServiceV1Response yarnScalingServiceV1Response = new YarnScalingServiceV1Response();
        YarnScalingServiceV1Response.NewNodeManagerCandidates newNodeManagerCandidates = new YarnScalingServiceV1Response.NewNodeManagerCandidates();
        YarnScalingServiceV1Response.NewNodeManagerCandidates.Candidate candidate = new YarnScalingServiceV1Response.NewNodeManagerCandidates.Candidate();
        candidate.setModelName("compute");
        candidate.setCount(UPSCALE_NODE_COUNT);
        newNodeManagerCandidates.setCandidates(List.of(candidate));
        yarnScalingServiceV1Response.setNewNMCandidates(newNodeManagerCandidates);
        return yarnScalingServiceV1Response;
    }

    private YarnScalingServiceV1Response populateDownscale(String stackCrn) {
        YarnScalingServiceV1Response yarnScalingServiceV1Response = new YarnScalingServiceV1Response();
        ApiHostList apiHostList = dataProviderService.readHosts(stackCrn);
        List<YarnScalingServiceV1Response.DecommissionCandidate> decommissionCandidates = apiHostList.getItems().stream().map(apiHost -> {
            YarnScalingServiceV1Response.DecommissionCandidate decommissionCandidate = new YarnScalingServiceV1Response.DecommissionCandidate();
            decommissionCandidate.setNodeId(apiHost.getHostname());
            return decommissionCandidate;
            }).collect(Collectors.toList());
        yarnScalingServiceV1Response.setDecommissionCandidates(Map.of(YarnScalingServiceV1Response.YARN_RESPONSE_DECOMMISSION_CANDIDATES_KEY,
                decommissionCandidates));
        return yarnScalingServiceV1Response;
    }
}
