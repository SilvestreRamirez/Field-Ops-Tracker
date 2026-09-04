package com.deskvestre.fieldopstracker.di

import com.deskvestre.fieldopstracker.data.repository.FieldRecordRepositoryImpl
import com.deskvestre.fieldopstracker.data.repository.TokenRepositoryImpl
import com.deskvestre.fieldopstracker.domain.repository.FieldRecordRepository
import com.deskvestre.fieldopstracker.domain.repository.TokenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideFieldRecordRepository(repositoryImpl: FieldRecordRepositoryImpl): FieldRecordRepository

    @Binds
    @Singleton
    abstract fun provideTokenRepository(repositoryImpl: TokenRepositoryImpl): TokenRepository
}