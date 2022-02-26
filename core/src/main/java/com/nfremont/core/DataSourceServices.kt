package com.nfremont.core

enum class DataSourceServices(
    val cacheKey: String?,
    val url: String
) {
    LOGIN(cacheKey = "LOGIN", url = "/auth/login"),
    REFRESH(cacheKey = "REFRESH", url = "/auth/refresh"),

    // MANGA
    MANGA_LIST(cacheKey = "MANGA_LIST", url = "/manga"),
}