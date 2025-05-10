package com.example.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SportDto(
    @Json(name = "key")
    val key: String?,

    @Json(name = "group")
    val group: String?,

    @Json(name = "title")
    val title: String?,

    @Json(name = "description")
    val description: String?,

    @Json(name = "active")
    val active: Boolean?,

    @Json(name = "has_outrights")
    val hasOutrights: Boolean?
)