package com.nfremont.core

import io.ktor.client.*

class HttpClientBuilder(
    private val client: HttpClient
) {
    private var requestMethod = RequestMethod.GET
    private var tag: String = "HttpClientBuilder"

    fun get() : HttpClientBuilder {
        requestMethod = RequestMethod.GET
        return this
    }

    fun post() : HttpClientBuilder {
        requestMethod = RequestMethod.POST
        return this
    }

    fun logger(tag: String) : HttpClientBuilder {
        this.tag = tag
        return this
    }

    fun execute() {

    }
}