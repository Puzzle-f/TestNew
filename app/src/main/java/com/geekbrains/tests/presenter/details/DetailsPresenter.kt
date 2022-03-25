package com.geekbrains.tests.presenter.details

import android.util.Log
import com.geekbrains.tests.view.ViewContract
import com.geekbrains.tests.view.details.ViewDetailsContract

internal class DetailsPresenter internal constructor(
    private val viewContract: ViewContract,
    private var count: Int = 0
) : PresenterDetailsContract {

    private var viewDetailsContract: ViewDetailsContract? = null

    override fun setCounter(count: Int) {
        this.count = count
    }

    override fun onIncrement() {
        count++
        viewDetailsContract?.setCount(count)
        Log.d("", "count = $count")
    }

    override fun onDecrement() {
        count--
        viewDetailsContract?.setCount(count)
        Log.d("", "count = $count")
    }

    override fun onAttach() {
        viewDetailsContract = viewContract as ViewDetailsContract
    }

    override fun onDetach() {
        viewDetailsContract = null
    }
}
