package com.test.manvitha.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.test.manvitha.network.ApiService
import com.test.manvitha.repository.DoctorSearchRepository
import com.test.manvitha.repository.DoctorSearchRepositoryImpl
import com.test.manvitha.viewmodel.ByLocationTabViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {
    single {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        Retrofit.Builder()
            .baseUrl("https://eyedoclocatorashuat.eyemedvisioncare.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
            .create(ApiService::class.java)
    }

    single<DoctorSearchRepository> { DoctorSearchRepositoryImpl(get()) }

    viewModel { ByLocationTabViewModel(get()) }
}