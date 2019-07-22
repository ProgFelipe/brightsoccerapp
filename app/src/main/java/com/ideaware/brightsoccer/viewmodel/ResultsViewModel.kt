package com.ideaware.brightsoccer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ideaware.brightsoccer.service.BrightService
import com.ideaware.brightsoccer.service.model.SoccerMatch
import com.ideaware.brightsoccer.service.model.State
import com.ideaware.brightsoccer.utils.getCalendar
import com.ideaware.brightsoccer.utils.getDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class ResultsViewModel(private val brightService: BrightService) : ViewModel(), IResultsViewModel {

    /**
     * To promote Encapsulation and Immutability
     */
    private val loadingStateLiveData = MutableLiveData<Boolean>()
    override val loadingState: LiveData<Boolean> = loadingStateLiveData

    //private val errorStateLiveData = MutableLiveData<Resource<*>>()
    //override val errorState: LiveData<Resource<*>> = errorStateLiveData

    private val tempLiveData = MutableLiveData<List<SoccerMatch>>()

    private val matchesLiveData = MutableLiveData<List<SoccerMatch>>()
    override val matchesState: LiveData<List<SoccerMatch>> = matchesLiveData

    override val resultsLiveData: MutableLiveData<List<SoccerMatch>> =
        Transformations.switchMap(matchesLiveData) { resource ->
            sortMatches(resource)
        } as MutableLiveData<List<SoccerMatch>>

    override fun callResultsService() {
        GlobalScope.launch(Dispatchers.IO) {
            loadingStateLiveData.postValue(true)
            val result = brightService.getResults()
            val match = result.body()
            loadingStateLiveData.postValue(false)
            matchesLiveData.postValue(match)
        }
    }

    private val dateTimeStrToLocalDateTime: (SoccerMatch) -> Date = { soccerMatch ->
        soccerMatch.date?.getDate()!!
    }

    private fun sortMatches(matches: List<SoccerMatch>): LiveData<List<SoccerMatch>> {
        matches.map(dateTimeStrToLocalDateTime).sortedDescending()
        val matchesWithHeader = ArrayList<SoccerMatch>()
        var soccerMatchHeader: SoccerMatch
        var previousMonth = -1
        for (match in matches) {
            val month = match.date?.getCalendar()?.get(Calendar.MONTH)
            if (previousMonth == -1 || month != previousMonth) {
                soccerMatchHeader = SoccerMatch(state = State.Header, date = match.date)
                matchesWithHeader.add(soccerMatchHeader)
            }
            matchesWithHeader.add(match)
            previousMonth = month ?: -1
        }
        tempLiveData.value = matchesWithHeader
        return tempLiveData
    }
}