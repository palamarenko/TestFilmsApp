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
import com.example.filmsviewapp.io.data.FilmsPagingSource
import com.example.filmsviewapp.io.rest.FilmDto
import com.example.filmsviewapp.ui.base.BaseViewModel
import ua.palamarenko.cozyandroid2.CozyCell
import ua.palamarenko.cozyandroid2.base_fragment.navigation.tasks.taskNavigate
import ua.palamarenko.cozyandroid2.tools.LOG_EVENT

const val DATA = "DATA"

class ListViewModel : BaseViewModel() {



    fun loadPagingData(): LiveData<PagingData<CozyCell>> {
        val pager = Pager(PagingConfig(10), pagingSourceFactory = {
            return@Pager FilmsPagingSource()
        })

       return pager.flowable.map {
            it.map { FilmCell(it, this) as CozyCell }
        }.toLiveData()
    }

    fun navigateToSingle(dto : FilmDto){
        taskNavigate(R.id.singlePage, bundleOf(Pair(DATA,dto)))
    }
}