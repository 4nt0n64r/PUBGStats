package com.a4nt0n64r.cahetest.di.module

import com.a4nt0n64r.cahetest.ui.MainPresenterImpl
import com.a4nt0n64r.cahetest.ui.base.Presenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {
    @Provides
    @Singleton
    fun providePresenter(): Presenter = MainPresenterImpl()
}