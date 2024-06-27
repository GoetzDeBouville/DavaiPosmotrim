package com.davay.android.data.converters

import com.davay.android.data.dto.CollectionDto
import com.davay.android.data.dto.GenreDto
import com.davay.android.data.dto.MovieDto
import com.davay.android.data.dto.SessionDto
import com.davay.android.data.dto.ShortMovieDto
import com.davay.android.data.dto.UserDto
import com.davay.android.domain.models.Collection
import com.davay.android.domain.models.Genre
import com.davay.android.domain.models.MovieDetails
import com.davay.android.domain.models.ShortMovie
import com.davay.android.domain.models.User
import com.davay.android.domain.models.Session

fun CollectionDto.convert() = Collection(
    id,
    name,
    imgUrl
)

fun GenreDto.convert() = Genre(name)

fun ShortMovieDto.convert() = ShortMovie(
    id,
    name,
    imgUrl
)

fun UserDto.convert() = User(
    userId,
    name
)

fun MovieDto.convert() = MovieDetails(
    id,
    name,
    imgUrl,
    alternativeName,
    ratingKinopoisk,
    numOfMarksKinopoisk,
    ratingImdb,
    numOfMarksImdb,
    genres.map { it.name },
    year,
    countries,
    duration,
    description,
    actors,
    directors
)

fun SessionDto.convert() = Session(
    id,
    users.map { it.convert() },
    movies.map { it.convert() },
    matchedMovies.map { it.convert() },
    date,
    status
)