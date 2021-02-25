package com.pokemon.pokeshake.delivery.funtranslations

import com.pokemon.pokeshake.domain.gateway.ShakespeareTranslatorApiGateway
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod.POST
import org.springframework.web.client.RestTemplate
import java.net.URLEncoder

class FunTranslationsShakespeareApiGateway(private val restTemplate: RestTemplate, private val endpoint: String) : ShakespeareTranslatorApiGateway {

    override fun translate(stringToTranslate: String): String {
        val response = restTemplate.exchange(
            getEndpoint(endpoint, stringToTranslate),
            POST,
            HttpEntity<Result>(HttpHeaders()),
            Result::class.java
        )
        return response.body!!.contents.translated
    }

    private fun getEndpoint(endpoint: String, stringToTranslate: String): String {
        val encodedString = URLEncoder.encode(stringToTranslate, "utf-8")
        return "$endpoint?text=$encodedString"
    }

    data class Result(
        val contents: Contents
    ) {
        data class Contents(
            val translated: String
        )
    }

}