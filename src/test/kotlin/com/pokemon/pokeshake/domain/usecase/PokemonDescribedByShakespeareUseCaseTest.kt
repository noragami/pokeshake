package com.pokemon.pokeshake.domain.usecase

import com.nhaarman.mockito_kotlin.*
import com.pokemon.pokeshake.domain.PokemonApiGateway
import com.pokemon.pokeshake.domain.ShakespeareTranslatorApiGateway
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class PokemonDescribedByShakespeareUseCaseTest {

    private val pokemonApiGateway = mock<PokemonApiGateway>()
    private val shakespeareTranslatorApiGateway = mock<ShakespeareTranslatorApiGateway>()
    private val useCase = PokemonDescribedByShakespeareUseCase(pokemonApiGateway, shakespeareTranslatorApiGateway)

    @Before
    fun `setup mocks`() {
        whenever(pokemonApiGateway.englishDescription(any())).thenReturn(POKEMON_DESCRIPTION)
        whenever(shakespeareTranslatorApiGateway.translate(any())).thenReturn(SHAKESPEARE_DESCRIPTION)
    }

    @Test
    fun `should return PokemonResponse`() {
        val (name, description) = useCase.describe(POKEMON_NAME)

        assertThat(name).isEqualTo(POKEMON_NAME)
        assertThat(description).isEqualTo(SHAKESPEARE_DESCRIPTION)
    }

    @Test
    fun `should invoke PokemonApiGateway`() {
        useCase.describe(POKEMON_NAME)

        verify(pokemonApiGateway, times(1)).englishDescription(POKEMON_NAME)
    }

    @Test
    fun `should invoke ShakespeareTranslatorApiGateway`() {
        useCase.describe(POKEMON_NAME)

        verify(shakespeareTranslatorApiGateway, times(1)).translate(POKEMON_DESCRIPTION)
    }

    companion object {
        private const val POKEMON_NAME = "aPokemon"
        private const val POKEMON_DESCRIPTION = "aDescription"
        private const val SHAKESPEARE_DESCRIPTION = "aShakespeareDescription"

    }
}