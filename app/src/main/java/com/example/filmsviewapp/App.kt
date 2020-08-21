package com.example.filmsviewapp

import android.app.Application
import com.example.filmsviewapp.io.dagger.AppComponent
import com.example.filmsviewapp.io.dagger.AppModule
import com.example.filmsviewapp.io.dagger.DaggerAppComponent
import io.reactivex.plugins.RxJavaPlugins

class App : Application() {


    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
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
