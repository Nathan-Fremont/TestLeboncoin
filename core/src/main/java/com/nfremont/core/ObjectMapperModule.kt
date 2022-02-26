package com.nfremont.core

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.koin.dsl.module

val objectMapperModule = module {
    single<ObjectMapper> {
        JsonMapper.builder()
            .addModule(
                KotlinModule.Builder()
                    .enable(
                        KotlinFeature.StrictNullChecks,
                    )
                    .build()
            )
            .build()
    }
}