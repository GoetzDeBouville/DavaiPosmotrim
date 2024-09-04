package com.davay.android.feature.match.presentation

import android.os.Parcelable
import com.davay.android.core.domain.models.MovieDetails
import kotlinx.parcelize.Parcelize

@Parcelize
data class MatchBottomSheetArgs(
    val movieDetails: MovieDetails,
    val buttonText: String,
    val showDismisAnimation: Boolean
) : Parcelable
