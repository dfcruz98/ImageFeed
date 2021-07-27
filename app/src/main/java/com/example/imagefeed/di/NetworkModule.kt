package com.example.imagefeed.di

import com.example.imagefeed.BuildConfig
import com.example.imagefeed.remote.IPhotosRepository
import com.example.imagefeed.remote.PhotosRepository
import com.example.imagefeed.remote.PhotosService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://www.flickr.com/services/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): PhotosService =
        retrofit.create(PhotosService::class.java)

    @Singleton
    @Provides
    fun providePhotosRepository(api: PhotosService): IPhotosRepository = PhotosRepository(api)


    @Singleton
    @Provides
    fun provideInterceptor(): OkHttpClient {
        val builder = OkHttpClient
            .Builder()

        // Logs in the terminal the request/response made
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC

        builder.addInterceptor(logging)

        // Add new interceptor that is going to add new parameters to every request
        builder.addInterceptor(Interceptor { chain ->
            val original = chain.request()
            val originalUrl = original.url

            val url = originalUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.CONSUMER_KEY)
                .addQueryParameter("format", "json")
                .addQueryParameter("nojsoncallback", "1")
                .build()

            val requestBuilder = original.newBuilder().url(url)

            chain.proceed(requestBuilder.build())
        })

        return builder.build()
    }

}