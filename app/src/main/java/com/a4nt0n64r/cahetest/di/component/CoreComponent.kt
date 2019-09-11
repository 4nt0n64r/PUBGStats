package com.a4nt0n64r.cahetest.di.component

import com.a4nt0n64r.cahetest.di.module.AppModule
import com.a4nt0n64r.cahetest.di.module.DataBaseModule
import com.a4nt0n64r.cahetest.di.module.PresenterModule
import com.a4nt0n64r.cahetest.domain.repository.Repository
import com.a4nt0n64r.cahetest.ui.MainActivity
import com.a4nt0n64r.cahetest.ui.base.Presenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, PresenterModule::class,DataBaseModule::class])
interface CoreComponent {
    fun inject(target: MainActivity)
    fun inject(target: Presenter)
    fun inject(target: Repository)
}
