package com.example.filmsviewapp

import android.app.Application
import com.example.filmsviewapp.io.dagger.AppComponent
import com.example.filmsviewapp.io.dagger.DaggerAppComponent
import io.reactivex.plugins.RxJavaPlugins
import ua.palamarenko.cozyandroid2.CozyLibrary
import ua.palamarenko.cozyandroid2.di.AppModule

class App : Application() {


    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        CozyLibrary.init(this)
        component = buildComponent()
        RxJavaPlugins.setErrorHandler { e ->
            e.printStackTrace()
        }
    }


    private fun buildComponent(): AppComponent {
        return DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

}
