package com.pokemon.pokeshake

import com.pokemon.pokeshake.domain.usecase.PokemonDescribedByShakespeareUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PokeshakeConfiguration {

    @Bean
    fun pokemonDescribedByShakespeareUseCase(): PokemonDescribedByShakespeareUseCase {
        return PokemonDescribedByShakespeareUseCase()
    }
}