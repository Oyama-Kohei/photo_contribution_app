package com.example.android_trip_2023_app.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_trip_2023_app.R
import com.example.android_trip_2023_app.model.PointSummaryResponse
import com.example.android_trip_2023_app.model.TeamDatabaseModel
import com.example.android_trip_2023_app.repository.PointSummaryApiRepositoryImpl
import com.example.android_trip_2023_app.utils.DatabaseHelper
import com.example.android_trip_2023_app.utils.ResultHandler
import kotlinx.coroutines.launch

class TeamViewModel : ViewModel() {
    val pointSummaryData: LiveData<List<PointSummaryResponse>> get() = _pointSummaryData
    val onLoading: LiveData<Boolean> get() = _onLoading
    val errorDialogMsg: LiveData<String> get() = _errorDialogMsg
    val onTransitMap: LiveData<Boolean> get() = _onTransitMap

    private var _pointSummaryData = MutableLiveData<List<PointSummaryResponse>>()
    private var _onLoading = MutableLiveData<Boolean>()
    private var _errorDialogMsg = MutableLiveData<String>()
    private var _onTransitMap = MutableLiveData<Boolean>()


    var repository = PointSummaryApiRepositoryImpl()

    fun init(context: Context) {
        fetchPointSummary(context)
    }

    fun onTapMap() {
        _onTransitMap.postValue(true)
    }

    private fun fetchPointSummary(context: Context) {
        viewModelScope.launch {
            try {
                _onLoading.postValue(true)
                when (val result = repository.getPointSummary()) {
                    is ResultHandler.Success<List<PointSummaryResponse>> -> {
                        if (result.data != null) {
                            _pointSummaryData.postValue(result.data!!)
                        }
                    }
                    else -> _errorDialogMsg.postValue(context.getString(R.string.unexpected_error_dialog))
                }
                _onLoading.postValue(false)
            } catch (e: Exception) {
                _onLoading.postValue(false)
                _errorDialogMsg.postValue(context.getString(R.string.unexpected_error_dialog))
            }
        }
    }

    fun getMountainTeamColor(context: Context): String {
        val targetContent = "山の上エリア"
        val filteredDataList = pointSummaryData.value!!.filter { teamData ->
            teamData.area.any { it.areaName == targetContent }
        }

        var resultTeamId = filteredDataList.first().teamId
        var resultPost = filteredDataList.first().area.first { it.areaName == targetContent }.point
        for(filteredData in filteredDataList) {
            if(resultPost < filteredData.area.first { it.areaName == targetContent }.point) {
                resultPost = filteredData.area.first { it.areaName == targetContent }.point
                resultTeamId = filteredData.teamId
            }
        }

        val teamDataList = DatabaseHelper(context).getTeamDataList() ?: return "#FFFFFF"
        return teamDataList.first { it.teamId == resultTeamId }.teamColor
    }

    fun getMakibaTeamColor(context: Context): String {
        val targetContent = "まきばエリア"
        val filteredDataList = pointSummaryData.value!!.filter { teamData ->
            teamData.area.any { it.areaName == targetContent }
        }

        var resultTeamId = filteredDataList.first().teamId
        var resultPost = filteredDataList.first().area.first { it.areaName == targetContent }.point
        for(filteredData in filteredDataList) {
            if(resultPost < filteredData.area.first { it.areaName == targetContent }.point) {
                resultPost = filteredData.area.first { it.areaName == targetContent }.point
                resultTeamId = filteredData.teamId
            }
        }

        val teamDataList = DatabaseHelper(context).getTeamDataList() ?: return "#FFFFFF"
        return teamDataList.first { it.teamId == resultTeamId }.teamColor
    }

    fun getFruitTeamColor(context: Context): String {
        val targetContent = "うまひつじフルーツ"
        val filteredDataList = pointSummaryData.value!!.filter { teamData ->
            teamData.area.any { it.areaName == targetContent }
        }

        var resultTeamId = filteredDataList.first().teamId
        var resultPost = filteredDataList.first().area.first { it.areaName == targetContent }.point
        for(filteredData in filteredDataList) {
            if(resultPost < filteredData.area.first { it.areaName == targetContent }.point) {
                resultPost = filteredData.area.first { it.areaName == targetContent }.point
                resultTeamId = filteredData.teamId
            }
        }

        val teamDataList = DatabaseHelper(context).getTeamDataList() ?: return "#FFFFFF"
        return teamDataList.first { it.teamId == resultTeamId }.teamColor
    }

    fun getWakuwakuTeamColor(context: Context): String {
        val targetContent = "わくわくランド"
        val filteredDataList = pointSummaryData.value!!.filter { teamData ->
            teamData.area.any { it.areaName == targetContent }
        }

        var resultTeamId = filteredDataList.first().teamId
        var resultPost = filteredDataList.first().area.first { it.areaName == targetContent }.point
        for(filteredData in filteredDataList) {
            if(resultPost < filteredData.area.first { it.areaName == targetContent }.point) {
                resultPost = filteredData.area.first { it.areaName == targetContent }.point
                resultTeamId = filteredData.teamId
            }
        }

        val teamDataList = DatabaseHelper(context).getTeamDataList() ?: return "#FFFFFF"
        return teamDataList.first { it.teamId == resultTeamId }.teamColor
    }
}