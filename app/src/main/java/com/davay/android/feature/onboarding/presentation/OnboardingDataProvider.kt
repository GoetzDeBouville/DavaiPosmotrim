package com.davay.android.feature.onboarding.presentation

import androidx.navigation.NavDirections
import com.davay.android.R

//class OnboardingDataProvider {
//    fun getMainOnboardingData(): Array<IntArray> =
//        arrayOf(
//            intArrayOf(
//                R.string.onboarding_choose_full_text,
//                R.drawable.img_search_movies,
//                R.string.onboarding_with_no_more_dicussions
//            ),
//            intArrayOf(
//                R.string.onboarding_for_large_company,
//                R.drawable.img_large_party,
//                R.string.onboarding_choose_movies_with_any
//            ),
//            intArrayOf(
//                R.string.onboarding_movie_library,
//                R.drawable.img_favorite_film,
//                R.string.onboarding_only_favorite_genres
//            ),
//            intArrayOf(
//                R.id.action_onboardingFragment_to_registrationFragment
//            )
//        )
//
//    fun getInstructionOnboardingData(): Array<IntArray> =
//        arrayOf(
//            intArrayOf(
//                R.string.onboarding_instructions_as_you_got_used,
//                R.drawable.img_movie_for_night,
//                R.string.onboarding_instructions_swipes_description
//            ),
//            intArrayOf(
//                R.string.onboarding_instructions_choose_from_common_matches,
//                R.drawable.img_favorite_movies,
//                R.string.onboarding_instructions_saving_matches_history
//            ),
//            intArrayOf(
//                R.string.onboarding_instructions_one_moment,
//                R.drawable.img_session,
//                R.string.onboarding_instructions_session_finishing
//            ),
//            intArrayOf(
//                R.id.action_onboardingFragment_to_selectMovieFragment
//            ) // После влива фрагмента выбора заменить action id
//        )
//}
class OnboardingDataProvider {

    fun getMainOnboardingData(): List<OnboardingItem> =
        listOf(
            OnboardingItem(
                R.string.onboarding_choose_full_text,
                R.drawable.img_search_movies,
                R.string.onboarding_with_no_more_dicussions
            ),
            OnboardingItem(
                R.string.onboarding_for_large_company,
                R.drawable.img_large_party,
                R.string.onboarding_choose_movies_with_any
            ),
            OnboardingItem(
                R.string.onboarding_movie_library,
                R.drawable.img_favorite_film,
                R.string.onboarding_only_favorite_genres
            ),
            OnboardingItem(
                action = OnboardingFragmentDirections.actionOnboardingFragmentToRegistrationFragment()
            )
        )

    fun getInstructionOnboardingData(): List<OnboardingItem> =
        listOf(
            OnboardingItem(
                R.string.onboarding_instructions_as_you_got_used,
                R.drawable.img_movie_for_night,
                R.string.onboarding_instructions_swipes_description
            ),
            OnboardingItem(
                R.string.onboarding_instructions_choose_from_common_matches,
                R.drawable.img_favorite_movies,
                R.string.onboarding_instructions_saving_matches_history
            ),
            OnboardingItem(
                R.string.onboarding_instructions_one_moment,
                R.drawable.img_session,
                R.string.onboarding_instructions_session_finishing
            ),
            OnboardingItem(
                action = OnboardingFragmentDirections.actionOnboardingFragmentToSelectMovieFragment()
            )
        )

    data class OnboardingItem(
        val textResId: Int? = null,
        val imageResId: Int? = null,
        val descriptionResId: Int? = null,
        val action: NavDirections? = null
    )
}