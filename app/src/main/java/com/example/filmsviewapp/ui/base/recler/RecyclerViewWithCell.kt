package com.example.filmsviewapp.ui.base.recler

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.*
import com.example.filmsviewapp.R
import kotlinx.android.synthetic.main.view_error_state.view.*
import kotlinx.android.synthetic.main.view_recycler_view.view.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RecyclerViewWithCell : FrameLayout {

    private val cozyAdapter = PagingRecyclerAdapter()

    private lateinit var view: View


    constructor(context: Context) : this(context, null) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }


    private fun init(context: Context, attrs: AttributeSet?) {
        view = View.inflate(context, R.layout.view_recycler_view, null)

        addView(view)
        view.baseRecycler.layoutManager = LinearLayoutManager(this.context)

        view.srRefresh.isRefreshing = false
        view.srRefresh.isEnabled = false
    }

    fun setRefreshing(refreshing: Boolean) {
        view.srRefresh.isEnabled = refreshing
    }

    fun refreshListener(refreshListener: () -> Unit) {
        setRefreshing(true)
        view.srRefresh.setOnRefreshListener {
            refreshListener.invoke()
        }
    }

    fun setRefreshForPagingEnable() {
        refreshListener {
            pagingRefresh()
        }
    }


    fun refreshHide() {
        view.srRefresh.isRefreshing = false
    }

    fun refreshShow() {
        view.srRefresh.isRefreshing = true
    }


    fun getRecyclerView(): RecyclerView {
        return view.baseRecycler
    }

    fun firstProgressEnable() {
        view.progress.visibility = View.VISIBLE
    }



    @OptIn(ExperimentalPagingApi::class)
    fun submitData(
        lifecycle: Lifecycle,
        pagingData: PagingData<BaseCell>,
        footer: PagingLoadState? = DefaultLoaderView {
            pagingRetry()
        },
        header: PagingLoadState? = DefaultLoaderViewHeader {
            pagingRetry()
        },
        errorCallBack: (LoadState.Error) -> Unit = {}
    ) {

        when{
            footer != null && header !=null ->{
                view.baseRecycler.adapter =
                    cozyAdapter.withLoadStateHeaderAndFooter(footer = footer, header = header)
            }
            footer !=null ->{
                view.baseRecycler.adapter =
                    cozyAdapter.withLoadStateFooter(footer = footer)
            }
            else ->{
                view.baseRecycler.adapter = cozyAdapter
            }
        }


        cozyAdapter.submitData(lifecycle, pagingData)
        cozyAdapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.NotLoading) {
                view.baseRecycler.visibility = View.VISIBLE
            }
            if (view.srRefresh.isRefreshing) {
                view.srRefresh.isRefreshing = loadState.source.refresh is LoadState.Loading
            } else {
                view.progress.visibility =
                    if (loadState.source.refresh is LoadState.Loading) View.VISIBLE else View.GONE
            }

            if (loadState.source.refresh is LoadState.Error) {
                view.flError.visibility = View.VISIBLE
                errorCallBack(loadState.source.refresh as LoadState.Error)
                view.baseRecycler.visibility = View.GONE
            } else {
                view.flError.visibility = View.GONE
            }
        }

        lifecycle.coroutineScope.launch {
            cozyAdapter.differ.dataRefreshFlow.collect {
                if (it) {
                    view.flPlaceHolder.visibility = View.VISIBLE
                } else {
                    view.flPlaceHolder.visibility = View.GONE
                }
            }
        }

    }


    fun setErrorState(view: View) {
        view.flError.addView(view)
    }

    fun setErrorState(settings: DefaultLoaderViewSettings? = null) {
        val view = View.inflate(context, R.layout.view_error_state, null)
        if (settings != null) {
            view.btRetry.backgroundTintList = ColorStateList.valueOf(settings.mainColor)
            view.btRetry.setTextColor(settings.textColor)
            view.btRetry.text = settings.retryButtonText
        }

        view.btRetry.setOnClickListener {
            pagingRetry()
        }

        this.view.flError.addView(view)
    }

    fun pagingRefresh() {
        cozyAdapter.differ.refresh()
    }

    fun pagingRetry() {
        cozyAdapter.differ.retry()
    }
}