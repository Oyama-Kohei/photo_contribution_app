package com.example.android_trip_2023_app.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_trip_2023_app.model.TeamResponse

class TeamViewModel : ViewModel() {
    val teamData: LiveData<List<TeamResponse>> get() = _teamData
    val errorDialogMsg: LiveData<String> get() = _errorDialogMsg

    private var _teamData = MutableLiveData<List<TeamResponse>>()
    private var _errorDialogMsg = MutableLiveData<String>()

    init {
        val newData = fetchTeamData()
        _teamData.value = newData
    }

    private fun fetchTeamData(): List<TeamResponse> {
        return listOf(
            TeamResponse(
                "team1",
                "チームお馬鹿さん",
                1589
            ),
            TeamResponse(
                "team2",
                "チームあんぽんたん",
                587
            ),
            TeamResponse(
                "team3",
                "チームまぬけ",
                1498
            ),
            TeamResponse(
                "team4",
                "チーム消しカス",
                1123
            ),
        )
    }

}