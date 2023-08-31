package com.samkt.rickyandmorty

import android.app.Application
import com.samkt.rickyandmorty.di.AppModule
import com.samkt.rickyandmorty.di.AppModuleImplementation
import timber.log.Timber

class RickyAndMortyApplication() : Application() {
    companion object {
        lateinit var app: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        initTimber()
        app = AppModuleImplementation()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}
