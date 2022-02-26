package com.nfremont.core

import com.fasterxml.jackson.databind.ObjectMapper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BaseRepository<RESPONSE, JSON_RESPONSE>(
    private val dataSource: IDataSource,
    private val cacheManager: CacheManager
) : KoinComponent {
    val mapper: ObjectMapper by inject()

    abstract suspend fun transformToEntity(jsonResponse: JSON_RESPONSE): RESPONSE
}