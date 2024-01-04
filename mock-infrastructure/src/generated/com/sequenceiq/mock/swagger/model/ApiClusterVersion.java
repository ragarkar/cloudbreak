package com.sequenceiq.mock.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The CDH version of the cluster.
 */
public enum ApiClusterVersion {

  CDH3("CDH3"),

  CDH3U4X("CDH3u4X"),

  CDH4("CDH4"),

  CDH5("CDH5"),

  CDH6("CDH6"),

  CDH7("CDH7"),

  EXPERIENCE1("EXPERIENCE1"),

  UNKNOWN("UNKNOWN");

  private String value;

  ApiClusterVersion(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static ApiClusterVersion fromValue(String text) {
    for (ApiClusterVersion b : ApiClusterVersion.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}

