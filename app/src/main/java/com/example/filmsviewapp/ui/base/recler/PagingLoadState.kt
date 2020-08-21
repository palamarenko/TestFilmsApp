package com.example.filmsviewapp.ui.base.recler

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.example.filmsviewapp.R
import kotlinx.android.synthetic.main.cell_default_loader.view.*

abstract class PagingLoadState :
    LoadStateAdapter<BaseViewHolder<BaseCell>>() {

    abstract val layout : Int
    abstract fun bind(view: View, loadState: LoadState)


    override fun onBindViewHolder(holder: BaseViewHolder<BaseCell>, loadState: LoadState) {
        bind(holder.itemView,loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): BaseViewHolder<BaseCell> {
        val view = View.inflate(parent.context, layout, null)
        val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        view.layoutParams = lp
        return BaseViewHolder(view)
    }

}


class DefaultLoaderViewSettings(val mainColor : Int, val textColor : Int, val retryButtonText : String)

class DefaultLoaderView(val settings : DefaultLoaderViewSettings? = null, val retryCallBack : () -> Unit ={ }) : PagingLoadState(){

    override val layout = R.layout.cell_default_loader

    override fun bind(view: View, loadState: LoadState) {

        if(settings!=null){
            view.progress.indeterminateDrawable
                .setColorFilter(settings.mainColor, PorterDuff.Mode.SRC_IN);
            view.btRetry.backgroundTintList = ColorStateList.valueOf(settings.mainColor)
            view.btRetry.setTextColor(settings.textColor)
            view.btRetry.text = settings.retryButtonText
        }


        if(loadState.endOfPaginationReached){
            view.progress.visibility = View.GONE
            view.btRetry.visibility = View.GONE
            return
        }
        view.progress.visibility = if(loadState is LoadState.Loading) View.VISIBLE else View.GONE
        view.btRetry.visibility = if(loadState !is LoadState.Loading) View.VISIBLE else View.GONE
        view.btRetry.setOnClickListener {
            retryCallBack.invoke()
        }
    }

}

class DefaultLoaderViewHeader(val settings : DefaultLoaderViewSettings? = null, val retryCallBack : () -> Unit ={ }) : PagingLoadState(){

    override val layout = R.layout.cell_default_loader

    override fun bind(view: View, loadState: LoadState) {

        if(settings!=null){
            view.progress.indeterminateDrawable
                .setColorFilter(settings.mainColor, PorterDuff.Mode.SRC_IN);
            view.btRetry.backgroundTintList = ColorStateList.valueOf(settings.mainColor)
            view.btRetry.setTextColor(settings.textColor)
            view.btRetry.text = settings.retryButtonText
        }


        if(loadState.endOfPaginationReached){
            view.progress.visibility = View.GONE
            view.btRetry.visibility = View.GONE
            return
        }
        view.progress.visibility = if(loadState is LoadState.Loading) View.VISIBLE else View.GONE
        view.btRetry.visibility = if(loadState !is LoadState.Loading) View.VISIBLE else View.GONE
        view.btRetry.setOnClickListener {
            retryCallBack.invoke()
        }
    }

}