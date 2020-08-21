package com.example.filmsviewapp.ui.list

import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava2.flowable
import com.example.filmsviewapp.R
import com.example.filmsviewapp.io.dagger.pagingFilmsRepository
import com.example.filmsviewapp.io.data.FilmsPagingSource
import com.example.filmsviewapp.io.rest.FilmDto
import com.example.filmsviewapp.ui.base.recler.BaseCell
import com.example.filmsviewapp.ui.base.view_model.BaseViewModel

const val DATA = "DATA"

class ListViewModel : BaseViewModel() {

    private val navigateLiveData = MutableLiveData<FilmDto>()


    fun loadPagingData(): LiveData<PagingData<BaseCell>> {

        return pagingFilmsRepository.loadPagingData().map {
            it.map { FilmCell(it, this) as BaseCell }
        }.toLiveData()
    }

    fun listenNavigate(): LiveData<FilmDto> {
        return navigateLiveData
    }

    fun navigateToSingle(dto: FilmDto) {
        navigateLiveData.postValue(dto)
    }
}