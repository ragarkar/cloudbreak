package com.sequenceiq.cloudbreak.idbmms;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cloudera.thunderhead.service.idbrokermappingmanagement.IdBrokerMappingManagementProto;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.sequenceiq.cloudbreak.grpc.ManagedChannelWrapper;
import com.sequenceiq.cloudbreak.idbmms.exception.IdbmmsOperationException;
import com.sequenceiq.cloudbreak.idbmms.model.MappingsConfig;

import io.grpc.ManagedChannel;
import io.opentracing.Tracer;

/**
 * A GRPC-based client for the IDBroker Mapping Management Service (IDBMMS).
 */
@Component
public class GrpcIdbmmsClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrpcIdbmmsClient.class);

    @Inject
    private ManagedChannelWrapper channelWrapper;

    @Inject
    private Tracer tracer;

    public static GrpcIdbmmsClient createClient(ManagedChannelWrapper channelWrapper, Tracer tracer) {
        GrpcIdbmmsClient client = new GrpcIdbmmsClient();
        client.channelWrapper = Preconditions.checkNotNull(channelWrapper);
        client.tracer = Preconditions.checkNotNull(tracer);
        return client;
    }

    @VisibleForTesting
    IdbmmsClient makeClient(ManagedChannel channel) {
        return new IdbmmsClient(channel, tracer);
    }

    /**
     * Retrieves IDBroker mappings from IDBMMS for a particular environment.
     *
     * @param environmentCrn the environment CRN to get mappings for; must not be {@code null}
     * @param requestId an optional request ID; must not be {@code null}
     * @return the mappings config associated with environment {@code environmentCrn}; never {@code null}
     * @throws NullPointerException if either argument is {@code null}
     * @throws IdbmmsOperationException if any problem is encountered during the IDBMMS call processing
     */
    public MappingsConfig getMappingsConfig(String environmentCrn, Optional<String> requestId) {
        checkNotNull(environmentCrn);
        checkNotNull(requestId);

        IdbmmsClient client = makeClient(channelWrapper.getChannel());
        String effectiveRequestId = requestId.orElse(UUID.randomUUID().toString());
        LOGGER.debug("Fetching IDBroker mappings for environment {} using request ID {}", environmentCrn, effectiveRequestId);
        MappingsConfig mappingsConfig = client.getMappingsConfig(effectiveRequestId, environmentCrn);
        LOGGER.debug("Retrieved IDBroker mappings of version {} for environment {}", mappingsConfig.getMappingsVersion(), environmentCrn);
        return mappingsConfig;
    }

    public IdBrokerMappingManagementProto.SetMappingsResponse setMappingsConfig(String environmentCrn, String dataAccessRole,
            String baseLineRole, String accountId, Optional<String> requestId) {
        checkNotNull(environmentCrn);

        IdbmmsClient client = makeClient(channelWrapper.getChannel());
        LOGGER.debug("Configuring IDBroker mappings for environment '{}' with Data Access role '{}', Ranger Audit role '{}', account Id '{}'...",
                environmentCrn, dataAccessRole, baseLineRole, accountId);
        IdBrokerMappingManagementProto.SetMappingsResponse setMappingsResponse =
                client.setMappings(getOrGenerate(requestId), environmentCrn, dataAccessRole, baseLineRole, accountId);
        LOGGER.debug("IDBroker mappings have been configured for environment '{}' with Data Access role '{}', Ranger Audit role '{}', account Id '{}'.",
                environmentCrn, dataAccessRole, baseLineRole, accountId);
        return setMappingsResponse;
    }

    /**
     * Deletes IDBroker mappings in IDBMMS for a particular environment.
     *
     * @param environmentCrn the environment CRN to delete mappings for; must not be {@code null}
     * @param requestId an optional request ID; must not be {@code null}
     * @throws NullPointerException if either argument is {@code null}
     * @throws IdbmmsOperationException if any problem is encountered during the IDBMMS call processing
     */
    public void deleteMappings(String environmentCrn, Optional<String> requestId) {
        checkNotNull(environmentCrn);
        checkNotNull(requestId);

        IdbmmsClient client = makeClient(channelWrapper.getChannel());
        String effectiveRequestId = requestId.orElse(UUID.randomUUID().toString());
        LOGGER.debug("Deleting IDBroker mappings for environment {} using request ID {}", environmentCrn, effectiveRequestId);
        client.deleteMappings(effectiveRequestId, environmentCrn);
        LOGGER.debug("Deleted IDBroker mappings for environment {}", environmentCrn);
    }

    private static String getOrGenerate(Optional<String> requestId) {
        return requestId.orElseGet(GrpcIdbmmsClient::newRequestId);
    }

    private static String newRequestId() {
        return UUID.randomUUID().toString();
    }
}
