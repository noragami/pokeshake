package com.pokemon.pokeshake.domain.usecase

import com.pokemon.pokeshake.domain.PokemonApiGateway
import com.pokemon.pokeshake.domain.ShakespeareTranslatorApiGateway
import com.pokemon.pokeshake.domain.model.PokemonResponse

class PokemonDescribedByShakespeareUseCase(
    private val pokemonApiGateway: PokemonApiGateway,
    private val shakespeareTranslatorApiGateway: ShakespeareTranslatorApiGateway) {

    fun describe(pokemonName: String): PokemonResponse {
        val englishDescription = pokemonApiGateway.englishDescription(pokemonName)
        val translatedDescription = shakespeareTranslatorApiGateway.translate(englishDescription)
        return PokemonResponse(pokemonName, translatedDescription)
    }

}
