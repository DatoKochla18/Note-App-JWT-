package com.example.notesapp

import android.app.Application
import com.example.notesapp.core.getCoreModules
import com.example.notesapp.feature.login.getLoginModules
import com.example.notesapp.feature.note.getHomeModules
import com.example.notesapp.feature.profile.getProfileModules
import com.example.notesapp.feature.register.getRegisterModule
import com.example.notesapp.feature.splash.getSplashModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

fun getAllModules(): List<Module> {
    return getCoreModules() +
            getSplashModules() +
            getLoginModules() +
            getRegisterModule() +
            getProfileModules() +
            getHomeModules()
}


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(getAllModules())
        }
    }
}