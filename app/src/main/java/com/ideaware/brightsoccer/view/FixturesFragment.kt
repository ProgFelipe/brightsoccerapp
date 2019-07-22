package com.ideaware.brightsoccer.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ideaware.brightsoccer.R
import com.ideaware.brightsoccer.api.RetrofitFactory
import com.ideaware.brightsoccer.view.adapter.FixturesRecyclerViewAdapter
import com.ideaware.brightsoccer.viewmodel.FixturesViewModel
import com.ideaware.brightsoccer.viewmodel.IFixturesViewModel
import com.ideaware.brightsoccer.viewmodel.getViewModel
import kotlinx.android.synthetic.main.fragment_fixtures.*


class FixturesFragment : Fragment(), IFixturesView {

    private val viewModel: IFixturesViewModel by lazy {
        getViewModel { FixturesViewModel(RetrofitFactory().getService()) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fixtures, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI()
    }

    private fun initializeUI() {
        recyclerView.adapter = FixturesRecyclerViewAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        viewModel.loadingState.observe(this, Observer {
            if (it) showProgressBar() else hideProgressBar()
        })

        viewModel.fixturesLiveData.observe(this, Observer {
            (recyclerView.adapter as FixturesRecyclerViewAdapter).matches = it
            (recyclerView.adapter as FixturesRecyclerViewAdapter).notifyDataSetChanged()
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
        viewModel.callFixturesService()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FixturesFragment()
    }
}
