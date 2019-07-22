package com.ideaware.brightsoccer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ideaware.brightsoccer.service.ApiErrorResponse
import com.ideaware.brightsoccer.service.model.SoccerMatch

interface IFixturesViewModel {
    val loadingState: LiveData<Boolean>
    val matchesState: LiveData<List<SoccerMatch>>
    val errorState: LiveData<ApiErrorResponse<*>>
    val fixturesLiveData: MutableLiveData<List<SoccerMatch>>
    fun callFixturesService()
}