package com.example.filmsviewapp.ui.base.recler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseCell {
    abstract val data: Any
    abstract val layout: Int
    abstract fun bind(view: View)

    var position: Int = 0
    open var identifier: Long? = null
    open var contentHash: Long? = null

    open fun getIdentifier(): Long {
        if (identifier != null) {
            return identifier!!
        }

        return data.hashCode().toLong()
    }

    open fun getContentHash(): Long {
        if (contentHash != null) {
            return contentHash!!
        }

        return data.hashCode().toLong()
    }
}