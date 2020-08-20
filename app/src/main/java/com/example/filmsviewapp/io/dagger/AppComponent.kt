package com.example.filmsviewapp.io.dagger

import android.content.Context
import androidx.room.Room
import com.example.filmsviewapp.App
import com.example.filmsviewapp.io.data.FilmInteractorImpl
import com.example.filmsviewapp.io.data.FilmsInteractor
import com.example.filmsviewapp.io.rest.ApiGet
import dagger.Component
import dagger.Module
import dagger.Provides
import ua.palamarenko.cozyandroid2.di.AppModule
import ua.palamarenko.cozyandroid2.rest.ApiFactory
import ua.palamarenko.cozyandroid2.rest.Rest
import javax.inject.Singleton

@Component(modules = [(AppModule::class), (DiModule::class)])
@Singleton
interface AppComponent {

    val rest: ApiGet
    val applicationContext: Context
    val filmsInteractor : FilmsInteractor

}


val appComponent: AppComponent = App.component
val filmsInteractor = appComponent.filmsInteractor


@Module
class DiModule {
    @Provides
    @Singleton
    internal fun provideRest(): ApiGet {
        return Rest(ApiFactory("https://api.themoviedb.org/",ApiGet::class.java)).api
    }


    @Provides
    @Singleton
    internal fun provideUserInteractor(rest: ApiGet): FilmsInteractor {
        return FilmInteractorImpl(rest)
    }
}