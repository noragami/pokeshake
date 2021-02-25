package com.pokemon.pokeshake.delivery.funtranslations

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FunTranslationsShakespeareApiGatewayIntegrationTest {

    private val funTranslationsShakespeareApiGateway = FunTranslationsShakespeareApiGateway()

    @Test
    fun `should call real service`() {
        val result = funTranslationsShakespeareApiGateway.translate("Better safe than sorry!")

        assertThat(result).isNotBlank
        assertThat(result).isEqualTo("shakespeare not implemented")
    }
}