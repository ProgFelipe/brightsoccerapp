package com.ideaware.brightsoccer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ideaware.brightsoccer.service.ApiErrorResponse
import com.ideaware.brightsoccer.service.ApiSuccessResponse
import com.ideaware.brightsoccer.service.SoccerMatchesService
import com.ideaware.brightsoccer.service.NetworkBound
import com.ideaware.brightsoccer.service.model.SoccerMatch
import com.ideaware.brightsoccer.service.model.State
import com.ideaware.brightsoccer.utils.getCalendar
import com.ideaware.brightsoccer.utils.getDate
import retrofit2.Response
import java.util.*

class FixturesViewModel(private val soccerMatchesService: SoccerMatchesService) : ViewModel(), IFixturesViewModel {

    /**
     * To promote Encapsulation and Immutability
     */
    private val loadingStateLiveData = MutableLiveData<Boolean>()
    override val loadingState: LiveData<Boolean> = loadingStateLiveData

    private val errorStateLiveData = MutableLiveData<ApiErrorResponse<*>>()
    override val errorState: LiveData<ApiErrorResponse<*>> = errorStateLiveData

    private val tempLiveData = MutableLiveData<List<SoccerMatch>>()

    private val matchesLiveData = MutableLiveData<List<SoccerMatch>>()
    override val matchesState: LiveData<List<SoccerMatch>> = matchesLiveData

    override val fixturesLiveData: MutableLiveData<List<SoccerMatch>> =
        Transformations.switchMap(matchesLiveData) { resource ->
            sortMatches(resource)
        } as MutableLiveData<List<SoccerMatch>>

    override fun callFixturesService() {
        object : NetworkBound<List<SoccerMatch>>() {
            override suspend fun createCall(): Response<List<SoccerMatch>> {
                return soccerMatchesService.getFixture()
            }

            override fun onFetchFailed(apiErrorResponse: ApiErrorResponse<List<SoccerMatch>>) {
                errorStateLiveData.postValue(apiErrorResponse)
            }

            override fun onStartFetching() {
                loadingStateLiveData.postValue(true)
            }

            override fun onFinishFetching() {
                loadingStateLiveData.postValue(false)
            }

            override fun processResponse(response: ApiSuccessResponse<List<SoccerMatch>>) {
                matchesLiveData.postValue(response.body)
            }
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