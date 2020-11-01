package com.insiderser.popularmovies.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    private val creators: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = creators[modelClass]?.get() as T?
        ?: throw Error(
            "Cannot create an instance of ${modelClass.name} using Dagger. " +
                "Did you forget to add it into dagger graph?"
        )
}
