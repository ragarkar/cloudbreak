package com.sequenceiq.periscope.converter;

import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Inject;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.service.secret.service.SecretService;
import com.sequenceiq.periscope.api.model.AutoscaleClusterResponse;
import com.sequenceiq.periscope.api.model.LoadAlertResponse;
import com.sequenceiq.periscope.api.model.ScalingConfigurationRequest;
import com.sequenceiq.periscope.api.model.TimeAlertResponse;
import com.sequenceiq.periscope.domain.Cluster;

@Component
public class ClusterConverter extends AbstractConverter<AutoscaleClusterResponse, Cluster> {

    @Inject
    private TimeAlertResponseConverter timeAlertResponseConverter;

    @Inject
    private LoadAlertResponseConverter loadAlertResponseConverter;

    @Inject
    private SecretService secretService;

    @Override
    public AutoscaleClusterResponse convert(Cluster source) {
        AutoscaleClusterResponse json = new AutoscaleClusterResponse(
                source.getHost(),
                source.getPort(),
                secretService.get(source.getClusterManagerUser()),
                source.getStackCrn(),
                source.isAutoscalingEnabled(),
                source.getState().name());

        if (!source.getTimeAlerts().isEmpty()) {
            List<TimeAlertResponse> timeAlertRequests =
                    timeAlertResponseConverter.convertAllToJson(new ArrayList<>(source.getTimeAlerts()));
            json.setTimeAlerts(timeAlertRequests);
        }

        if (!source.getLoadAlerts().isEmpty()) {
            List<LoadAlertResponse> loadAlertRequests =
                    loadAlertResponseConverter.convertAllToJson(new ArrayList<>(source.getLoadAlerts()));
            json.setLoadAlerts(loadAlertRequests);
        }
        ScalingConfigurationRequest scalingConfig = new ScalingConfigurationRequest(source.getMinSize(), source.getMaxSize(), source.getCoolDown());
        json.setScalingConfiguration(scalingConfig);

        return json;
    }

}
