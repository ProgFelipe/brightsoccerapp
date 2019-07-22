package com.ideaware.brightsoccer.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ideaware.brightsoccer.R
import com.ideaware.brightsoccer.api.RetrofitFactory
import com.ideaware.brightsoccer.view.adapter.ResultsRecyclerViewAdapter
import com.ideaware.brightsoccer.viewmodel.*
import kotlinx.android.synthetic.main.fragment_fixtures.*
import kotlinx.android.synthetic.main.fragment_results.progressBar

class ResultsFragment : Fragment(), IResultsView {

    private val viewModel: IResultsViewModel by lazy {
        getViewModel { ResultsViewModel(RetrofitFactory().getService()) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI()
    }

    private fun initializeUI() {
        recyclerView.adapter = ResultsRecyclerViewAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        viewModel.loadingState.observe(this, Observer {
            if (it) showProgressBar() else hideProgressBar()
        })

        viewModel.resultsLiveData.observe(this, Observer {
            (recyclerView.adapter as ResultsRecyclerViewAdapter).matches = it
            (recyclerView.adapter as ResultsRecyclerViewAdapter).notifyDataSetChanged()
        })
    }


    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }


    override fun onStart() {
        super.onStart()
        viewModel.callResultsService()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ResultsFragment()
    }
}
