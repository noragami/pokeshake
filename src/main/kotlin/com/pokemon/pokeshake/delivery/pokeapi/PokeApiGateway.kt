package com.pokemon.pokeshake.delivery.pokeapi

import com.fasterxml.jackson.annotation.JsonProperty
import com.pokemon.pokeshake.domain.gateway.PokemonApiGateway
import com.pokemon.pokeshake.domain.gateway.PokemonApiResponse
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod.GET
import org.springframework.http.MediaType
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

class PokeApiGateway(private val restTemplate: RestTemplate, private val endpoint: String) : PokemonApiGateway {

    override fun englishDescription(pokemonName: String): PokemonApiResponse {
        return try {
            val response = restTemplate.exchange(getEndpoint(endpoint, pokemonName), GET, HttpEntity<Species>(headers()), Species::class.java)
            PokemonApiResponse.Success(getDescription(response.body))
        } catch (ex: RestClientException) {
            PokemonApiResponse.Failure(ex.localizedMessage)
        }
    }

    private fun getDescription(body: Species?): String =
        body?.entries?.first { it.language.name == "en" }?.text!!.removeUnwantedChars()

    private fun String.removeUnwantedChars() = replace("\\s+".toRegex(), " ")

    private fun headers(): HttpHeaders {
        val headers = HttpHeaders()
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        headers.contentType = MediaType.APPLICATION_JSON
        headers.add("user-agent", "Mozilla/5.0 Firefox/26.0")
        return headers
    }

    private fun getEndpoint(endpoint: String, pokemonName: String): String = "$endpoint/$pokemonName"

    data class Species(
        @JsonProperty("flavor_text_entries") val entries: List<Entry>
    ) {
        data class Entry(
            @JsonProperty("flavor_text") val text: String,
            val language: Language
        ) {
            data class Language(
                val name: String
            )
        }
    }

}