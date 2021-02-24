package com.pokemon.pokeshake

import com.pokemon.pokeshake.domain.PokemonApiGateway
import com.pokemon.pokeshake.domain.usecase.PokemonDescribedByShakespeareUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PokeshakeConfiguration {

    @Bean
    fun pokemonApiGateway(): PokemonApiGateway {
        return object : PokemonApiGateway {
            override fun englishDescription(pokemonName: String): String {
                return "not implemented"
            }
        }
    }

    @Bean
    fun pokemonDescribedByShakespeareUseCase(pokemonApiGateway: PokemonApiGateway): PokemonDescribedByShakespeareUseCase {
        return PokemonDescribedByShakespeareUseCase(pokemonApiGateway)
    }
}