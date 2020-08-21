package com.example.filmsviewapp.ui.base.view_model

import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

open class BaseViewModel : DisposableViewModel() {

        val liveDataMap = HashMap<String, MutableLiveData<*>>()


        open fun validateError(throwable: Throwable) {}



        fun <T : Any> Observable<T>.bindSubscribe(
            bindName: String? = null,
            onError: (Throwable) -> Unit = { validateError(it) },
            onComplete: () -> Unit = {},
            onNext: (T) -> Unit = {}
        ) = subscribe(onNext, onError, onComplete).apply {
            if (bindName == null) compositeDisposable.bind(this) else compositeDisposable.bind(
                bindName,
                this
            )
        }


        fun <T : Any> Flowable<T>.bindSubscribe(
            bindName: String? = null,
            onError: (Throwable) -> Unit = { validateError(it) },
            onComplete: () -> Unit = {},
            onNext: (T) -> Unit = {}
        ) = subscribe(onNext, onError, onComplete).apply {
            if (bindName == null) compositeDisposable.bind(this) else compositeDisposable.bind(
                bindName,
                this
            )
        }

        fun <T : Any> Single<T>.bindSubscribe(
            bindName: String? = null,
            onError: (Throwable) -> Unit = { validateError(it) },
            onNext: (T) -> Unit = {}
        ) = subscribe(onNext, onError).apply {
            if (bindName == null) compositeDisposable.bind(this) else compositeDisposable.bind(
                bindName,
                this
            )
        }

        fun <T : Any> Flowable<T>.toLiveData(
            bindName: String? = null,
            onError: (Throwable) -> Unit = { validateError(it) }
        ): MutableLiveData<T> {

            val liveData = if (bindName != null && liveDataMap.get(bindName) != null) {
                liveDataMap.get(bindName) as MutableLiveData<T>
            } else {
                MutableLiveData()
            }

            if (bindName != null && liveDataMap.get(bindName) == null) {
                liveDataMap.put(bindName, liveData)
            }

            bindSubscribe(bindName, onError, {}, {
                liveData.postValue(it)
            })

            return liveData

        }


        fun <T : Any> Single<T>.toLiveData(
            bindName: String? = null,
            onError: (Throwable) -> Unit = { validateError(it) }
        ): MutableLiveData<T> {

            val liveData = if (bindName != null && liveDataMap[bindName] != null) {
                liveDataMap.get(bindName) as MutableLiveData<T>
            } else {
                MutableLiveData()
            }

            if (bindName != null && liveDataMap.get(bindName) == null) {
                liveDataMap[bindName] = liveData
            }


            bindSubscribe(bindName = bindName, onNext = {
                liveData.postValue(it)
            }, onError = onError)

            return liveData
        }


        fun Completable.toLiveData(
            bindName: String? = null,
            onError: (Throwable) -> Unit = { validateError(it) }
        ): MutableLiveData<Any> {

            val liveData = if (bindName != null && liveDataMap[bindName] != null) {
                liveDataMap.get(bindName) as MutableLiveData<Any>
            } else {
                MutableLiveData<Any>()
            }

            if (bindName != null && liveDataMap.get(bindName) == null) {
                liveDataMap[bindName] = liveData
            }


            bindSubscribe(bindName = bindName, onNext = {
                liveData.postValue(Any())
            }, onError = onError)

            return liveData
        }


        fun <T : Any> Single<T>.justSubscribe(onNext: (T) -> Unit = {}) = subscribe(onNext, {}).apply {
            compositeDisposable.bind(this)
        }


        fun <T : Any> Flowable<T>.justSubscribe(
            bindName: String? = null,
            onComplete: () -> Unit = {},
            onNext: (T) -> Unit = {}
        ) = subscribe(onNext, {}, onComplete).apply {
            if (bindName == null) compositeDisposable.bind(this) else compositeDisposable.bind(
                bindName,
                this
            )
        }

        fun Completable.bindSubscribe(
            bindName: String? = null,
            onError: (Throwable) -> Unit =
                { validateError(it) },
            onNext: () -> Unit = {}
        ) = subscribe(onNext, onError).apply {
            if (bindName == null) compositeDisposable.bind(this) else compositeDisposable.bind(
                bindName,
                this
            )
        }

        fun Completable.justSubscribe(
            onNext: () -> Unit = {}
        ) = subscribe(onNext, {})

        override fun onCleared() {
            super.onCleared()
            liveDataMap.clear()
        }
    }

