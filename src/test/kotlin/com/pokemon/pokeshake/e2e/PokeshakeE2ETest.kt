package com.pokemon.pokeshake.e2e

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.http.HttpHeader
import com.github.tomakehurst.wiremock.http.HttpHeaders
import com.pokemon.pokeshake.delivery.PokeshakeApplication
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [PokeshakeApplication::class],
    properties = ["funtranslations.shakespeare.endpoint=http://localhost:9998"]
)
@AutoConfigureWireMock(port = 9998)
class PokeshakeE2ETest {

    @LocalServerPort
    private val port = 0

    @BeforeEach
    fun setup() {
        reset()
    }

    @Test
    fun `test known pokemon`() {
        stubShakespeareResponse(200, okResponse())

        RestAssured.given()
            .spec(requestSpecification())
            .get("$POKEMON_ENDPOINT/charizard")
            .then()
            .assertThat()
            .statusCode(200)
            .and()
            .body("name", equalTo("charizard"))
            .body("description", equalTo(TRANSLATED_STRING))
    }

    @Test
    fun `test known pokemon but quota limit reached`() {
        stubShakespeareResponse(429, koResponse())

        RestAssured.given()
            .spec(requestSpecification())
            .get("$POKEMON_ENDPOINT/charizard")
            .then()
            .assertThat()
            .statusCode(429)
    }

    @Test
    fun `test unknown pokemon`() {
        RestAssured.given()
            .spec(requestSpecification())
            .get("$POKEMON_ENDPOINT/monstermon")
            .then()
            .assertThat()
            .statusCode(404)
    }

    private fun requestSpecification(): RequestSpecification {
        return RequestSpecBuilder()
            .setBaseUri(BASE_URI)
            .setPort(port)
            .setContentType(ContentType.JSON)
            .build()
    }

    private fun okResponse() = """
        {
          "success": {
            "total": 1
          },
          "contents": {
            "translated": "Spits fire yond is hot enow to melt boulders. Known to cause forest fires unintentionally.",
            "text": "Spits fire that is hot enough to melt boulders. Known to cause forest fires unintentionally.",
            "translation": "shakespeare"
          }
        }
    """.trimIndent()

    private fun koResponse() = """
        {
            "error": {
                "code": 429,
                "message": "Too Many Requests: Rate limit of 5 requests per hour exceeded. Please wait for 59 minutes and 29 seconds."
            }
        }
    """.trimIndent()

    private fun stubShakespeareResponse(status: Int, responseBody: String) {
        stubFor(post(urlPathEqualTo("/"))
            .withQueryParam("text", WireMock.equalTo(STRING_TO_TRANSLATE))
            .willReturn(aResponse()
                .withStatus(status)
                .withHeaders(HttpHeaders(
                    HttpHeader.httpHeader("Content-Type", "application/json;charset=UTF-8"),
                    HttpHeader.httpHeader("Content-Encoding", "gzip")
                ))
                .withBody(responseBody)
            )
        )
    }

    companion object {
        private const val STRING_TO_TRANSLATE = "Spits fire that is hot enough to melt boulders. Known to cause forest fires unintentionally."
        private const val TRANSLATED_STRING = "Spits fire yond is hot enow to melt boulders. Known to cause forest fires unintentionally."
        private const val BASE_URI = "http://localhost"
        private const val POKEMON_ENDPOINT = "/pokemon"
    }

}