package com.pokemon.pokeshake.delivery.funtranslations

import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.http.HttpHeader
import com.github.tomakehurst.wiremock.http.HttpHeaders
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import java.net.URLEncoder


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 9998)
class FunTranslationsShakespeareApiGatewayIntegrationTest {

    private val restTemplate = RestTemplate()
    private val funTranslationsShakespeareApiGateway = FunTranslationsShakespeareApiGateway(restTemplate, ENDPOINT)

    @BeforeEach
    fun setup() {
        reset()
    }

    @Test
    fun `should call wiremock service`() {
        stubResponse(200, okResponse(), STRING_TO_TRANSLATE)

        val result = funTranslationsShakespeareApiGateway.translate(STRING_TO_TRANSLATE)

        assertThat(result).isNotBlank
        assertThat(result).isEqualTo(TRANSLATED_STRING)
    }

    @Test
    fun `should call wiremock service but api quota has been reached`() {
        stubResponse(429, koResponse(), STRING_TO_TRANSLATE)

        Assertions.assertThrows(HttpClientErrorException.TooManyRequests::class.java) {
            funTranslationsShakespeareApiGateway.translate(STRING_TO_TRANSLATE)
        }
    }

    private fun okResponse() = """
        {
          "success": {
            "total": 1
          },
          "contents": {
            "translated": "Thee did giveth mr. Tim a hearty meal.",
            "text": "You gave Mr. Tim a hearty meal.",
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

    private fun stubResponse(status: Int, responseBody: String, stringToTranslate: String) {
        val encodedString = URLEncoder.encode(stringToTranslate, "utf-8")
        stubFor(post(urlEqualTo("/?text=$encodedString"))
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
        private const val ENDPOINT = "http://localhost:9998"
        private const val STRING_TO_TRANSLATE = "You gave Mr. Tim a hearty meal."
        private const val TRANSLATED_STRING = "Thee did giveth mr. Tim a hearty meal."
    }
}