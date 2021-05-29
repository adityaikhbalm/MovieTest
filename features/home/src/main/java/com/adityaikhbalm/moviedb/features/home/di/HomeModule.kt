package com.adityaikhbalm.moviedb.features.home.di

import com.adityaikhbalm.moviedb.features.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel { HomeViewModel(get(), get(), get()) }
}
