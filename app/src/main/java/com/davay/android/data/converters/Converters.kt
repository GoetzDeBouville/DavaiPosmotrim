package com.davay.android.data.converters

import com.davay.android.data.database.entity.MovieDetailsEntity
import com.davay.android.data.database.entity.SessionEntity
import com.davay.android.data.database.entity.SessionWithMovies
import com.davay.android.data.dto.CollectionDto
import com.davay.android.data.dto.GenreDto
import com.davay.android.data.dto.MovieDetailsDto
import com.davay.android.data.dto.MovieDto
import com.davay.android.data.dto.SessionDto
import com.davay.android.data.dto.SessionStatusDto
import com.davay.android.data.dto.UserDto
import com.davay.android.domain.models.Collection
import com.davay.android.domain.models.Genre
import com.davay.android.domain.models.Movie
import com.davay.android.domain.models.MovieDetails
import com.davay.android.domain.models.Session
import com.davay.android.domain.models.SessionStatus
import com.davay.android.domain.models.User
import java.text.SimpleDateFormat
import java.util.Locale

fun CollectionDto.convert() = Collection(
    id,
    name,
    imgUrl
)

fun GenreDto.convert() = Genre(name)

fun MovieDto.convert() = Movie(
    id,
    name,
    imgUrl
)

fun UserDto.convert() = User(
    userId,
    name
)

fun MovieDetailsDto.convert() = MovieDetails(
    id,
    name,
    description,
    year,
    countries,
    imgUrl,
    alternativeName,
    ratingKinopoisk,
    ratingImdb,
    numOfMarksKinopoisk,
    numOfMarksImdb,
    duration,
    genres.map { it.name },
    actors,
    directors
)

fun SessionDto.convert(): Session {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val parsedDate = requireNotNull(dateFormat.parse(date)) { "Date parsing failed for $date" }

    return Session(
        id = id,
        users = users.map { it.convert() },
        numberOfMatchedMovies = numberOfMatchedMovies,
        date = parsedDate,
        status = status.convert(),
        imgUrl = imgUrl
    )
}

fun SessionStatusDto.convert(): SessionStatus {
    return when (this) {
        SessionStatusDto.WAITING -> SessionStatus.WAITING
        SessionStatusDto.VOTING -> SessionStatus.VOTING
        SessionStatusDto.CLOSED -> SessionStatus.CLOSED
    }
}

fun MovieDetailsEntity.convert(): MovieDetails {
    return MovieDetails(
        id = movieId,
        name = name,
        description = description,
        year = year,
        countries = countries,
        imgUrl = imgUrl,
        alternativeName = alternativeName,
        ratingKinopoisk = ratingKinopoisk,
        ratingImdb = ratingImdb,
        numOfMarksKinopoisk = numOfMarksKinopoisk,
        numOfMarksImdb = numOfMarksImdb,
        duration = duration,
        genres = genres,
        actors = actors,
        directors = directors
    )
}

fun SessionEntity.convert(): Session {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val parsedDate = requireNotNull(dateFormat.parse(date)) { "Date parsing failed for $date" }
    return Session(
        id = sessionId,
        users = users.mapIndexed() { index, name -> User(index.toString(), name) },
        numberOfMatchedMovies = numberOfMatchedMovies,
        date = parsedDate,
        status = SessionStatus.CLOSED,
        imgUrl = imgUrl
    )
}

fun SessionWithMovies.getSession(): Session {
    return session.convert()
}

fun SessionWithMovies.getMovies(): List<MovieDetails> {
    return movies.map { it.convert() }
}

fun MovieDetails.toEntity(): MovieDetailsEntity {
    return MovieDetailsEntity(
        movieId = id,
        name = name,
        description = description,
        year = year,
        countries = countries ?: emptyList(),
        imgUrl = imgUrl,
        alternativeName = alternativeName,
        ratingKinopoisk = ratingKinopoisk,
        ratingImdb = ratingImdb,
        numOfMarksKinopoisk = numOfMarksKinopoisk,
        numOfMarksImdb = numOfMarksImdb,
        duration = duration,
        genres = genres,
        actors = actors ?: emptyList(),
        directors = directors ?: emptyList(),
    )
}

fun Session.toEntity(): SessionEntity {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val dateString = dateFormat.format(date)
    return SessionEntity(
        sessionId = id,
        users = users.map { it.name },
        numberOfMatchedMovies = numberOfMatchedMovies ?: 0,
        date = dateString,
        imgUrl = imgUrl
    )
}
