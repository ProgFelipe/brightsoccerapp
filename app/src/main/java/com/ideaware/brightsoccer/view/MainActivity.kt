package com.ideaware.brightsoccer.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ideaware.brightsoccer.R
import com.ideaware.brightsoccer.view.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var searchView: SearchView
    private lateinit var fixturesFragment: FixturesFragment
    private lateinit var resultsFragment: ResultsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        setupViewPager()
        tabLayout.setupWithViewPager(viewPager)
    }


    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        fixturesFragment = FixturesFragment.newInstance()
        resultsFragment = ResultsFragment.newInstance()
        adapter.addFragment(fixturesFragment, getString(R.string.fixtures))
        adapter.addFragment(resultsFragment, getString(R.string.results))
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setSearchableInfo(
            searchManager
                .getSearchableInfo(componentName)
        )
        searchView.maxWidth = Integer.MAX_VALUE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {


            override fun onQueryTextSubmit(query: String): Boolean {
                getCurrentRecyclerViewAdapter()?.filter?.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                getCurrentRecyclerViewAdapter()?.filter?.filter(query)
                return false
            }
        })
        return true
    }

    fun getCurrentRecyclerViewAdapter(): BaseRecyclerViewAdapter?{
        return if (viewPager.currentItem == 0) {
            fixturesFragment.getRecyclerViewAdapter()
        } else {
            resultsFragment.getRecyclerViewAdapter()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (!searchView.isIconified) {
            searchView.isIconified = true
            return
        }
        super.onBackPressed()
    }
}