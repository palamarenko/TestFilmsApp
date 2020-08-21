package com.example.filmsviewapp.io.data

import com.example.filmsviewapp.io.rest.ApiGet
import com.example.filmsviewapp.io.rest.FilmDto
import com.example.filmsviewapp.io.rest.FilmsResponse
import io.reactivex.Single

interface FilmsInteractor{
    fun loadFilms(page : Int) : Single<FilmsResponse>

}


class FilmInteractorImpl(val rest : ApiGet) : FilmsInteractor{
    override fun loadFilms(page: Int): Single<FilmsResponse> {
        return rest.loadFilms(page)
    }

}