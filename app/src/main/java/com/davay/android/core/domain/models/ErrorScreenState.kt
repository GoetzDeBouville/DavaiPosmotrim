package com.davay.android.core.domain.models

import com.davay.android.R

enum class ErrorScreenState(
    val errorTitleResource: Int,
    val errorDescriptionResource: Int,
    val imageResource: Int,
    val buttonTextResource: Int?
) {
    NO_INTERNET(
        com.davai.uikit.R.string.error_message_no_internet_title,
        com.davai.uikit.R.string.error_message_no_internet_description,
        com.davai.uikit.R.drawable.ic_error_no_network_200,
        com.davai.uikit.R.string.error_message_no_internet_button_text
    ),
    SERVER_ERROR(
        com.davai.uikit.R.string.error_message_no_access_service_title,
        com.davai.uikit.R.string.error_message_no_access_service_description,
        com.davai.uikit.R.drawable.ic_error_no_service_access_200,
        null
    ),
    ERROR_BUILD_SESSION_GENRES(
        com.davai.uikit.R.string.error_message_no_build_service_error,
        com.davai.uikit.R.string.error_message_no_build_service_error_description_genres,
        com.davai.uikit.R.drawable.ic_error_no_service_access_200,
        com.davai.uikit.R.string.error_message_no_build_service_error_button_text_genres,
    ),
    ERROR_BUILD_SESSION_COLLECTIONS(
        com.davai.uikit.R.string.error_message_no_build_service_error,
        com.davai.uikit.R.string.error_message_no_build_service_error_description_collections,
        com.davai.uikit.R.drawable.ic_error_no_service_access_200,
        com.davai.uikit.R.string.error_message_no_build_service_error_button_text_collections,
    ),
    EMPTY(
        com.davai.uikit.R.string.error_message_empty_title,
        com.davai.uikit.R.string.empty_text,
        com.davai.uikit.R.drawable.placeholder_empty,
        null
    ),
    APP_VERSION_ERROR(
        com.davai.uikit.R.string.error_message_old_version_title,
        com.davai.uikit.R.string.error_message_old_version_description,
        com.davai.uikit.R.drawable.ic_error_old_version_200,
        com.davai.uikit.R.string.error_message_no_internet_button_text
    ),
    MOVIE_LIST_FINISHED(
        R.string.select_movies_you_liked_whole_movie_list,
        R.string.select_movies_wait_for_end_of_session,
        com.davai.uikit.R.drawable.placeholder_empty,
        null
    )
}