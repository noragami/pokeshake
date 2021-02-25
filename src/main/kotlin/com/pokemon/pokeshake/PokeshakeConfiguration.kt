package com.pokemon.pokeshake

import com.pokemon.pokeshake.delivery.pokeapi.PokeApiGateway
import com.pokemon.pokeshake.domain.gateway.PokemonApiGateway
import com.pokemon.pokeshake.domain.gateway.ShakespeareTranslatorApiGateway
import com.pokemon.pokeshake.domain.usecase.PokemonDescribedByShakespeareUseCase
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class PokeshakeConfiguration {

    @Value("\${pokeapi.endpoint}")
    private lateinit var pokeApiEndpoint: String

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }

    @Bean
    fun pokemonApiGateway(restTemplate: RestTemplate): PokemonApiGateway {
        return PokeApiGateway(restTemplate, pokeApiEndpoint)
    }

    @Bean
    fun shakespeareTranslatorApiGateway(): ShakespeareTranslatorApiGateway {
        return object : ShakespeareTranslatorApiGateway {
            override fun translate(stringToTranslate: String): String {
                return "shakespeare not implemented"
            }
        }
    }

    @Bean
    fun pokemonDescribedByShakespeareUseCase(
        pokemonApiGateway: PokemonApiGateway,
        shakespeareTranslatorApiGateway: ShakespeareTranslatorApiGateway
    ): PokemonDescribedByShakespeareUseCase {
        return PokemonDescribedByShakespeareUseCase(pokemonApiGateway, shakespeareTranslatorApiGateway)
    }
}