package com.nfremont.core

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonTypeInfo
import io.ktor.client.statement.*

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
data class JsonBaseResponse<JSON_TYPE>(
    val httpResponse: HttpResponse? = null,
    val response: JSON_TYPE,
)