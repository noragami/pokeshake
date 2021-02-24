package com.pokemon.pokeshake.domain

interface PokemonApiGateway {

    fun englishDescription(pokemonName: String): String

}
