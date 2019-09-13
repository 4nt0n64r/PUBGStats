package com.a4nt0n64r.cahetest.di.component

import com.a4nt0n64r.cahetest.data.source.db.MyDatabase
import com.a4nt0n64r.cahetest.data.source.db.PlayerDao
import com.a4nt0n64r.cahetest.di.module.AppModule
import com.a4nt0n64r.cahetest.di.module.DataBaseModule
import com.a4nt0n64r.cahetest.di.module.PresenterModule
import com.a4nt0n64r.cahetest.di.module.RepositoryModule
import com.a4nt0n64r.cahetest.domain.repository.Repository
import com.a4nt0n64r.cahetest.ui.MainActivity
import com.a4nt0n64r.cahetest.ui.base.Presenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, PresenterModule::class, DataBaseModule::class, RepositoryModule::class])
interface CoreComponent {
    fun inject(target: MainActivity)
    fun inject(target: MyDatabase)
    fun inject(target: Repository)
    fun inject(target: PlayerDao)
}
