package com.insiderser.popularmovies.util

import javax.inject.Inject

interface InputValidator {
    fun isSearchQueryValid(query: String): Boolean
}

class InputValidatorImpl @Inject constructor() : InputValidator {

    override fun isSearchQueryValid(query: String): Boolean {
        return query.trim().length >= 2
    }
}
