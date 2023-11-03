package com.example.android_trip_2023_app.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_trip_2023_app.model.ActivityData
import com.example.android_trip_2023_app.model.ActivityResponse
import com.example.android_trip_2023_app.model.ContributionGetResponse
import com.example.android_trip_2023_app.model.TeamResponse

class ContributionViewModel : ViewModel() {

    val contributionData: LiveData<List<ContributionGetResponse>> get() = _contributionData
    val errorDialogMsg: LiveData<String> get() = _errorDialogMsg

    private var _contributionData = MutableLiveData<List<ContributionGetResponse>>()
    private var _errorDialogMsg = MutableLiveData<String>()

    init {
        val newData = fetchContributionData()
        _contributionData.value = newData
    }

    private fun fetchContributionData(): List<ContributionGetResponse> {
        return listOf(
            ContributionGetResponse(
                "contribution1",
                "https://firebasestorage.googleapis.com/v0/b/myapppj-eb8e9.appspot.com/o/example.jpeg?alt=media&token=80b8b69b-b5d1-4d3e-a597-b23f617ff42c&_gl=1*1wgu8ce*_ga*NTEwMzg5NjYzLjE2ODk5OTA0Njg.*_ga_CW55HF8NVT*MTY5Njk4MjYyNy40NS4xLjE2OTY5ODM2MzAuNDYuMC4w",
                "チームお馬鹿さん",
                "https://firebasestorage.googleapis.com/v0/b/myapppj-eb8e9.appspot.com/o/kigyouhoshin_logo.jpeg?alt=media&token=1d97c06d-d3a8-4a17-92af-ecc6a783a26c&_gl=1*1h3132s*_ga*NTEwMzg5NjYzLjE2ODk5OTA0Njg.*_ga_CW55HF8NVT*MTY5Njk4MjYyNy40NS4xLjE2OTY5ODI3MjkuNDMuMC4w",
                71,
            ),
            ContributionGetResponse(
                "contribution2",
                "https://firebasestorage.googleapis.com/v0/b/myapppj-eb8e9.appspot.com/o/example2.jpeg?alt=media&token=657aa611-2d82-4519-81f0-0a454070ea51&_gl=1*hxjt1m*_ga*NTEwMzg5NjYzLjE2ODk5OTA0Njg.*_ga_CW55HF8NVT*MTY5Njk4MjYyNy40NS4xLjE2OTY5ODM2ODYuNjAuMC4w",
                "チームあんぽんたん",
                "https://firebasestorage.googleapis.com/v0/b/myapppj-eb8e9.appspot.com/o/kigyouhoshin_logo.jpeg?alt=media&token=1d97c06d-d3a8-4a17-92af-ecc6a783a26c&_gl=1*1h3132s*_ga*NTEwMzg5NjYzLjE2ODk5OTA0Njg.*_ga_CW55HF8NVT*MTY5Njk4MjYyNy40NS4xLjE2OTY5ODI3MjkuNDMuMC4w",
                50
            ),
//            ContributionGetResponse(
//                "contribution3",
//                "https://firebasestorage.googleapis.com/v0/b/myapppj-eb8e9.appspot.com/o/header1.jpg?alt=media&token=4b9df167-ad35-49a4-a489-ef05c515b66d&_gl=1*ja7asd*_ga*NTEwMzg5NjYzLjE2ODk5OTA0Njg.*_ga_CW55HF8NVT*MTY5Njk0Nzc0MS40NC4xLjE2OTY5NDc3NTUuNDYuMC4w",
//                "チームまぬけ",
//                "https://firebasestorage.googleapis.com/v0/b/myapppj-eb8e9.appspot.com/o/kigyouhoshin_logo.jpeg?alt=media&token=1d97c06d-d3a8-4a17-92af-ecc6a783a26c&_gl=1*1h3132s*_ga*NTEwMzg5NjYzLjE2ODk5OTA0Njg.*_ga_CW55HF8NVT*MTY5Njk4MjYyNy40NS4xLjE2OTY5ODI3MjkuNDMuMC4w",
//            ),
        )
    }
}