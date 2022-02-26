package com.nfremont.core

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import org.koin.dsl.module

val httpClientModule = module {
    single<HttpClient> {
        HttpClient(CIO) {
            Logging {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("HttpClient: $message")
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
    }
}