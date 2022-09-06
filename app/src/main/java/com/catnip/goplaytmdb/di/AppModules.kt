package com.catnip.goplaytmdb.di

import com.catnip.goplaytmdb.data.local.CacheDatabase
import com.catnip.goplaytmdb.data.local.datasource.CacheDataSource
import com.catnip.goplaytmdb.data.local.datasource.CacheDataSourceImpl
import com.catnip.goplaytmdb.data.network.MovieApiService
import com.catnip.goplaytmdb.data.network.datasource.MovieApiDataSource
import com.catnip.goplaytmdb.data.network.datasource.MovieApiDataSourceImpl
import com.catnip.goplaytmdb.data.repository.MovieRepository
import com.catnip.goplaytmdb.data.repository.MovieRepositoryImpl
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
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
        single { get<CacheDatabase>().cacheDao() }
        single { CacheDatabase.create(androidContext()) }
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


    }

    private val viewModel = module {

    }

    private val common = module {
        single { Gson() }
    }


}