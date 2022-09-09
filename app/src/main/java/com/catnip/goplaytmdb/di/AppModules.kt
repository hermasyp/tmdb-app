package com.catnip.goplaytmdb.di

import com.catnip.goplaytmdb.data.local.AppDatabase
import com.catnip.goplaytmdb.data.local.datasource.CacheDataSource
import com.catnip.goplaytmdb.data.local.datasource.CacheDataSourceImpl
import com.catnip.goplaytmdb.data.network.MovieApiService
import com.catnip.goplaytmdb.data.network.datasource.MovieApiDataSource
import com.catnip.goplaytmdb.data.network.datasource.MovieApiDataSourceImpl
import com.catnip.goplaytmdb.data.repository.MovieRepository
import com.catnip.goplaytmdb.data.repository.MovieRepositoryImpl
import com.catnip.goplaytmdb.domain.usecases.GetHeaderDataUseCase
import com.catnip.goplaytmdb.domain.usecases.GetSectionDataUseCase
import com.catnip.goplaytmdb.presentation.ui.homefeeds.HomeFeedsViewModel
import com.catnip.goplaytmdb.presentation.ui.home.HomeViewModel
import com.catnip.goplaytmdb.presentation.ui.mylist.MyListViewModel
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
object AppModules {

    fun getModules(): List<Module> = listOf(
        localModule, networkModule, dataSource, repository, useCase, viewModel, common
    )

    private val localModule = module {
        single { get<AppDatabase>().cacheDao() }
        single { AppDatabase.create(androidContext()) }
    }

    private val networkModule = module {
        single { ChuckerInterceptor.Builder(androidContext()).build() }
        single { MovieApiService.invoke(get()) }
    }

    private val dataSource = module {
        single<MovieApiDataSource> { MovieApiDataSourceImpl(get()) }
        single<CacheDataSource> { CacheDataSourceImpl(get()) }
    }

    private val repository = module {
        single<MovieRepository> { MovieRepositoryImpl(get(), get(), get()) }
    }

    private val useCase = module {
        single { GetSectionDataUseCase(get(), Dispatchers.IO) }
        single { GetHeaderDataUseCase(get(), Dispatchers.IO) }

    }

    private val viewModel = module {
        viewModelOf(::HomeFeedsViewModel)
        viewModelOf(::HomeViewModel)
        viewModelOf(::MyListViewModel)
    }

    private val common = module {
        single { Gson() }
    }


}