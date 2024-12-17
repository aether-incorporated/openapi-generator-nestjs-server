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

package org.openapitools.server.api;

import java.math.BigDecimal;
import org.openapitools.server.model.ChildWithNullable;
import org.openapitools.server.model.Client;
import org.openapitools.server.model.EnumClass;
import org.openapitools.server.model.FakeBigDecimalMap200Response;
import java.io.File;
import org.openapitools.server.model.FileSchemaTestClass;
import org.openapitools.server.model.HealthCheckResult;
import java.util.List;
import java.time.LocalDate;
import java.util.Map;
import java.time.OffsetDateTime;
import org.openapitools.server.model.OuterComposite;
import org.openapitools.server.model.OuterObjectWithEnumProperty;
import org.openapitools.server.model.Pet;
import org.openapitools.server.model.TestInlineFreeformAdditionalPropertiesRequest;
import org.openapitools.server.model.User;

import jakarta.ws.rs.*;

import java.io.InputStream;
import java.util.Map;
import java.util.List;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid;

@Path("/fake")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaHelidonServerCodegen", comments = "Generator version: 7.11.0-SNAPSHOT")
public interface FakeService {

    @GET
    @Path("/BigDecimalMap")
    @Produces({ "*/*" })
    FakeBigDecimalMap200Response fakeBigDecimalMap();

    @GET
    @Path("/health")
    @Produces({ "application/json" })
    HealthCheckResult fakeHealthGet();

    @GET
    @Path("/http-signature-test")
    @Consumes({ "application/json", "application/xml" })
    void fakeHttpSignatureTest(@Valid @NotNull Pet pet, @QueryParam("query_1") String query1, @HeaderParam("header_1")  String header1);

    @POST
    @Path("/outer/boolean")
    @Consumes({ "application/json" })
    @Produces({ "*/*" })
    Boolean fakeOuterBooleanSerialize(@Valid Boolean body);

    @POST
    @Path("/outer/composite")
    @Consumes({ "application/json" })
    @Produces({ "*/*" })
    OuterComposite fakeOuterCompositeSerialize(@Valid OuterComposite outerComposite);

    @POST
    @Path("/outer/number")
    @Consumes({ "application/json" })
    @Produces({ "*/*" })
    BigDecimal fakeOuterNumberSerialize(@Valid BigDecimal body);

    @POST
    @Path("/outer/string")
    @Consumes({ "application/json" })
    @Produces({ "*/*" })
    String fakeOuterStringSerialize(@Valid String body);

    @POST
    @Path("/property/enum-int")
    @Consumes({ "application/json" })
    @Produces({ "*/*" })
    OuterObjectWithEnumProperty fakePropertyEnumIntegerSerialize(@Valid @NotNull OuterObjectWithEnumProperty outerObjectWithEnumProperty);

    @POST
    @Path("/additionalProperties-reference")
    @Consumes({ "application/json" })
    void testAdditionalPropertiesReference(@Valid @NotNull Map<String, Object> requestBody);

    @PUT
    @Path("/body-with-binary")
    @Consumes({ "image/png" })
    void testBodyWithBinary(@Valid File body);

    @PUT
    @Path("/body-with-file-schema")
    @Consumes({ "application/json" })
    void testBodyWithFileSchema(@Valid @NotNull FileSchemaTestClass fileSchemaTestClass);

    @PUT
    @Path("/body-with-query-params")
    @Consumes({ "application/json" })
    void testBodyWithQueryParams(@QueryParam("query") @NotNull String query, @Valid @NotNull User user);

    @PATCH
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    Client testClientModel(@Valid @NotNull Client client);

    @POST
    @Consumes({ "application/x-www-form-urlencoded" })
    void testEndpointParameters(@FormParam(value = "number")  BigDecimal number, @FormParam(value = "double")  Double _double, @FormParam(value = "pattern_without_delimiter")  String patternWithoutDelimiter, @FormParam(value = "byte")  byte[] _byte, @FormParam(value = "integer")  Integer integer, @FormParam(value = "int32")  Integer int32, @FormParam(value = "int64")  Long int64, @FormParam(value = "float")  Float _float, @FormParam(value = "string")  String string,  @FormParam(value = "binary") InputStream binaryInputStream, @FormParam(value = "date")  LocalDate date, @FormParam(value = "dateTime")  OffsetDateTime dateTime, @FormParam(value = "password")  String password, @FormParam(value = "callback")  String paramCallback);

    @GET
    @Consumes({ "application/x-www-form-urlencoded" })
    void testEnumParameters(@HeaderParam("enum_header_string_array")  List<String> enumHeaderStringArray, @HeaderParam("enum_header_string")  @DefaultValue("-efg") String enumHeaderString, @QueryParam("enum_query_string_array") List<String> enumQueryStringArray, @QueryParam("enum_query_string") @DefaultValue("-efg") String enumQueryString, @QueryParam("enum_query_integer") Integer enumQueryInteger, @QueryParam("enum_query_double") Double enumQueryDouble, @QueryParam("enum_query_model_array") List<EnumClass> enumQueryModelArray, @FormParam(value = "enum_form_string_array")  List<String> enumFormStringArray, @FormParam(value = "enum_form_string")  String enumFormString);

    @DELETE
    void testGroupParameters(@QueryParam("required_string_group") @NotNull Integer requiredStringGroup, @HeaderParam("required_boolean_group") @NotNull  Boolean requiredBooleanGroup, @QueryParam("required_int64_group") @NotNull Long requiredInt64Group, @QueryParam("string_group") Integer stringGroup, @HeaderParam("boolean_group")  Boolean booleanGroup, @QueryParam("int64_group") Long int64Group);

    @POST
    @Path("/inline-additionalProperties")
    @Consumes({ "application/json" })
    void testInlineAdditionalProperties(@Valid @NotNull Map<String, String> requestBody);

    @POST
    @Path("/inline-freeform-additionalProperties")
    @Consumes({ "application/json" })
    void testInlineFreeformAdditionalProperties(@Valid @NotNull TestInlineFreeformAdditionalPropertiesRequest testInlineFreeformAdditionalPropertiesRequest);

    @GET
    @Path("/jsonFormData")
    @Consumes({ "application/x-www-form-urlencoded" })
    void testJsonFormData(@FormParam(value = "param")  String param, @FormParam(value = "param2")  String param2);

    @POST
    @Path("/nullable")
    @Consumes({ "application/json" })
    void testNullable(@Valid @NotNull ChildWithNullable childWithNullable);

    @PUT
    @Path("/test-query-parameters")
    void testQueryParameterCollectionFormat(@QueryParam("pipe") @NotNull List<String> pipe, @QueryParam("ioutil") @NotNull List<String> ioutil, @QueryParam("http") @NotNull List<String> http, @QueryParam("url") @NotNull List<String> url, @QueryParam("context") @NotNull List<String> context, @QueryParam("allowEmpty") @NotNull String allowEmpty, @QueryParam("language") Map<String, String> language);

    @POST
    @Path("/stringMap-reference")
    @Consumes({ "application/json" })
    void testStringMapReference(@Valid @NotNull Map<String, String> requestBody);
}
