package com.pokemon.pokeshake.domain.usecase

import com.pokemon.pokeshake.domain.exception.PokemonNotFoundException
import com.pokemon.pokeshake.domain.gateway.PokemonApiGateway
import com.pokemon.pokeshake.domain.gateway.PokemonApiResponse
import com.pokemon.pokeshake.domain.gateway.ShakespeareTranslatorApiGateway
import com.pokemon.pokeshake.domain.model.PokemonResponse

class PokemonDescribedByShakespeareUseCase(
    private val pokemonApiGateway: PokemonApiGateway,
    private val shakespeareTranslatorApiGateway: ShakespeareTranslatorApiGateway) {

    @Throws(PokemonNotFoundException::class)
    fun describe(pokemonName: String): PokemonResponse {
        when (val pokemonApiResponse = pokemonApiGateway.englishDescription(pokemonName)) {
            is PokemonApiResponse.Success -> {
                val translatedDescription = shakespeareTranslatorApiGateway.translate(pokemonApiResponse.description)
                return PokemonResponse(pokemonName, translatedDescription)
            }
            is PokemonApiResponse.Failure -> throw PokemonNotFoundException(pokemonApiResponse.description)
        }

    }

}
