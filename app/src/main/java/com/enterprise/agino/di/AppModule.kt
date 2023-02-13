package com.enterprise.agino.di

import android.content.Context
import com.enterprise.agino.BuildConfig
import com.enterprise.agino.common.Constants.BASE_URL
import com.enterprise.agino.common.interceptors.ApiInterceptor
import com.enterprise.agino.common.interceptors.NetworkInterceptor
import com.enterprise.agino.data.remote.api.FarmService
import com.enterprise.agino.data.remote.api.FieldService
import com.enterprise.agino.data.remote.api.SensorService
import com.enterprise.agino.data.remote.api.UserService
import com.google.gson.Gson
import com.tomtom.sdk.search.reversegeocoder.ReverseGeocoder
import com.tomtom.sdk.search.reversegeocoder.online.OnlineReverseGeocoder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGson() = Gson()


    @Provides
    @Singleton
    fun provideReverseDecoder(@ApplicationContext context: Context): ReverseGeocoder =
        OnlineReverseGeocoder.create(context, com.enterprise.agino.common.Constants.MAP_KEY)

    @Provides
    @Singleton
    fun provideFarmRepository(
        farmService: FarmService,
        reverseGeocoder: ReverseGeocoder
    ) = com.enterprise.agino.data.repository.FarmRepository(
        farmService,
        Dispatchers.IO,
        reverseGeocoder
    )


    @Provides
    @Singleton
    fun provideFarmService(retrofit: Retrofit): FarmService =
        retrofit.create(FarmService::class.java)

    @Provides
    @Singleton
    fun provideFieldService(retrofit: Retrofit): FieldService =
        retrofit.create(FieldService::class.java)


    @Provides
    @Singleton
    fun provideSensorService(retrofit: Retrofit): SensorService =
        retrofit.create(SensorService::class.java)

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun providesHttpClient(
        @ApplicationContext context: Context,
        // TODO: uncomment this once you have implemented the interceptor
//        authTokenInterceptor: AuthTokenInterceptor
    ): OkHttpClient {

        val builder = OkHttpClient.Builder()
            .addInterceptor(NetworkInterceptor(context))
            .addInterceptor(ApiInterceptor())
//            .addInterceptor(authTokenInterceptor)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            logging.redactHeader("Authorization")
            builder.addInterceptor(logging)
        } else {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
            logging.redactHeader("Authorization")
            builder.addInterceptor(logging)
        }

        return builder.build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
}