/**
 * OpenAPI Petstore
 * This is a sample server Petstore server. For this sample, you can use the api key `special-key` to test the authorization filters.
 *
 * OpenAPI spec version: 1.0.0
 *
 * Contact: team@openapitools.org
 *
 * NOTE: This class is auto generated by OpenAPI Generator.
 *
 * https://openapi-generator.tech
 */

// this model was generated using modelTest.mustache
package sample.cask.model
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.UUID
import scala.math.BigDecimal
import ujson.Value

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import scala.util.*

class ComplexRequestTest extends AnyWordSpec with Matchers {


    "ComplexRequest.fromJson" should {
        """not parse invalid json""" in {
           val Failure(err) = Try(ComplexRequestData.fromJsonString("invalid jason"))
           err.getMessage should startWith ("Error parsing json 'invalid jason'")
        }
        """parse """ ignore {
           val d8a = ComplexRequestData.fromJsonString("""""")
           val Failure(err : ValidationErrors) = ComplexRequestData.validated(d8a, true)

           sys.error("TODO")
        }
    }
}
