package com.example.filmsviewapp.io.data

import androidx.paging.rxjava2.RxPagingSource
import com.example.filmsviewapp.io.dagger.filmsInteractor
import com.example.filmsviewapp.io.rest.FilmDto
import com.example.filmsviewapp.ui.base.utils.setSchedulers
import io.reactivex.Single

class FilmsPagingSource() : RxPagingSource<Int, FilmDto>() {


    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, FilmDto>> {
        return filmsInteractor.loadFilms(page = params.key ?: 1)
            .map {
                val nextKey = if((params.key ?: 1) + 1 >= it.totalPages) null else (params.key ?: 1) + 1
                LoadResult.Page(it.list, null, nextKey) as LoadResult<Int, FilmDto> }
            .onErrorReturn { return@onErrorReturn LoadResult.Error<Int, FilmDto>(it) }
            .setSchedulers()
    }
}