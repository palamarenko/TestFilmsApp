package com.example.filmsviewapp.io.dagger

import android.content.Context
import androidx.room.Room
import com.example.filmsviewapp.App
import com.example.filmsviewapp.io.data.FilmInteractorImpl
import com.example.filmsviewapp.io.data.FilmsInteractor
import com.example.filmsviewapp.io.data.FilmsPagingRepository
import com.example.filmsviewapp.io.rest.ApiFactory
import com.example.filmsviewapp.io.rest.ApiGet
import com.example.filmsviewapp.io.rest.Rest
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Component(modules = [(AppModule::class), (DiModule::class)])
@Singleton
interface AppComponent {

    val rest: ApiGet
    val applicationContext: Context
    val filmsInteractor : FilmsInteractor
    val pagingFilmsRepository : FilmsPagingRepository

}


val appComponent: AppComponent = App.component
val filmsInteractor = appComponent.filmsInteractor
val pagingFilmsRepository = appComponent.pagingFilmsRepository


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

    @Provides
    @Singleton
    internal fun provideFilmsPagingRepository(interactor: FilmsInteractor): FilmsPagingRepository {
        return FilmsPagingRepository(interactor)
    }
}


@Module
class AppModule(private val appContext: Context) {

    @Provides
    internal fun provideContext(): Context {
        return appContext
    }
}