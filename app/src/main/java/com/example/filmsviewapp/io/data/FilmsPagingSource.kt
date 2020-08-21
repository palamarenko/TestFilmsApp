package com.example.filmsviewapp.io.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava2.RxPagingSource
import androidx.paging.rxjava2.flowable
import com.example.filmsviewapp.io.dagger.filmsInteractor
import com.example.filmsviewapp.io.rest.FilmDto
import com.example.filmsviewapp.ui.base.recler.BaseCell
import com.example.filmsviewapp.ui.base.utils.setSchedulers
import com.example.filmsviewapp.ui.list.FilmCell
import io.reactivex.Flowable
import io.reactivex.Single

class FilmsPagingSource(val filmsInteractor: FilmsInteractor) : RxPagingSource<Int, FilmDto>() {


    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, FilmDto>> {
        return filmsInteractor.loadFilms(page = params.key ?: 1)
            .map {
                val nextKey =
                    if ((params.key ?: 1) + 1 >= it.totalPages) null else (params.key ?: 1) + 1
                LoadResult.Page(it.list, null, nextKey) as LoadResult<Int, FilmDto>
            }
            .onErrorReturn { return@onErrorReturn LoadResult.Error<Int, FilmDto>(it) }
            .setSchedulers()
    }
}


class FilmsPagingRepository(val filmsInteractor: FilmsInteractor) {

    fun loadPagingData(): Flowable<PagingData<FilmDto>> {
        val pager = Pager(PagingConfig(10), pagingSourceFactory = {
            return@Pager FilmsPagingSource(filmsInteractor)
        })

        return pager.flowable
    }
}