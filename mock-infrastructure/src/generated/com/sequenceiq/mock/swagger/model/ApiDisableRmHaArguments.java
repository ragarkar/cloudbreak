package com.sequenceiq.mock.swagger.model;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Arguments used for Disable RM HA command.
 */
@ApiModel(description = "Arguments used for Disable RM HA command.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-12-10T21:24:30.629+01:00")




public class ApiDisableRmHaArguments   {
  @JsonProperty("activeName")
  private String activeName = null;

  public ApiDisableRmHaArguments activeName(String activeName) {
    this.activeName = activeName;
    return this;
  }

  /**
   * Name of the ResourceManager that will be active after HA is disabled.
   * @return activeName
  **/
  @ApiModelProperty(value = "Name of the ResourceManager that will be active after HA is disabled.")


  public String getActiveName() {
    return activeName;
  }

  public void setActiveName(String activeName) {
    this.activeName = activeName;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiDisableRmHaArguments apiDisableRmHaArguments = (ApiDisableRmHaArguments) o;
    return Objects.equals(this.activeName, apiDisableRmHaArguments.activeName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(activeName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiDisableRmHaArguments {\n");

    sb.append("    activeName: ").append(toIndentedString(activeName)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

