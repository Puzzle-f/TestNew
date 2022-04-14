package com.geekbrains.tests.view.details.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.geekbrains.tests.R
import com.geekbrains.tests.presenter.details.DetailsPresenter
import com.geekbrains.tests.presenter.details.PresenterDetailsContract
import com.geekbrains.tests.view.details.ViewDetailsContract
import kotlinx.android.synthetic.main.fragment_details.*
import java.util.*

class DetailsFragment : Fragment(), ViewDetailsContract {

    private val presenter: PresenterDetailsContract = DetailsPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter.onAttach()
    }

    private fun setUI() {
        arguments?.let {
            val counter = it.getInt(TOTAL_COUNT_EXTRA, 0)
            presenter.setCounter(counter)
            setCountText(counter)
        }
        decrementButton.setOnClickListener { presenter.onDecrement() }
        incrementButton.setOnClickListener { presenter.onIncrement() }
    }

    private fun setCountText(count: Int) {
        totalCountTextView.text =
            String.format(Locale.getDefault(), getString(R.string.results_count),
                count)
    }

    override fun setCount(count: Int) {
        setCountText(count)
    }

    companion object {
        const val TOTAL_COUNT_EXTRA = "TOTAL_COUNT_EXTRA"

        @JvmStatic
        fun newInstance(counter: Int) =
            DetailsFragment().apply {
                arguments = bundleOf(
                    TOTAL_COUNT_EXTRA to
                            counter
                )
            }
    }

    override fun onDetach() {
        super.onDetach()
        presenter.onDetach()
    }


}