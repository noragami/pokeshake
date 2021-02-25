package com.pokemon.pokeshake.domain.gateway

interface ShakespeareTranslatorApiGateway {

    fun translate(stringToTranslate: String): String

}
