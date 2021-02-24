package com.pokemon.pokeshake.e2e

import com.pokemon.pokeshake.PokeshakeApplication
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@ActiveProfiles("E2E")
@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [PokeshakeApplication::class]
)
class PokeshakeE2E {

    @LocalServerPort
    private val port = 0

    @Test
    fun `test e2e`() {
        RestAssured.given()
            .spec(requestSpecification())
            .get(getPath(POKEMON_ENDPOINT, "charizard"))
            .then()
            .assertThat()
            .statusCode(200)
            .and()
            .body("name", equalTo("charizard"))
            .body("description", equalTo("not implemented"))
    }

    private fun getPath(endpoint: String, pokemon: String): String = "$endpoint/$pokemon"

    private fun requestSpecification(): RequestSpecification {
        return RequestSpecBuilder()
            .setBaseUri(BASE_URI)
            .setPort(port)
            .setContentType(ContentType.JSON)
            .build()
    }

    companion object {
        private const val BASE_URI = "http://localhost"
        private const val POKEMON_ENDPOINT = "/pokemon"
    }

}