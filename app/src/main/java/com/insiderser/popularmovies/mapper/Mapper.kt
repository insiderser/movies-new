package com.insiderser.popularmovies.mapper

interface Mapper<in From, out To> {

    fun map(from: From): To
}
