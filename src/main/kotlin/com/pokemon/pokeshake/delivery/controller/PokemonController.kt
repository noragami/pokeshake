package com.pokemon.pokeshake.delivery.controller

import com.pokemon.pokeshake.domain.exception.PokemonNotFoundException
import com.pokemon.pokeshake.domain.model.PokemonResponse
import com.pokemon.pokeshake.domain.usecase.PokemonDescribedByShakespeareUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping("/pokemon")
class PokemonController(private val pokemonDescribedByShakespeareUseCase: PokemonDescribedByShakespeareUseCase) {

    @GetMapping("/{pokemonName}")
    fun pokemon(@PathVariable pokemonName: String): PokemonResponse {
        return try {
            pokemonDescribedByShakespeareUseCase.describe(pokemonName)
        } catch (ex: PokemonNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "$pokemonName Not Found", ex)
        }
    }
}