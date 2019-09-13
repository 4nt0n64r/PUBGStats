package com.a4nt0n64r.cahetest.di.component

import com.a4nt0n64r.cahetest.data.source.db.MyDatabase
import com.a4nt0n64r.cahetest.data.source.db.PlayerDao
import com.a4nt0n64r.cahetest.di.module.*
import com.a4nt0n64r.cahetest.domain.repository.Repository
import com.a4nt0n64r.cahetest.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, PresenterModule::class, DataBaseModule::class, RepositoryModule::class, NetworkModule::class])
interface CoreComponent {
    fun inject(target: MainActivity)
    fun inject(target: MyDatabase)
    fun inject(target: Repository)
    fun inject(target: PlayerDao)
}
