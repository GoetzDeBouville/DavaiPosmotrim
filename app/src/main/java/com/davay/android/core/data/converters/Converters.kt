package com.davay.android.core.data.converters

import com.davay.android.core.data.database.entity.MovieDetailsEntity
import com.davay.android.core.data.database.entity.MovieIdEntity
import com.davay.android.core.data.database.entity.SessionEntity
import com.davay.android.core.data.database.entity.SessionWithMoviesDb
import com.davay.android.core.data.dto.CollectionDto
import com.davay.android.core.data.dto.GenreDto
import com.davay.android.core.data.dto.MovieDetailsDto
import com.davay.android.core.data.dto.MovieDto
import com.davay.android.core.data.dto.SessionDto
import com.davay.android.core.data.dto.SessionStatusDto
import com.davay.android.core.data.dto.UserDto
import com.davay.android.core.domain.models.CompilationFilms
import com.davay.android.core.domain.models.Genre
import com.davay.android.core.domain.models.Movie
import com.davay.android.core.domain.models.MovieDetails
import com.davay.android.core.domain.models.Session
import com.davay.android.core.domain.models.SessionStatus
import com.davay.android.core.domain.models.SessionWithMovies
import com.davay.android.core.domain.models.User
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.round

fun CollectionDto.toDomain() = CompilationFilms(
    id,
    name,
    imgUrl
)

fun GenreDto.toDomain() = Genre(name)

fun MovieDto.toDomain() = Movie(
    id,
    name,
    imgUrl
)

fun UserDto.toDomain() = User(
    userId = userId,
    name = name
)

fun MovieDetailsDto.toDomain(id: Int) = MovieDetails(
    id,
    name,
    description,
    year,
    countries,
    imgUrl?.let {
        URLDecoder.decode(it.removePrefix("/"), StandardCharsets.UTF_8.toString())
    } ?: "",
    alternativeName,
    (round(ratingKinopoisk * 10) / 10),
    ratingImdb,
    numOfMarksKinopoisk,
    numOfMarksImdb,
    duration,
    genres.map { it.name },
    personsArrFormatter(actors),
    personsArrFormatter(directors)
)

private fun personsArrFormatter(strList: List<String?>?): List<String> {
    return if (strList.isNullOrEmpty()) {
        emptyList()
    } else {
        val newList = mutableListOf<String>()

        strList.forEach { str ->
            if (str.isNullOrEmpty().not()) {
                newList.add(str!!)
            }
        }
        newList
    }
}


/**
 * Конвертирует строку с датой в timeStamp.
 * Если при форматировании дата получается null, то возвращается дата minDate.
 * Если при форматировании дата больше текущей, то возвращается дата minDate.
 * Если при форматировании дата меньше minDate, то возвращается minDate.
 * minDate устанавливаем на 2024-01-01.
 */
fun SessionDto.toDomain(): Session {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val minDate = dateFormat.parse("2024-01-01")
    val currentDate = Date()

    val parsedDate = try {
        dateFormat.parse(date)
    } catch (e: ParseException) {
        null
    }

    val validDate = when {
        parsedDate == null -> minDate
        parsedDate.before(minDate) -> minDate
        parsedDate.after(currentDate) -> minDate
        else -> parsedDate
    }

    val timestamp = validDate.time

    return Session(
        id = id,
        users = users.map { it.name },
        movieIdList = movieIdList,
        matchedMovieIdList = matchedMovieIdList,
        date = timestamp,
        status = status.toDomain(),
        imgUrl = imgUrl ?: ""
    )
}

fun SessionStatusDto.toDomain(): SessionStatus {
    return when (this) {
        SessionStatusDto.WAITING -> SessionStatus.WAITING
        SessionStatusDto.VOTING -> SessionStatus.VOTING
        SessionStatusDto.CLOSED -> SessionStatus.CLOSED
        SessionStatusDto.ROULETTE -> SessionStatus.ROULETTE
    }
}

fun MovieDetailsEntity.toDomain(): MovieDetails {
    return MovieDetails(
        id = movieId,
        name = name,
        description = description,
        year = year,
        countries = countries?.toListData(),
        imgUrl = imgUrl,
        alternativeName = alternativeName,
        ratingKinopoisk = ratingKinopoisk,
        ratingImdb = ratingImdb,
        numOfMarksKinopoisk = numOfMarksKinopoisk,
        numOfMarksImdb = numOfMarksImdb,
        duration = duration,
        genres = genres.toListData(),
        actors = actors?.toListData(),
        directors = directors?.toListData()
    )
}

fun SessionEntity.toDomain(): Session {
    return Session(
        id = sessionId,
        users = users.toListData(),
        movieIdList = emptyList(),
        matchedMovieIdList = emptyList(),
        date = date,
        status = SessionStatus.CLOSED,
        imgUrl = imgUrl
    )
}

fun MovieDetails.toDbEntity(): MovieDetailsEntity {
    return MovieDetailsEntity(
        movieId = id,
        name = name,
        description = description,
        year = year,
        countries = countries?.toStringData(),
        imgUrl = imgUrl,
        alternativeName = alternativeName,
        ratingKinopoisk = ratingKinopoisk,
        ratingImdb = ratingImdb,
        numOfMarksKinopoisk = numOfMarksKinopoisk,
        numOfMarksImdb = numOfMarksImdb,
        duration = duration,
        genres = genres.toStringData(),
        actors = actors?.toStringData(),
        directors = directors?.toStringData(),
    )
}

fun Session.toDbEntity(): SessionEntity {
    return SessionEntity(
        sessionId = id,
        users = users.map { it }.toStringData(),
        numberOfMatchedMovies = matchedMovieIdList.size,
        date = date,
        imgUrl = imgUrl
    )
}

fun List<String>.toStringData(): String {
    return joinToString(separator = ";")
}

fun String.toListData(): List<String> {
    return split(";")
}

fun SessionWithMoviesDb.toDomain(): SessionWithMovies {
    return SessionWithMovies(
        session = session.toDomain(),
        movies = movies.map { it.toDomain() }
    )
}

fun MovieIdEntity.toDomain(): Int = this.movieId
