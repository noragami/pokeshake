package com.pokemon.pokeshake.delivery.pokeapi

import com.pokemon.pokeshake.domain.gateway.PokemonApiResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.web.client.RestTemplate

class PokeApiGatewayIntegrationTest {

    private val restTemplate = RestTemplate()
    private val pokeApiGateway = PokeApiGateway(restTemplate, ENDPOINT)


    @Test
    fun `should call real service`() {
        val result = pokeApiGateway.englishDescription("charizard")

        assertThat(result).isInstanceOf(PokemonApiResponse.Success::class.java)
    }

    @Test
    fun `should return a Failure response on unknown pokemon`() {
        val result = pokeApiGateway.englishDescription("shakemon")

        assertThat(result).isInstanceOf(PokemonApiResponse.Failure::class.java)
        assertThat((result as PokemonApiResponse.Failure).description).isEqualTo("404 Not Found: [Not Found]")

    }

    @Test
    fun `should retrieve the description and replace all common whitepaces to an empty space`() {
        val result = pokeApiGateway.englishDescription("ditto")

        assertThat(result).isInstanceOf(PokemonApiResponse.Success::class.java)
        assertThat((result as PokemonApiResponse.Success).description).isEqualTo("It can freely recombine its own cellular structure to transform into other life-forms.")

    }

    companion object {
        private const val ENDPOINT = "https://pokeapi.co/api/v2/pokemon-species/"
    }
}