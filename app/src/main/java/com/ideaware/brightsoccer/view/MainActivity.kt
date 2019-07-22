package com.ideaware.brightsoccer.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ideaware.brightsoccer.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewPager()
        tabLayout.setupWithViewPager(viewPager)
    }


    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(FixturesFragment.newInstance(), getString(R.string.fixtures))
        adapter.addFragment(ResultsFragment.newInstance(), getString(R.string.results))
        viewPager.adapter = adapter
    }

    internal inner class ViewPagerAdapter(supportFragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(supportFragmentManager) {

        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }
    }
}
