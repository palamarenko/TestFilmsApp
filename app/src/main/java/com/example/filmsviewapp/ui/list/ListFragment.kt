package com.example.filmsviewapp.ui.list

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat.getColor
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.filmsviewapp.R
import com.example.filmsviewapp.ui.base.BaseFragment
import com.example.filmsviewapp.ui.base.recler.DefaultLoaderViewSettings
import com.example.filmsviewapp.ui.base.utils.listen
import com.example.filmsviewapp.ui.base.utils.setSchedulers
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : BaseFragment<ListViewModel>() {

    override val layout = R.layout.fragment_list

    override fun onStartScreen() {
        super.onStartScreen()
        vm().loadPagingData().listen(this) {
            recycler.submitData(lifecycle, it)
        }

        vm().listenNavigate().listen(this){
            findNavController().navigate(R.id.singlePage, bundleOf(Pair(DATA,it)))
        }

        ReactiveNetwork
            .observeNetworkConnectivity(context)
            .setSchedulers()
            .subscribe { if(it.available()){ recycler.pagingRetry() } }

        recycler.setRefreshForPagingEnable()
        recycler.firstProgressEnable()
        recycler.setErrorState(
            DefaultLoaderViewSettings(
                retryButtonText = getString(R.string.retry),
                mainColor = Color.BLACK,
                textColor = Color.WHITE)
        )

    }


}