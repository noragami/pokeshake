package com.pokemon.pokeshake.e2e

import com.pokemon.pokeshake.PokeshakeApplication
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [PokeshakeApplication::class]
)
class PokeshakeE2ETest {

    @LocalServerPort
    private val port = 0

    @Test
    fun `test known pokemon`() {
        RestAssured.given()
            .spec(requestSpecification())
            .get(getPath(POKEMON_ENDPOINT, "charizard"))
            .then()
            .assertThat()
            .statusCode(200)
            .and()
            .body("name", equalTo("charizard"))
            .body("description", equalTo("shakespeare not implemented"))
    }

    @Test
    fun `test unknown pokemon`() {
        RestAssured.given()
            .spec(requestSpecification())
            .get(getPath(POKEMON_ENDPOINT, "monstermon"))
            .then()
            .assertThat()
            .statusCode(404)
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