package com.pokemon.pokeshake.domain.usecase

import com.pokemon.pokeshake.domain.model.PokemonResponse

class PokemonDescribedByShakespeareUseCase {

    fun describe(pokemonName: String): PokemonResponse {
        return PokemonResponse(pokemonName, "not implemented")
    }

}
