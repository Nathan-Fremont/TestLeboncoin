package com.nfremont.coreTest

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.koin.test.KoinTest

fun KoinTest.getMockEngine(requestHandler: MockRequestHandler): HttpClient {
    return HttpClient(MockEngine) {
        expectSuccess = false
        engine {
            addHandler(requestHandler)
        }
    }
}

fun MockRequestHandleScope.respondWithFile200(givenFileName: String): HttpResponseData {
    return respondWithFile(
        givenFileName = givenFileName,
        statusCode = HttpStatusCode.OK,
    )
}

fun MockRequestHandleScope.respondWithFile400(givenFileName: String): HttpResponseData {
    return respondWithFile(
        givenFileName = givenFileName,
        statusCode = HttpStatusCode.BadRequest,
    )
}

fun MockRequestHandleScope.respondWithFile401(givenFileName: String): HttpResponseData {
    return respondWithFile(
        givenFileName = givenFileName,
        statusCode = HttpStatusCode.Unauthorized,
    )
}

fun MockRequestHandleScope.respondWithFile(
    givenFileName: String,
    statusCode: HttpStatusCode = HttpStatusCode.OK,
    headers: Headers = headersOf(),
): HttpResponseData {
    val fileName = "api/${givenFileName.removeSuffix(".json")}.json"
    val fileContent = javaClass.classLoader?.getResourceAsStream(fileName)
    val content = fileContent?.bufferedReader()?.use { it.readText() }
    return respond(
        content = content ?: "",
        status = statusCode,
        headers = headers
    )
}