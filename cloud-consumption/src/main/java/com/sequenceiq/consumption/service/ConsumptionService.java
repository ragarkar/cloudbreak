package com.sequenceiq.consumption.service;

import static com.sequenceiq.cloudbreak.common.exception.NotFoundException.notFound;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sequenceiq.cloudbreak.auth.ThreadBasedUserCrnProvider;
import com.sequenceiq.cloudbreak.common.dal.repository.AccountAwareResourceRepository;
import com.sequenceiq.cloudbreak.common.event.PayloadContext;
import com.sequenceiq.cloudbreak.common.exception.NotFoundException;
import com.sequenceiq.cloudbreak.common.service.account.AbstractAccountAwareResourceService;
import com.sequenceiq.cloudbreak.quartz.model.JobResource;
import com.sequenceiq.consumption.api.v1.consumption.model.common.ConsumptionType;
import com.sequenceiq.consumption.configuration.repository.ConsumptionRepository;
import com.sequenceiq.consumption.domain.Consumption;
import com.sequenceiq.consumption.dto.ConsumptionCreationDto;
import com.sequenceiq.consumption.dto.converter.ConsumptionDtoConverter;
import com.sequenceiq.flow.core.PayloadContextProvider;
import com.sequenceiq.flow.core.ResourceIdProvider;

@Service
public class ConsumptionService extends AbstractAccountAwareResourceService<Consumption> implements ResourceIdProvider,
        PayloadContextProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumptionService.class);

    @Inject
    private ConsumptionRepository consumptionRepository;

    @Inject
    private ConsumptionDtoConverter consumptionDtoConverter;

    @Override
    public Long getResourceIdByResourceCrn(String resourceCrn) {
        return consumptionRepository.findIdByResourceCrnAndAccountId(resourceCrn, ThreadBasedUserCrnProvider.getAccountId())
                .orElseThrow(notFound("Consumption with crn:", resourceCrn));
    }

    @Override
    public Long getResourceIdByResourceName(String resourceName) {
        return consumptionRepository.findIdByNameAndAccountId(resourceName, ThreadBasedUserCrnProvider.getAccountId())
                .orElseThrow(notFound("Consumption with name:", resourceName));
    }

    @Override
    public PayloadContext getPayloadContext(Long resourceId) {
        return null;
    }

    @Override
    protected AccountAwareResourceRepository<Consumption, Long> repository() {
        return consumptionRepository;
    }

    @Override
    protected void prepareDeletion(Consumption resource) {
    }

    @Override
    protected void prepareCreation(Consumption resource) {
    }

    public Consumption findConsumptionById(Long id) {
        return consumptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Consumption with ID [%s] not found", id)));
    }

    public Optional<Consumption> findStorageConsumptionByMonitoredResourceCrnAndLocation(String monitoredResourceCrn, String storageLocation) {
        Optional<Consumption> result = consumptionRepository.findStorageConsumptionByMonitoredResourceCrnAndLocation(monitoredResourceCrn, storageLocation);
        result.ifPresentOrElse(
                consumption -> LOGGER.debug("Storage consumption with location [{}] found for resource with CRN [{}].", storageLocation, monitoredResourceCrn),
                () -> LOGGER.warn("Storage consumption with location [{}] not found for resource with CRN [{}].", storageLocation, monitoredResourceCrn));
        return result;
    }

    public Optional<Consumption> create(ConsumptionCreationDto creationDto) {
        if (!hasStorageLocationCollision(creationDto)) {
            Consumption consumption = consumptionDtoConverter.creationDtoToConsumption(creationDto);
            return Optional.of(create(consumption, consumption.getAccountId()));
        } else {
            return Optional.empty();
        }
    }

    private boolean hasStorageLocationCollision(ConsumptionCreationDto creationDto) {
        if (ConsumptionType.STORAGE.equals(creationDto.getConsumptionType())) {
            if (isConsumptionPresentForLocationAndMonitoredCrn(creationDto.getMonitoredResourceCrn(), creationDto.getStorageLocation())) {
                LOGGER.warn("Storage consumption with location [{}] already exists for resource with CRN [{}].",
                        creationDto.getStorageLocation(), creationDto.getMonitoredResourceCrn());
                return true;
            }
        }
        LOGGER.debug("Storage consumption with location [{}] does not exist for resource with CRN [{}] yet.",
                creationDto.getStorageLocation(), creationDto.getMonitoredResourceCrn());
        return false;
    }

    public boolean isConsumptionPresentForLocationAndMonitoredCrn(String monitoredResourceCrn, String storageLocation) {
        return consumptionRepository.doesStorageConsumptionExistWithLocationForMonitoredCrn(monitoredResourceCrn, storageLocation);
    }

    public List<JobResource> findAllStorageConsumptionJobResource() {
        return consumptionRepository.findAllStorageConsumptionJobResource();
    }

}
