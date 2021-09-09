package com.sequenceiq.cloudbreak.service.eventbus;

import static com.sequenceiq.cloudbreak.common.exception.NotFoundException.notFound;

import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.cloud.model.CloudResource;
import com.sequenceiq.cloudbreak.cloud.notification.model.ResourceNotification;
import com.sequenceiq.cloudbreak.cloud.service.Persister;
import com.sequenceiq.cloudbreak.converter.spi.CloudResourceToResourceConverter;
import com.sequenceiq.cloudbreak.domain.Resource;
import com.sequenceiq.cloudbreak.domain.stack.Stack;
import com.sequenceiq.cloudbreak.repository.StackReferenceRepository;
import com.sequenceiq.cloudbreak.service.resource.ResourceService;

@Component
public class CloudResourcePersisterService implements Persister<ResourceNotification> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CloudResourcePersisterService.class);

    @Inject
    private StackReferenceRepository stackReferenceRepository;

    @Inject
    private ResourceService resourceService;

    @Inject
    private CloudResourceToResourceConverter cloudResourceToResourceConverter;

    @Override
    public ResourceNotification persist(ResourceNotification notification) {
        LOGGER.debug("Resource allocation notification received: {}", notification);
        Long stackId = notification.getCloudContext().getId();
        CloudResource cloudResource = notification.getCloudResource();
        Resource resource = cloudResourceToResourceConverter.convert(cloudResource);
        Optional<Resource> persistedResource = getPersistedResource(stackId, cloudResource);
        if (persistedResource.isPresent()) {
            LOGGER.debug("Trying to persist a resource (name: {}, type: {}, stackId: {}) that is already persisted, skipping..",
                    cloudResource.getName(), cloudResource.getType().name(), stackId);
            return notification;
        }
        setStack(stackId, cloudResource, resource);
        resourceService.save(resource);
        return notification;
    }

    @Override
    public ResourceNotification update(ResourceNotification notification) {
        LOGGER.debug("Resource update notification received: {}", notification);
        Long stackId = notification.getCloudContext().getId();
        CloudResource cloudResource = notification.getCloudResource();
        Resource persistedResource = getPersistedResource(stackId, cloudResource)
                .orElseThrow(notFound("resource", cloudResource.getName()));
        Resource resource = cloudResourceToResourceConverter.convert(cloudResource);
        updateWithPersistedFields(resource, persistedResource);
        setStack(stackId, cloudResource, resource);
        resourceService.save(resource);
        return notification;
    }

    @Override
    public ResourceNotification delete(ResourceNotification notification) {
        LOGGER.debug("Resource deletion notification received: {}", notification);
        Long stackId = notification.getCloudContext().getId();
        CloudResource cloudResource = notification.getCloudResource();
        Optional<Resource> persistedResource = getPersistedResource(stackId, cloudResource);
        persistedResource.ifPresent(value -> resourceService.delete(value));
        return notification;
    }

    private Stack findStackById(Long stackId) {
        return stackReferenceRepository.getOne(stackId);
    }

    private void updateWithPersistedFields(Resource resource, Resource persistedResource) {
        if (persistedResource != null) {
            resource.setId(persistedResource.getId());
            resource.setInstanceGroup(persistedResource.getInstanceGroup());
        }
    }

    private Optional<Resource> getPersistedResource(Long stackId, CloudResource cloudResource) {
        if (cloudResource.isStackAware()) {
            return resourceService.findByStackIdAndNameAndType(stackId, cloudResource.getName(), cloudResource.getType());
        } else {
            return resourceService.findByResourceReferenceAndType(cloudResource.getReference(), cloudResource.getType());
        }
    }

    private void setStack(Long stackId, CloudResource cloudResource, Resource resource) {
        if (cloudResource.isStackAware()) {
            LOGGER.debug("Setting stack {} for cloud resource {} and type {}", stackId, cloudResource.getName(), cloudResource.getType());
            resource.setStack(findStackById(stackId));
        }
    }
}
