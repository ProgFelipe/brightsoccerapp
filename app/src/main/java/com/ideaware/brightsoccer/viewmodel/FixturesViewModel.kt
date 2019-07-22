package com.ideaware.brightsoccer.viewmodel

import androidx.lifecycle.ViewModel
import com.ideaware.brightsoccer.service.BrightService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FixturesViewModel(private val brightService: BrightService) : ViewModel(), IFixturesViewModel {
    override fun callFixturesService() {
        GlobalScope.launch(Dispatchers.IO) {
            val result = brightService.getFixture()
            val match = result.body()
        }
    }

}