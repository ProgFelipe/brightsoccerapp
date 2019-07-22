package com.ideaware.brightsoccer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ideaware.brightsoccer.service.model.SoccerMatch

interface IFixturesViewModel {
    val loadingState: LiveData<Boolean>
    val matchesState: LiveData<List<SoccerMatch>>
    //val errorState: LiveData<Resource<*>>
    val fixturesLiveData: MutableLiveData<List<SoccerMatch>>
    fun callFixturesService()
}