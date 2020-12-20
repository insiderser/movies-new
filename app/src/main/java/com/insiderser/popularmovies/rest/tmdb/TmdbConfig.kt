package com.insiderser.popularmovies.rest.tmdb

object TmdbConfig {

    const val PAGE_SIZE = 20

    /**
     * Http code: 422 Unprocessable Entity.
     *
     * Signals that the requested page number is out of range.
     */
    const val PAGE_OUT_OF_RANGE_CODE = 422
}
