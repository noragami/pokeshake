package com.pokemon.pokeshake.delivery.controller

import com.pokemon.pokeshake.domain.model.PokemonResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pokemon")
class PokemonController {

    @GetMapping("/{pokemonName}")
    fun pokemon(@PathVariable pokemonName: String): PokemonResponse {
        return PokemonResponse(pokemonName, "not implemented")
    }
}