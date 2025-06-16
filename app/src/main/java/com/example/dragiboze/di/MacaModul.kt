package com.example.dragiboze.di

import com.example.dragiboze.database.baza.AppDatabase
import com.example.dragiboze.models.api.interfaces.MacApi
import com.example.dragiboze.models.api.interfaces.TakmicarApi
import com.example.dragiboze.models.repository.implemenation.KorisnikRepositoryImpl
import com.example.dragiboze.models.repository.implemenation.KvizRepositoryImpl
import com.example.dragiboze.models.repository.implemenation.MjauRepositoryImpl
import com.example.dragiboze.models.repository.implemenation.TakmicarRepositoryImpl
import com.example.dragiboze.models.repository.interfaces.KorisnikRepository
import com.example.dragiboze.models.repository.interfaces.KvizRepository
import com.example.dragiboze.models.repository.interfaces.MjauRepository
import com.example.dragiboze.models.repository.interfaces.TakmicarRepository
import com.example.meoworld.data.datastore.UserStore

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MacaModul {

    private val json: Json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    @Provides
    @Singleton
    fun bindsMjauRepository(baza: AppDatabase ,api: MacApi): MjauRepository = MjauRepositoryImpl(baza, api)

    @Provides
    @Singleton
    fun bindsTakmicarRepository(baza: AppDatabase , stor: UserStore, tak: TakmicarApi): TakmicarRepository =
        TakmicarRepositoryImpl(baza, stor, tak)

    @Provides
    @Singleton
    fun bindsKorisnikRepository(baza: AppDatabase , stor: UserStore, takmicarRepo: TakmicarRepository): KorisnikRepository =
        KorisnikRepositoryImpl(baza, stor, takmicarRepo)

    @Provides
    @Singleton
    fun bindsKvizRepository(baza: AppDatabase , stor: UserStore, api: MacApi): KvizRepository =
        KvizRepositoryImpl(baza, stor, api)

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor {
                val updatedRequest = it.request().newBuilder()
                    .addHeader("CustomHeader", "CustomValue")
                    .addHeader("x-api-key", "live_rr5UM2mLAiotWcAsPq175VLqSDc3yYDlPJWlXvIi3pr11ThdPs1fXC0Pq4zUrzw1")
                    .build()
                it.proceed(updatedRequest)
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    @Provides
    @Singleton
    fun provideMacaRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Provides
    @Singleton
    fun provideTakmicarRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://rma.finlab.rs/")
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()


    @Provides
    @Singleton
    fun provideMacApi(retrofit: Retrofit): MacApi =
        retrofit.create(MacApi::class.java)

    @Provides
    @Singleton
    fun provideTakmicarApi(retrofit: Retrofit): TakmicarApi =
        retrofit.create(TakmicarApi::class.java)


}