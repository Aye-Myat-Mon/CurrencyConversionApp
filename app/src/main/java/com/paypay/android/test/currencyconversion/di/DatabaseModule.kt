package com.paypay.android.test.currencyconversion.di

import android.content.Context
import androidx.room.Room
import com.paypay.android.test.currencyconversion.data.db.AppDatabase
import com.paypay.android.test.currencyconversion.data.db.CurrencyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by ayemyatmon on 04,January,2022
 */
@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app.db"
        ).build()
    }

    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): CurrencyDao {
        return appDatabase.currencyDao()
    }
}