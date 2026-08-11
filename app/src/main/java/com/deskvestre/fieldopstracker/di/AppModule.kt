package com.deskvestre.fieldopstracker.di

import android.content.Context
import androidx.room.Room
import com.deskvestre.fieldopstracker.data.local.AppDatabase
import com.deskvestre.fieldopstracker.data.local.dao.FieldRecordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "field-tracker"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFieldRecordDao(database: AppDatabase): FieldRecordDao {
        return database.fieldRecordDao()
    }
}