package com.ideaware.brightsoccer.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ideaware.brightsoccer.R

class ResultsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FixturesFragment()
    }
}
