/*
 * OpenAPI Petstore
 * This spec is mainly for testing Petstore server and contains fake endpoints, models. Please do not use this for any other purpose. Special characters: \" \\
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import java.util.Map;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.Objects;
import java.util.Map;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.openapitools.client.JSON;


/**
 * ScaleneTriangle
 */
@JsonPropertyOrder({
  ScaleneTriangle.JSON_PROPERTY_SHAPE_TYPE,
  ScaleneTriangle.JSON_PROPERTY_TRIANGLE_TYPE
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.11.0-SNAPSHOT")
public class ScaleneTriangle {
  public static final String JSON_PROPERTY_SHAPE_TYPE = "shapeType";
  @javax.annotation.Nonnull
  private String shapeType;

  public static final String JSON_PROPERTY_TRIANGLE_TYPE = "triangleType";
  @javax.annotation.Nonnull
  private String triangleType;

  public ScaleneTriangle() { 
  }

  public ScaleneTriangle shapeType(@javax.annotation.Nonnull String shapeType) {
    this.shapeType = shapeType;
    return this;
  }

  /**
   * Get shapeType
   * @return shapeType
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_SHAPE_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getShapeType() {
    return shapeType;
  }


  @JsonProperty(JSON_PROPERTY_SHAPE_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setShapeType(@javax.annotation.Nonnull String shapeType) {
    this.shapeType = shapeType;
  }


  public ScaleneTriangle triangleType(@javax.annotation.Nonnull String triangleType) {
    this.triangleType = triangleType;
    return this;
  }

  /**
   * Get triangleType
   * @return triangleType
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_TRIANGLE_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getTriangleType() {
    return triangleType;
  }


  @JsonProperty(JSON_PROPERTY_TRIANGLE_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setTriangleType(@javax.annotation.Nonnull String triangleType) {
    this.triangleType = triangleType;
  }

  /**
   * A container for additional, undeclared properties.
   * This is a holder for any undeclared properties as specified with
   * the 'additionalProperties' keyword in the OAS document.
   */
  private Map<String, Object> additionalProperties;

  /**
   * Set the additional (undeclared) property with the specified name and value.
   * If the property does not already exist, create it otherwise replace it.
   */
  @JsonAnySetter
  public ScaleneTriangle putAdditionalProperty(String key, Object value) {
    if (this.additionalProperties == null) {
        this.additionalProperties = new HashMap<>();
    }
    this.additionalProperties.put(key, value);
    return this;
  }

  /**
   * Return the additional (undeclared) property.
   */
  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return additionalProperties;
  }

  /**
   * Return the additional (undeclared) property with the specified name.
   */
  public Object getAdditionalProperty(String key) {
    if (this.additionalProperties == null) {
        return null;
    }
    return this.additionalProperties.get(key);
  }

  /**
   * Return true if this ScaleneTriangle object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o, false, null, true);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ScaleneTriangle {\n");
    sb.append("    shapeType: ").append(toIndentedString(shapeType)).append("\n");
    sb.append("    triangleType: ").append(toIndentedString(triangleType)).append("\n");
    sb.append("    additionalProperties: ").append(toIndentedString(additionalProperties)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

