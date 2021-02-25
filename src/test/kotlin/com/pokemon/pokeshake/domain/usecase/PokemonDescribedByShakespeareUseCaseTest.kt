package com.pokemon.pokeshake.domain.usecase

import com.nhaarman.mockito_kotlin.*
import com.pokemon.pokeshake.domain.exception.PokemonNotFoundException
import com.pokemon.pokeshake.domain.gateway.PokemonApiGateway
import com.pokemon.pokeshake.domain.gateway.PokemonApiResponse
import com.pokemon.pokeshake.domain.gateway.ShakespeareTranslatorApiGateway
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class PokemonDescribedByShakespeareUseCaseTest {

    private val pokemonApiGateway = mock<PokemonApiGateway>()
    private val shakespeareTranslatorApiGateway = mock<ShakespeareTranslatorApiGateway>()
    private val useCase = PokemonDescribedByShakespeareUseCase(pokemonApiGateway, shakespeareTranslatorApiGateway)

    @BeforeEach
    fun `setup mocks`() {
        whenever(pokemonApiGateway.englishDescription(any())).thenReturn(PokemonApiResponse.Success(POKEMON_DESCRIPTION))
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

    @Test
    fun `should throw IllegalArgumentException when PokemonApiResponse is Failure`() {
        whenever(pokemonApiGateway.englishDescription(any())).thenReturn(PokemonApiResponse.Failure("failure"))

        assertThrows(PokemonNotFoundException::class.java) { useCase.describe(POKEMON_NAME) }
    }

    companion object {
        private const val POKEMON_NAME = "aPokemon"
        private const val POKEMON_DESCRIPTION = "aDescription"
        private const val SHAKESPEARE_DESCRIPTION = "aShakespeareDescription"

    }
}