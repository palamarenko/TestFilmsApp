package com.example.filmsviewapp.ui.list

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import androidx.paging.LoadState
import com.example.filmsviewapp.R
import com.example.filmsviewapp.ui.base.BaseFragment
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import kotlinx.android.synthetic.main.fragment_list.*
import ua.palamarenko.cozyandroid2.recycler.pagination.CozyDefaultLoaderViewSettings
import ua.palamarenko.cozyandroid2.tools.getColor
import ua.palamarenko.cozyandroid2.tools.listen
import ua.palamarenko.cozyandroid2.tools.setSchedulers


class ListFragment : BaseFragment<ListViewModel>() {

    override val layout = R.layout.fragment_list

    override fun onStartScreen() {
        super.onStartScreen()
        vm().loadPagingData().listen(this) {
            recycler.submitData(lifecycle, it)
        }

        ReactiveNetwork
            .observeNetworkConnectivity(context)
            .setSchedulers()
            .subscribe { if(it.available()){ recycler.pagingRetry() } }

        recycler.setRefreshForPagingEnable()
        recycler.firstProgressEnable()
        recycler.setErrorState(
            CozyDefaultLoaderViewSettings(
                retryButtonText = getString(R.string.retry),
                mainColor = getColor(R.color.colorAccent),
                textColor = Color.WHITE))

    }


}