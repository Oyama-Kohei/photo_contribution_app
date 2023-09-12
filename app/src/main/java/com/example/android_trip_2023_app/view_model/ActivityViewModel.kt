package com.example.android_trip_2023_app.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_trip_2023_app.model.ActivityData
import com.example.android_trip_2023_app.model.ActivityResponse

class ActivityViewModel : ViewModel() {
    val activityData: LiveData<List<ActivityResponse>> get() = _activityData
    val errorDialogMsg: LiveData<String> get() = _errorDialogMsg

    private var _activityData = MutableLiveData<List<ActivityResponse>>()
    private var _errorDialogMsg = MutableLiveData<String>()

    init {
        val newData = fetchActivityData()
        _activityData.value = newData
    }

    private fun fetchActivityData(): List<ActivityResponse> {
        return listOf(
            ActivityResponse(
                "エリア1",
                listOf(
                    ActivityData(
                        "001",
                        "アヒルの大行進参加して、アヒルと一緒に写真撮る",
                        100,
                    ),
                    ActivityData(
                        "001",
                        "アヒルの大行進参加して、アヒルと一緒に写真撮る",
                        100,
                    ),
                    ActivityData(
                        "001",
                        "アヒルの大行進参加して、アヒルと一緒に写真撮る",
                        100,
                    ),
                    ActivityData(
                        "001",
                        "アヒルの大行進参加して、アヒルと一緒に写真撮る",
                        100,
                    ),
                    ActivityData(
                        "001",
                        "アヒルの大行進参加して、アヒルと一緒に写真撮る",
                        100,
                    ),
                ),
            ),
            ActivityResponse(
                "エリア2",
                listOf(
                    ActivityData(
                        "002",
                        "子豚のレース参加して1位を当てた",
                        100,
                    ),
                    ActivityData(
                        "002",
                        "子豚のレース参加して1位を当てた",
                        100,
                    ),
                    ActivityData(
                        "002",
                        "子豚のレース参加して1位を当てた",
                        100,
                    ),
                ),
            ),
            ActivityResponse(
                "エリア3",
                listOf(
                    ActivityData(
                        "003",
                        "わんわんバスに乗って写真を撮る",
                        100,
                    ),
                    ActivityData(
                        "003",
                        "わんわんバスに乗って写真を撮る",
                        100,
                    ),
                    ActivityData(
                        "001",
                        "アヒルの大行進参加して、アヒルと一緒に写真撮る",
                        100,
                    ),
                ),
            ),
        )
    }
}