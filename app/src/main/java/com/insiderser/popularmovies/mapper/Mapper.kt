package com.insiderser.popularmovies.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.insiderser.popularmovies.util.mapNotNull

interface Mapper<From, To> : (From) -> To {
    override operator fun invoke(from: From): To

    companion object {
        inline fun <From, To> build(crossinline mapper: From.() -> To) = object : Mapper<From, To> {
            override fun invoke(from: From): To = mapper.invoke(from)
        }
    }
}

fun <From, To> Mapper<From, To>.asListMapper(): Mapper<List<From>, List<To>> =
    Mapper.build { map(this@asListMapper) }

fun <From : Any, To : Any> Mapper<From, To>.asPagingListMapper(): Mapper<PagingData<From>, PagingData<To>> =
    Mapper.build { map { invoke(it) } }

fun <From : Any, To : Any> Mapper<From, To?>.asPagingListMapperNotNull(): Mapper<PagingData<From>, PagingData<To>> =
    Mapper.build { mapNotNull { invoke(it) } }
