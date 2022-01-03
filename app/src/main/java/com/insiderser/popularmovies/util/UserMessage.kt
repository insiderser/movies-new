package com.insiderser.popularmovies.util

import java.util.UUID

data class UserMessage(
    val message: String,
    val retry: (() -> Unit)? = null,
    val id: String = UUID.randomUUID().toString(),
)
