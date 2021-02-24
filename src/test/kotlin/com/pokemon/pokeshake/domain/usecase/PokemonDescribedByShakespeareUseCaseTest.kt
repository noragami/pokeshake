package com.pokemon.pokeshake.domain.usecase

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PokemonDescribedByShakespeareUseCaseTest {

    private val useCase = PokemonDescribedByShakespeareUseCase()

    @Test
    fun `should return PokemonResponse`() {
        val pokemonName = "pokemon"

        val (name, description) = useCase.describe(pokemonName)

        assertThat(name).isEqualTo(pokemonName)
        assertThat(description).isNotEmpty
    }
}