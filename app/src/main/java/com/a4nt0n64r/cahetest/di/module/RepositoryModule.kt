package com.a4nt0n64r.cahetest.di.module

import com.a4nt0n64r.cahetest.data.repository.MainRepoImpl
import com.a4nt0n64r.cahetest.data.source.db.MyDataBase
import com.a4nt0n64r.cahetest.domain.repository.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(database: MyDataBase):Repository = MainRepoImpl(database)
}