package com.pokemon.pokeshake.domain.usecase

import com.pokemon.pokeshake.domain.PokemonApiGateway
import com.pokemon.pokeshake.domain.model.PokemonResponse

class PokemonDescribedByShakespeareUseCase(private val pokemonApiGateway: PokemonApiGateway) {

    fun describe(pokemonName: String): PokemonResponse {
        val englishDescription = pokemonApiGateway.englishDescription(pokemonName)
        return PokemonResponse(pokemonName, englishDescription)
    }

}
