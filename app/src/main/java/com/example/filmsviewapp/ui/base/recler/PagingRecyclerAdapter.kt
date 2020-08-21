package com.example.filmsviewapp.ui.base.recler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingData
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class PagingRecyclerAdapter() : RecyclerView.Adapter<BaseViewHolder<BaseCell>>() {

    companion object {
        val CozyDiffCallback = object : DiffUtil.ItemCallback<BaseCell>() {
            override fun areItemsTheSame(oldItem: BaseCell, newItem: BaseCell): Boolean {
                return oldItem.getIdentifier() == newItem.getIdentifier()
            }

            override fun areContentsTheSame(oldItem: BaseCell, newItem: BaseCell): Boolean {
                return oldItem.getContentHash() == newItem.getContentHash()
            }

            override fun getChangePayload(oldItem: BaseCell, newItem: BaseCell): Any? {
                return newItem
            }
        }
    }

    var differ = AsyncPagingDataDiffer(CozyDiffCallback, AdapterListUpdateCallback(this))


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<BaseCell> {
        return BaseViewHolder( LayoutInflater.from(parent.context).inflate(
            viewType,
            parent,
            false
        ))
    }

    override fun getItemCount(): Int {
        return differ.itemCount
    }

    override fun onBindViewHolder(holder: BaseViewHolder<BaseCell>, position: Int) {
        differ.getItem(position)?.position = position
        differ.getItem(position)?.bind(holder.itemView)

    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<BaseCell>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        differ.getItem(position)?.position = position
        differ.getItem(position)?.bind(holder.itemView)

    }


    fun submitData(lifecycle: Lifecycle, pagingData: PagingData<BaseCell>) {
        differ.submitData(lifecycle, pagingData)
    }

    suspend fun submitData(pagingData: PagingData<BaseCell>) {
        differ.submitData(pagingData)
    }

    fun addLoadStateListener(listener: (CombinedLoadStates) -> Unit) {
        differ.addLoadStateListener(listener)
    }

    fun withLoadStateFooter(
        footer: LoadStateAdapter<*>
    ): ConcatAdapter {
        addLoadStateListener { loadStates ->
            footer.loadState = loadStates.append
        }
        return ConcatAdapter(this, footer)
    }

    fun withLoadStateHeaderAndFooter(
        header: LoadStateAdapter<*>,
        footer: LoadStateAdapter<*>
    ): ConcatAdapter {
        addLoadStateListener { loadStates ->
            header.loadState = loadStates.prepend
            footer.loadState = loadStates.append
        }
        return ConcatAdapter(header, this, footer)
    }


    override fun getItemViewType(position: Int): Int {
           return differ.getItem(position)!!.layout
    }
}
