package com.davay.android.feature.coincidences.data

import com.davay.android.R
import com.davay.android.base.usecases.GetData
import com.davay.android.feature.coincidences.presentation.TestMovie

class TestMovieRepository : GetData<TestMovie> {

    override suspend fun getData(): Result<List<TestMovie>> = Result.success(mockTestMovieList)

    companion object {
        private val films = listOf(
            "Lord of the Rings" to R.string.url1,
            "Hateful Eight" to R.string.url2,
            "Солярис" to R.string.url3,
            "Nausicaa of the Valley of the Wind" to R.string.url4,
            "Whiplash" to R.string.url5,
        )
        private val mockTestMovieList = List(10) {
            TestMovie(
                id = it,
                title = films[it % 5].first,
                imageUrl = films[it % 5].second
            )
        }
    }
}