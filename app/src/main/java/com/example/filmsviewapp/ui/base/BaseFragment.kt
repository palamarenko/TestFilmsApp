package com.example.filmsviewapp.ui.base

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.filmsviewapp.ui.base.utils.ReflectionUtils
import com.example.filmsviewapp.ui.base.view_model.BaseViewModel


abstract class BaseFragment<T : BaseViewModel> : Fragment() {

    abstract val layout: Int

    private var fragmentView: View? = null
    private var callViewCreated: Boolean = false
    private var callFirstOnResume: Boolean = false


    fun vm(): T {
        val cl: Class<T> = ReflectionUtils.getGenericParameterClass(
            this::class.java,
            0
        ) as Class<T>
        return ViewModelProviders.of(this).get(cl)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(layout, null)
        }
        return fragmentView
    }

    override fun onDestroy() {
        super.onDestroy()
        callViewCreated = false
        callFirstOnResume = false

    }

    override fun onResume() {
        super.onResume()
        if (!callFirstOnResume) {
            callFirstOnResume = true
        } else {
            onRestartScreen()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!callViewCreated) {
            onStartScreen()
        } else {
            onRestartScreen()
        }

        callViewCreated = true
    }


    open fun onRestartScreen() {}
    open fun onStartScreen() {}

    fun <T : Parcelable> getArgumentParcelable(key: String, clazz: Class<T>) : T? {
        var value = arguments?.getParcelable<T>(key)
        return value
    }

}

