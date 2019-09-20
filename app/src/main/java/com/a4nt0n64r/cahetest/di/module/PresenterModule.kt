package com.a4nt0n64r.cahetest.di.module

import com.a4nt0n64r.cahetest.data.repository.RepoImpl
import com.a4nt0n64r.cahetest.domain.repository.Repository
import com.a4nt0n64r.cahetest.network.NetworkRepository
import com.a4nt0n64r.cahetest.ui.PresenterImpl
import com.a4nt0n64r.cahetest.ui.base.Presenter
import com.a4nt0n64r.cahetest.ui.base.View
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {
    @Provides
    @Singleton
    fun providePresenter(repository: Repository,networkRopository:NetworkRepository): Presenter = PresenterImpl(repository,networkRopository)
}