package com.pokemon.pokeshake.domain.gateway

sealed class PokemonApiResponse {
    data class Success(val description: String) : PokemonApiResponse()
    data class Failure(val description: String) : PokemonApiResponse()
}