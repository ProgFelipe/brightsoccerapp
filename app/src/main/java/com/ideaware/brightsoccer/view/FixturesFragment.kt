package com.ideaware.brightsoccer.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ideaware.brightsoccer.R
import com.ideaware.brightsoccer.api.RetrofitFactory
import com.ideaware.brightsoccer.viewmodel.FixturesViewModel
import com.ideaware.brightsoccer.viewmodel.IFixturesViewModel
import com.ideaware.brightsoccer.viewmodel.getViewModel

class FixturesFragment : Fragment() {

    private val viewModel: IFixturesViewModel by lazy {
        getViewModel { FixturesViewModel(RetrofitFactory().getService()) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fixtures, container, false)
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
