package com.pokemon.pokeshake

import com.pokemon.pokeshake.domain.PokemonApiGateway
import com.pokemon.pokeshake.domain.ShakespeareTranslatorApiGateway
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