package com.ideaware.brightsoccer.view.adapter

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ideaware.brightsoccer.R
import com.ideaware.brightsoccer.service.model.SoccerMatch
import com.ideaware.brightsoccer.service.model.State
import com.ideaware.brightsoccer.utils.dayAndDayName
import com.ideaware.brightsoccer.utils.fixtureDateFormat
import com.ideaware.brightsoccer.utils.getMonthAndYear
import kotlinx.android.synthetic.main.recycler_view_header_item_row.view.*
import kotlinx.android.synthetic.main.recycler_view_match_item_row.view.*


class FixturesRecyclerViewAdapter(matches: List<SoccerMatch> = ArrayList()) :
    BaseRecyclerViewAdapter(matches) {

    companion object {
        private const val HEADER = 0
        private const val MATCH_ITEM = 1
    }

    override fun getItemCount(): Int = filterMatchesList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER -> {
                ViewHolderHeader(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.recycler_view_header_item_row, parent, false)
                )
            }
            MATCH_ITEM -> {
                ViewHolderMatch(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.recycler_view_match_item_row, parent, false)
                )
            }
            else -> {
                ViewHolderMatch(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.recycler_view_match_item_row, parent, false)
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (filterMatchesList[position].state == State.Header) {
            HEADER
        } else {
            MATCH_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderHeader) {
            holder.itemView.dateTextView.text = filterMatchesList[position].date?.getMonthAndYear()
        } else if (holder is ViewHolderMatch) {
            holder.itemView.competitionTextView.text = filterMatchesList[position].competitionStage?.competition?.name

            holder.itemView.venueAndTimeTextView.text = filterMatchesList[position].venue?.name

            val formattedDate = filterMatchesList[position].date?.fixtureDateFormat() ?: ""
            val venue = filterMatchesList[position].venue?.name + " | "
            val venueAndTime = venue + formattedDate

            if (filterMatchesList[position].state == State.Postponed) {
                val spannable = SpannableString(venueAndTime)
                spannable.setSpan(
                    ForegroundColorSpan(Color.RED),
                    venue.length, venueAndTime.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                holder.itemView.venueAndTimeTextView.text = spannable
            } else {
                holder.itemView.venueAndTimeTextView.text = venueAndTime
            }

            holder.itemView.localTeamTextView.text = filterMatchesList[position].homeTeam?.name
            holder.itemView.awayTeamTextView.text = filterMatchesList[position].awayTeam?.name

            holder.itemView.posponedButton.visibility = if (filterMatchesList[position].state == State.Postponed) View.VISIBLE
            else View.GONE

            holder.itemView.matchDayTextView.text = filterMatchesList[position].date?.dayAndDayName()
        }
    }


    internal inner class ViewHolderHeader(view: View) : RecyclerView.ViewHolder(view)

    internal inner class ViewHolderMatch(view: View) : RecyclerView.ViewHolder(view)
}