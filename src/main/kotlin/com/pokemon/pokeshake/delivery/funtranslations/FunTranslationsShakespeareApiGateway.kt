package com.pokemon.pokeshake.delivery.funtranslations

import com.pokemon.pokeshake.domain.gateway.ShakespeareTranslatorApiGateway

class FunTranslationsShakespeareApiGateway : ShakespeareTranslatorApiGateway {

    override fun translate(stringToTranslate: String): String {
        return "shakespeare not implemented"
    }
}