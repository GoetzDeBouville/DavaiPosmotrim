package com.davay.android.feature.coincidences.data

import com.davay.android.base.usecases.GetData
import com.davay.android.data.dto.MovieDetailsDto
import com.davay.android.domain.models.ErrorType
import com.davay.android.domain.models.MovieDetails
import com.davay.android.utils.ConnectionChecker
import com.davay.android.utils.Result

class TestMovieRepository(
    private val connectionChecker: ConnectionChecker
) : GetData<MovieDetails, ErrorType> {

    override suspend fun getData(): Result<List<MovieDetails>, ErrorType> =
        if (connectionChecker.isConnected) {
            Result.Success(mockTestMovieList)
        } else {
            Result.Error(ErrorType.NO_CONNECTION)
        }

    companion object {
        private val urls = listOf(
            "https://images.squarespace-cdn.com/content/v1/57488e28746fb940f103c64e/" +
                "1626726014496-MF7OB9OBHLB21UR0LJV0/Fellowship+for+Trailer.jpg",
            "https://i.pinimg.com/originals/b8/79/e4/b879e41af44317004f6e765cb215b41c.jpg",
            "https://ruskino.ru/media/gallery/624/uxpmUNTVnFXMiVWIWSebd4l03TI.jpg",
            "https://i.pinimg.com/736x/5e/14/c7/5e14c743d3afd01c80f12e3a5171cada.jWind",
            "https://labande-annonce.fr/www-site/uploads/2014/12/affiche-whiplash-fr.jpg"
        )

        private val films = listOf(
            "Lord of the Rings" to urls[0],
            "Hateful Eight" to urls[1],
            "Солярис" to urls[2],
            "Nausicaa of the Valley of the Wind" to urls[3],
            "Whiplash" to urls[4]
        )

        private val mockTestMovieList = List(10) {
            MovieDetails(
                id = it,
                name = films[it % 5].first,
                imgUrl = films[it % 5].second,
                description = null,
                year = null,
                countries = listOf(),
                alternativeName = null,
                ratingKinopoisk = null,
                ratingImdb = null,
                numOfMarksKinopoisk = null,
                numOfMarksImdb = null,
                duration = null,
                genres = listOf(),
                directors = listOf(),
                actors = listOf(),
            )
        }
    }
}