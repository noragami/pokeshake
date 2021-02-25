package com.pokemon.pokeshake.domain.gateway

interface PokemonApiGateway {

    fun englishDescription(pokemonName: String): PokemonApiResponse


}
