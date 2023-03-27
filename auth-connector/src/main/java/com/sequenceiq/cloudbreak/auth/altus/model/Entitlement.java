package com.sequenceiq.cloudbreak.auth.altus.model;

public enum Entitlement {

    DATAHUB_FLOW_SCALING,
    DATAHUB_STREAMING_SCALING,
    DATAHUB_DEFAULT_SCALING,
    CDP_AZURE,
    CDP_GCP,
    AUDIT_ARCHIVING_GCP,
    CDP_CB_AWS_NATIVE,
    CDP_CB_AWS_NATIVE_DATALAKE,
    CDP_CB_AWS_NATIVE_FREEIPA,
    CDP_CB_AWS_VARIANT_MIGRATION,
    CDP_BASE_IMAGE,
    CDP_FREEIPA_HA_REPAIR,
    CDP_FREEIPA_REBUILD,
    CLOUDERA_INTERNAL_ACCOUNT,
    CDP_FMS_CLUSTER_PROXY,
    CDP_CLOUD_STORAGE_VALIDATION_ON_VM,
    CDP_CLOUD_STORAGE_VALIDATION,
    CDP_CLOUD_STORAGE_VALIDATION_AWS,
    CDP_CLOUD_STORAGE_VALIDATION_AZURE,
    CDP_CLOUD_STORAGE_VALIDATION_GCP,
    CDP_CM_HA,
    CDP_CM_DISABLE_AUTO_BUNDLE_COLLECTION,
    CDP_RAW_S3,
    CDP_MICRO_DUTY_SDX,
    CDP_RUNTIME_UPGRADE,
    CDP_RUNTIME_UPGRADE_DATAHUB,
    CDP_OS_UPGRADE_DATAHUB,
    LOCAL_DEV,
    CDP_AZURE_SINGLE_RESOURCE_GROUP,
    CDP_AZURE_SINGLE_RESOURCE_GROUP_DEDICATED_STORAGE_ACCOUNT,
    CDP_AZURE_IMAGE_MARKETPLACE,
    CDP_AZURE_IMAGE_MARKETPLACE_ONLY,
    CDP_AZURE_UAE_CENTRAL,
    CDP_CLOUD_IDENTITY_MAPPING,
    CDP_ALLOW_INTERNAL_REPOSITORY_FOR_UPGRADE,
    CDP_SDX_HBASE_CLOUD_STORAGE,
    CDP_DATA_LAKE_AWS_EFS,
    CB_AUTHZ_POWER_USERS,
    CDP_ALLOW_DIFFERENT_DATAHUB_VERSION_THAN_DATALAKE,
    DATAHUB_AWS_AUTOSCALING,
    DATAHUB_AWS_STOP_START_SCALING,
    DATAHUB_STOP_START_SCALING_FAILURE_RECOVERY,
    DATAHUB_AZURE_AUTOSCALING,
    DATAHUB_AZURE_STOP_START_SCALING,
    DATAHUB_GCP_AUTOSCALING,
    DATAHUB_GCP_STOP_START_SCALING,
    CDP_CCM_V2,
    CDP_CCM_V2_JUMPGATE,
    CDP_CCM_V2_USE_ONE_WAY_TLS,
    CDP_CB_AZURE_VERTICAL_SCALE,
    CDP_CB_GCP_VERTICAL_SCALE,
    CDP_CCM_V1_TO_V2_JUMPGATE_UPGRADE,
    CDP_CCM_V2_TO_V2_JUMPGATE_UPGRADE,
    CDP_CB_DATABASE_WIRE_ENCRYPTION,
    CDP_CB_DATABASE_WIRE_ENCRYPTION_DATAHUB,
    CDP_ENABLE_DISTROX_INSTANCE_TYPES,
    CDP_SHOW_CLI,
    PERSONAL_VIEW_CB_BY_RIGHT,
    CDP_DATA_LAKE_LOAD_BALANCER,
    CDP_DATA_LAKE_LOAD_BALANCER_AZURE,
    CDP_EXPERIENCE_DELETION_BY_ENVIRONMENT,
    CDP_USE_DATABUS_CNAME_ENDPOINT,
    CDP_USE_CM_SYNC_COMMAND_POLLER,
    CDP_DATAHUB_NODESTATUS_CHECK,
    CDP_NODESTATUS_ENABLE_SALT_PING,
    CDP_SAAS,
    CDP_VM_DIAGNOSTICS,
    CDP_FREEIPA_DATABUS_ENDPOINT_VALIDATION,
    CDP_DATAHUB_DATABUS_ENDPOINT_VALIDATION,
    CDP_DATALAKE_BACKUP_ON_UPGRADE,
    CDP_DATALAKE_BACKUP_ON_RESIZE,
    CDP_DATALAKE_RESIZE_RECOVERY,
    DATA_LAKE_LIGHT_TO_MEDIUM_MIGRATION,
    CDP_PUBLIC_ENDPOINT_ACCESS_GATEWAY_AZURE,
    CDP_PUBLIC_ENDPOINT_ACCESS_GATEWAY_GCP,
    FMS_FREEIPA_BATCH_CALL,
    CDP_CB_AZURE_ENCRYPTION_AT_HOST,
    CDP_USER_SYNC_CREDENTIALS_UPDATE_OPTIMIZATION,
    OJDBC_TOKEN_DH,
    OJDBC_TOKEN_DH_ONE_HOUR_TOKEN,
    CDP_ENDPOINT_GATEWAY_SKIP_VALIDATION,
    CDP_AWS_RESTRICTED_POLICY,
    CDP_CONCLUSION_CHECKER_SEND_USER_EVENT,
    CDP_ALLOW_HA_UPGRADE,
    CDP_ALLOW_HA_REPAIR,
    CDP_ALLOW_HA_DOWNSCALE,
    EPHEMERAL_DISKS_FOR_TEMP_DATA,
    CDP_FREEIPA_UPGRADE,
    UI_EDP_PROGRESS_BAR,
    CDP_DATA_LAKE_MEDIUM_DUTY_WITH_PROFILER,
    CDP_UNBOUND_ELIMINATION,
    CDP_CM_BULK_HOSTS_REMOVAL,
    CDP_TARGETED_UPSCALE,
    E2E_TEST_ONLY,
    CDP_DATALAKE_ZDU_OS_UPGRADE,
    CDP_ENVIRONMENT_PRIVILEGED_USER,
    WORKLOAD_IAM_SYNC,
    CDP_FMS_USERSYNC_THREAD_TIMEOUT,
    CDP_FMS_DELAYED_STOP_START,
    CDP_CENTRAL_COMPUTE_MONITORING,
    CDP_SAAS_SDX_INTEGRATION,
    CDP_ROTATE_SALTUSER_PASSWORD,
    CDP_USERSYNC_ENFORCE_GROUP_MEMBER_LIMIT,
    CDP_DATA_LAKE_BACKUP_RESTORE_PERMISSION_CHECKS,
    CDP_DATAHUB_EXPERIMENTAL_SCALE_LIMITS,
    CDP_POSTGRES_UPGRADE_EMBEDDED,
    CDP_POSTGRES_UPGRADE_EXCEPTION,
    CDP_POSTGRES_UPGRADE_SKIP_SERVICE_STOP,
    CDP_USERSYNC_SPLIT_FREEIPA_USER_RETRIEVAL,
    CDP_DATALAKE_BACKUP_LONG_TIMEOUT,
    CDP_POSTGRES_UPGRADE_SKIP_ATTACHED_DATAHUBS_CHECK,
    CDP_UPGRADE_SKIP_ATTACHED_DATAHUBS_CHECK,
    ACCESS_KEY_ECDSA,
    CDP_ENVIRONMENT_EDIT_PROXY_CONFIG,
    SDX_CONFIGURATION_OPTIMIZATION,
    TARGETING_SUBNETS_FOR_ENDPOINT_ACCESS_GATEWAY,
    CDP_AZURE_CERTIFICATE_AUTH,
    CDP_CB_COST_CALCULATION,
    CDP_CB_CO2_CALCULATION,
    WORKLOAD_IAM_USERSYNC_ROUTING,
    CDP_CB_ENFORCE_AWS_NATIVE_FOR_SINGLE_AZ_FREEIPA,
    CDP_CB_ENFORCE_AWS_NATIVE_FOR_SINGLE_AZ_DATALAKE,
    CDP_CB_ENFORCE_AWS_NATIVE_FOR_SINGLE_AZ_DATAHUB,
    CDP_GCP_RAZ,
    AWS_GP3_ROOT_VOLUME_AS_DEFAULT,
    CDP_FEDRAMP_EXTERNAL_DATABASE_FORCE_DISABLED
}
