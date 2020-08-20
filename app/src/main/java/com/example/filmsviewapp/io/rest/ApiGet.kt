package com.example.filmsviewapp.io.rest

import com.google.gson.annotations.Since
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiGet {

    @GET("/3/movie/popular")
    fun loadFilms(
        @Query("page") page: Int,
        @Query("api_key") apiKay: String = "ce7e4b76f331ffebd1b22d7243e68775"
    ) : Single<FilmsResponse>

}
