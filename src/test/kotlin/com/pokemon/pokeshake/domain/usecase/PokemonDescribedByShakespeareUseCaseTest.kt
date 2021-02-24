package com.pokemon.pokeshake.domain.usecase

import com.nhaarman.mockito_kotlin.*
import com.pokemon.pokeshake.domain.PokemonApiGateway
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class PokemonDescribedByShakespeareUseCaseTest {

    private val pokemonApiGateway = mock<PokemonApiGateway>()
    private val useCase = PokemonDescribedByShakespeareUseCase(pokemonApiGateway)

    @Before
    fun `setup mocks`() {
        whenever(pokemonApiGateway.englishDescription(any())).thenReturn(POKEMON_DESCRIPTION)
    }

    @Test
    fun `should return PokemonResponse`() {
        val (name, description) = useCase.describe(POKEMON_NAME)

        assertThat(name).isEqualTo(POKEMON_NAME)
        assertThat(description).isEqualTo(POKEMON_DESCRIPTION)
    }

    @Test
    fun `should call PokemonApiGateway`() {
        useCase.describe(POKEMON_NAME)

        verify(pokemonApiGateway, times(1)).englishDescription(POKEMON_NAME)
    }

    companion object {
        private const val POKEMON_NAME = "aPokemon"
        private const val POKEMON_DESCRIPTION = "aDescription"
    }
}