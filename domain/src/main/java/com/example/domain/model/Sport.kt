package com.example.domain.model

import com.example.network.dto.SportDto

data class Sport(
    val key: String,
    val title: String,
    val description: String
)

fun SportDto.toDomain(): Sport = Sport(
    key = key.orEmpty(),
    title = title.orEmpty(),
    description = description.orEmpty()
)
