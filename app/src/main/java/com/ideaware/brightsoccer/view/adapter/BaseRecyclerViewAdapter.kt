package com.ideaware.brightsoccer.view.adapter

import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.ideaware.brightsoccer.service.model.SoccerMatch

abstract class BaseRecyclerViewAdapter(var matches: List<SoccerMatch> = ArrayList()) : Filterable,
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var filterMatchesList: List<SoccerMatch> = matches

    fun updateMatches(matches: List<SoccerMatch>) {
        this.matches = matches
        filterMatchesList = matches
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    filterMatchesList = matches
                } else {
                    val filteredList = ArrayList<SoccerMatch>()
                    for (row in matches) {
                        if (row.competitionStage?.competition?.name?.toLowerCase()?.contains(charString.toLowerCase()) == true) {
                            filteredList.add(row)
                        }
                    }
                    filterMatchesList = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = filterMatchesList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                if (filterResults.values != null) {
                    filterMatchesList = filterResults.values as ArrayList<SoccerMatch>
                    notifyDataSetChanged()
                }
            }
        }
    }
}