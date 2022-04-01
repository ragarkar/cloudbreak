package com.sequenceiq.freeipa.service.freeipa.user;

public enum UserSyncLogEvent {
    FULL_USER_SYNC,
    PARTIAL_USER_SYNC,
    USER_SYNC_DELETE,
    RETRIEVE_FULL_UMS_STATE,
    RETRIEVE_PARTIAL_UMS_STATE,
    RETRIEVE_FULL_IPA_STATE,
    RETRIEVE_PARTIAL_IPA_STATE,
    CALCULATE_UMS_IPA_DIFFERENCE,
    APPLY_DIFFERENCE_TO_IPA,
    SET_WORKLOAD_CREDENTIALS,
    SYNC_CLOUD_IDENTITIES,
    ADD_GROUPS,
    ADD_USERS,
    ADD_USERS_TO_GROUPS,
    REMOVE_USERS_FROM_GROUPS,
    REMOVE_USERS,
    REMOVE_GROUPS,
    DISABLE_USERS,
    ENABLE_USERS
}
