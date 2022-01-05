package com.paypay.android.test.currencyconversion.di

import com.paypay.android.test.currencyconversion.BuildConfig
import com.paypay.android.test.currencyconversion.Constants
import com.paypay.android.test.currencyconversion.data.services.CurrencyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by ayemyatmon on 04,January,2022
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val baseUrl = Constants.BASE_URL

    @Singleton
    @Provides
    internal fun provideCurrencyApi(@Named("NAMING_MAIN_API") retrofit: Retrofit): CurrencyService =
        retrofit.create(CurrencyService::class.java)

    @Singleton
    @Provides
    @Named("NAMING_MAIN_API")
    internal fun provideAuthRetrofit(@Named("NAMING_MAIN_CLIENT") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    @Named("NAMING_MAIN_CLIENT")
    internal fun provideMainClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @Named("NAMING_MAIN_INTERCEPTOR") interceptor: Interceptor
    ): OkHttpClient {

        val builder = OkHttpClient.Builder()
            .addInterceptor(interceptor)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(httpLoggingInterceptor)
        }
        return builder.build()
    }

    @Singleton
    @Provides
    @Named("NAMING_MAIN_INTERCEPTOR")
    internal fun provideMainInterceptor(): Interceptor {

        return Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .method(original.method, original.body)
                .build()

            return@Interceptor chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    internal fun provideLogging(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }
}