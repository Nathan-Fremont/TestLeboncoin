package com.nfremont.core

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class QueryParameter(
    val key: String,
    val value: String
)

enum class RequestMethod {
    GET,
    POST
}

interface IDataSource : KoinComponent

abstract class BaseDataSource<REQUEST, RESPONSE>(
    val serviceUrl: String
) : IDataSource {
    val mapper: ObjectMapper by inject()

    var client = HttpClient(CIO) {
        Logging {
            logger = object : Logger {
                override fun log(message: String) {
                    println("${getDataSourceName()}: $message")
                }
            }
        }
        ResponseObserver { response ->
            println(response)
        }
        expectSuccess = false
        engine {
            requestTimeout = 10_000
        }
    }

    abstract fun getDataSourceName(): String

    @Throws(
        RedirectResponseException::class,
        ClientRequestException::class,
        ServerResponseException::class
    )
    abstract suspend fun execute(request: REQUEST): JsonBaseResponse<RESPONSE>

    open fun prepareRequestBuilder(builder: HttpRequestBuilder): HttpRequestBuilder {
        return builder
    }

    @Throws(
        RedirectResponseException::class,
        ClientRequestException::class,
        ServerResponseException::class
    )
    protected suspend inline fun <reified REQUEST, reified RESPONSE> doPost(jsonBody: REQUEST? = null): JsonBaseResponse<RESPONSE> {
        val response: HttpResponse = client.post(
            Url(BuildConfig.API_HOST + serviceUrl)
        ) {
            prepareRequestBuilder(this)
            if (jsonBody != null) {
                body = Json.encodeToString(jsonBody)
            }
        }

        val responseToString = response.receive<String>()
        val convertedResponse = mapper.readValue<RESPONSE>(responseToString)
        return JsonBaseResponse(
            httpResponse = response,
            response = convertedResponse
        )
    }

    @Throws(
        RedirectResponseException::class,
        ClientRequestException::class,
        ServerResponseException::class
    )
    protected suspend inline fun <reified RESPONSE> doGet(queryParameters: List<QueryParameter> = listOf()): JsonBaseResponse<RESPONSE> {
        val response: HttpResponse = client.get(
            Url(BuildConfig.API_HOST + serviceUrl)
        ) {
            prepareRequestBuilder(this)
            queryParameters.forEach { queryParam ->
                parameter(queryParam.key, queryParam.value)
            }
        }

        val responseToString = response.receive<String>()
        val convertedResponse = mapper.readValue<RESPONSE>(responseToString)
        return JsonBaseResponse(
            httpResponse = response,
            response = convertedResponse
        )
    }
}