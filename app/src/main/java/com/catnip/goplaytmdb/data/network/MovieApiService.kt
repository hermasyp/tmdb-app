package com.catnip.goplaytmdb.data.network

import com.catnip.goplaytmdb.BuildConfig
import com.catnip.goplaytmdb.data.network.model.response.MovieResponse
import com.catnip.goplaytmdb.data.network.model.response.MoviesResponse
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/


interface MovieApiService {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovie(@Query("page") page: Int = 1): MoviesResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovie(@Query("page") page: Int = 1): MoviesResponse

    @GET("movie/popular")
    suspend fun getPopularMovie(@Query("page") page: Int = 1): MoviesResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovie(@Query("page") page: Int = 1): MoviesResponse

    @GET("movie/{id_movie}")
    suspend fun getDetailMovie(@Path("id_movie") movieId: Int): MovieResponse

    companion object {
        @JvmStatic
        operator fun invoke(chuckerInterceptor: ChuckerInterceptor): MovieApiService {
            val authInterceptor = Interceptor {
                val originRequest = it.request()
                val newUrl = originRequest.url.newBuilder().apply {
                    addQueryParameter("api_key", BuildConfig.API_KEY)
                    addQueryParameter("language","en-US")
                }.build()
                it.proceed(originRequest.newBuilder().url(newUrl).build())
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(chuckerInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(MovieApiService::class.java)
        }
    }
}