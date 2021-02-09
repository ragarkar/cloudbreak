package com.sequenceiq.it.cloudbreak.testcase.mock;

public class BlueprintTestBase extends AbstractMockTest {

    private static final String VALID_CM_BP = "{\"cdhVersion\":\"7.0.1\",\"displayName\":\"datamart\",\"services\":[{\"refName\":\"hdfs\",\"serviceType\":" +
            "\"HDFS\",\"roleConfigGroups\":[{\"refName\":\"hdfs-NAMENODE-BASE\",\"roleType\":\"NAMENODE\",\"base\":true},{\"refName\":\"" +
            "hdfs-SECONDARYNAMENODE-BASE\",\"roleType\":\"SECONDARYNAMENODE\",\"base\":true},{\"refName\":\"hdfs-DATANODE-BASE\",\"roleType\":\"DATANODE\"," +
            "\"configs\":[{\"name\":\"dfs_datanode_max_locked_memory\",\"value\":\"0\",\"autoConfig\":false}],\"base\":true},{\"refName\":\"" +
            "hdfs-BALANCER-BASE\",\"roleType\":\"BALANCER\",\"base\":true}]},{\"refName\":\"impala\",\"serviceType\":\"IMPALA\",\"serviceConfigs\":[{\"name\":\"" +
            "impala_cmd_args_safety_valve\",\"value\":\"--cache_s3_file_handles=true\"}],\"roleConfigGroups\":[{\"refName\":\"impala-IMPALAD-COORDINATOR\"" +
            ",\"roleType\":\"IMPALAD\",\"configs\":[{\"name\":\"impalad_specialization\",\"value\":\"COORDINATOR_ONLY\"},{\"name\":\"" +
            "impala_hdfs_site_conf_safety_valve\",\"value\":\"<property><name>fs.s3a.block.size</name><value>268435456</value></property><property" +
            "><name>fs.s3a.experimental.input.fadvise</name><value>RANDOM</value></property><property><name>fs.s3a.fast.upload</name><value>true</value>" +
            "</property>\"}],\"base\":false},{\"refName\":\"impala-IMPALAD-EXECUTOR\",\"roleType\":\"IMPALAD\",\"configs\":[{\"name\":\"" +
            "impalad_specialization\",\"value\":\"EXECUTOR_ONLY\"},{\"name\":\"impala_hdfs_site_conf_safety_valve\",\"value\":\"<property><name>" +
            "fs.s3a.block.size</name><value>268435456</value></property><property><name>fs.s3a.experimental.input.fadvise</name><value>RANDOM</value>" +
            "</property><property><name>fs.s3a.fast.upload</name><value>true</value></property>\"}],\"base\":false},{\"refName\":\"impala-STATESTORE-BASE\"" +
            ",\"roleType\":\"STATESTORE\",\"base\":true},{\"refName\":\"impala-CATALOGSERVER-BASE\",\"roleType\":\"CATALOGSERVER\",\"base\":true}]}],\"" +
            "hostTemplates\":[{\"refName\":\"master\",\"cardinality\":\"1\",\"roleConfigGroupsRefNames\":[\"hdfs-BALANCER-BASE\",\"hdfs-NAMENODE-BASE\"," +
            "\"hdfs-SECONDARYNAMENODE-BASE\",\"impala-CATALOGSERVER-BASE\",\"impala-STATESTORE-BASE\"" +
            "]},{\"refName\":\"coordinator\",\"cardinality\":\"1\",\"roleConfigGroupsRefNames\":[\"hdfs-DATANODE-BASE\",\"impala-IMPALAD-COORDINATOR\"]}," +
            "{\"refName\":\"executor\",\"cardinality\":\"2\",\"roleConfigGroupsRefNames\":[\"hdfs-DATANODE-BASE\",\"impala-IMPALAD-EXECUTOR\"]}]}";

    String getValidCMTemplateText() {
        return VALID_CM_BP;
    }

}
