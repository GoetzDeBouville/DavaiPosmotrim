package com.davay.android.feature.selectmovie.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import com.davay.android.R
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentSelectMovieBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.extensions.dpToPx
import com.davay.android.feature.selectmovie.MovieDetailsDemo
import com.davay.android.feature.selectmovie.adapters.MovieCardAdapter
import com.davay.android.feature.selectmovie.adapters.SwipeCallback
import com.davay.android.feature.selectmovie.adapters.SwipeableLayoutManager
import com.davay.android.feature.selectmovie.di.DaggerSelectMovieFragmentComponent
import com.google.android.material.bottomsheet.BottomSheetBehavior

class SelectMovieFragment :
    BaseFragment<FragmentSelectMovieBinding, SelectMovieViewModel>(FragmentSelectMovieBinding::inflate) {

    override val viewModel: SelectMovieViewModel by injectViewModel<SelectMovieViewModel>()
    private var matchesCounter = 0 // заменить на подписку
    private val cardAdapter = MovieCardAdapter()
    private lateinit var swipeCardLayoutManager : SwipeableLayoutManager

    override fun diComponent(): ScreenComponent =
        DaggerSelectMovieFragmentComponent.builder().appComponent(AppComponentHolder.getComponent())
            .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeCardLayoutManager = SwipeableLayoutManager(requireContext())
        initViews()
        binding.rvFilmCard.post {
            if (binding.rvFilmCard.adapter == null) {
                Log.e("SelectMovieFragment", "RecyclerView adapter is not attached after setting!")
            } else {
                Log.d("SelectMovieFragment", "Adapter successfully attached")
            }
        }
    }

    private fun initViews() {
        buildRecyclerView()
        setToolbar()
        setBottomSheet()
    }

    private fun buildRecyclerView() {
        Log.d("SelectMovieFragment", "Building RecyclerView")
        binding.rvFilmCard.apply {
            layoutManager = swipeCardLayoutManager
            adapter = cardAdapter
        }

        Log.d("SelectMovieFragment", "Adapter attached")
        val itemTouchHelper = ItemTouchHelper(SwipeCallback(onSwipedLeft = {
            // Handle manual swipe left
        }, onSwipedRight = {
            // Handle manual swipe right
        }))

        itemTouchHelper.attachToRecyclerView(binding.rvFilmCard)
    }

    private fun setBottomSheet() = with(binding) {
        BottomSheetBehavior.from(clDetailsBottomSheet).apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
            peekHeight = BOTTOMSHEET_PEEK_HEIGHT_112_DP.dpToPx().toInt()

            clDetailsBottomSheet.post {
                val cardLocation = IntArray(2)
                rvFilmCard.getLocationOnScreen(cardLocation)
                val cardTop = cardLocation[1]

                val screenHeight = resources.displayMetrics.heightPixels
                val maxHeight = screenHeight - cardTop

                clDetailsBottomSheet.layoutParams.height = maxHeight
                clDetailsBottomSheet.requestLayout()
            }
        }
    }

    private fun setToolbar() {
        binding.toolbarviewHeader.apply {
            setStartIcon(com.davai.uikit.R.drawable.ic_cross)
            setEndIcon(com.davai.uikit.R.drawable.ic_heart)
            showEndIcon()
            setTitleText(requireContext().getString(R.string.select_movies_select_film))
            updateMatchesDisplay(matchesCounter)
            addStatusBarSpacer()
        }
    }


    private val mockMovies = listOf(
        MovieDetailsDemo(
            kinopoiskId = 12345,
            imdbId = 67890,
            movieName = "Интерстеллар",
            alternativeName = "Interstellar",
            posterUrl = "https://avatars.mds.yandex.net/get-kinopoisk-image/1946459/7ae40b66-ebda-4570-8676-980344d81bc1/3840x",
            englishName = "Interstellar",
            year = 2014,
            description = "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями.",
            shortDescription = "Эпическое путешествие через галактику.",
            ratingKinopoisk = 8.6f,
            votesKinopoisk = 4532142,
            ratingImdb = 8.6f,
            votesImdb = 112321,
            movieLengthMin = 169,
            genres = listOf("Фантастика", "Драма", "Приключения"),
            countries = listOf("США", "Великобритания"),
            topCast = listOf("Мэттью Макконахи", "Энн Хэтэуэй", "Джессика Честейн"),
            directors = listOf("Кристофер Нолан")
        ), MovieDetailsDemo(
            kinopoiskId = 23456,
            imdbId = 78901,
            movieName = "Тёмный рыцарь",
            posterUrl = "https://kinopoisk-ru.clstorage.net/30H4PS317/fc80630l6W4j/PCNvS_zGy0YMHp7no0AsGkQRD5F3GbGxVVkAfX4LQFrQ2bnuw6YEelehkDaD0ct6S5LEFcsAhqMFiXQJwV2qBmgDGrrdc46T6NorlwYFCJ6RcXtAE5yF8IRsBZiM-ELZ8k6bjTEkBrxDxbTX9mR-Hk-SVJGrlALXUqrFWuOaftAMACwryuRb55hPn2RQI3881LF8VVVJModFOsL-vWiyOGEOGexB9RSdkHY10iYmjX5vsIUCeWGTlCLLc5lj-jxzbAJMCDrFKIYITY200TA9PAVjD5VnKUDnBPjBv-9rYBuQ7wpOAUZGnZM1ZxJEVB6cbhJXMvgSQ2VCeVBo4bmfke9wTQoqNKs1690sBkJT_TyWsT2GlgiQByfLYE8923HqQF47HUNl5JxD9UdgdAfvvC7hlCIbE2PGYynw21I47zHu4O85yuVJtQot_DVxMg6c5fF8xRYJ0ceUS8Le7zsSScAdW98xpAbeIvZ3MldUbO89wjbR2gIAdvILQylzWW7DXTNuWuhVGmV4jF-kI5K9jzbAL9ek6GF1tzqzzM-LABjzLdk9kMZVjPJGtLGk9bwd7TE00ClCcJbQWbB5ghkPs30APJh5t0pFeN8-lXHQTX2mAx_nxysQ1XW60gytqVLYge8KHjCWVD3zNBVxlfR_vGwhtRIbg2EkoEuyOWBJjZMe8bz6CES59apcf4XxIs1cx2MddoRakLfUqXI93mly66MNCV4xB3TMc4VU0beUHJ-t0cbyGHJD5XBpIalRSZ8jPCB8q2qU-oV7r2wGIfJNfQaAbLSW-IPElUqDzS374UlDLTlNkPc1v7PlZNAk5Cx97hBUggowwcZhCaH5wdsuAG0gLop7Fwl2SX1-JcOTHz704Tx09yhh15R7cA-fuXIIoX3rzjHndkyQxaSRtHdujF7jJMGrQMGHYkthuPO7DhON4C4ZmoTJ95rdbCTTUsyetqM8VoYIYqe1qMHMnDviGPPPW-yxxhet0GWEwfZ2f9z8UYSCKKPi9SEoEAuQuNziTaO-GniWegVp_0x3YZFdb8WCPifkWbPFVZsCfc_LIWsiz_sMQBUkrmKnlpKElL3vzGJWkltw8QSCaXMIkjptMD7CTTmbtboXqr_v5wAyDJ2m4-7F1vqDxTWawx6veTI7sg4ZXlKUJGyTJHVxVKeubu3SVkLL0ZJmovuSKFPJX6P9k-9KSmTopFpMfEYgYz8s1XGsZ1d5YpcUatJvPjlx27H8WywDpJbPwaeVcyYFzZ7-YkQwWcLwZDGKMBsTmA8x_QFtKfl0qbfJnGylokK9LcRBH0WXidA21Wiyr_47AvuAPlteAicWnrBX5DJ1dq_fHDHEwklwY5dAegAIQTkuwVxAPlmrJCqF2d1OdQKSvywV4MyGtuhwRoTqgT79-iFJIt747XMVRp0yVodDZ-SeLkxQdWCpADGlA_khukNYT6MNkE8YO0ZJpBoPL6YRos9thWLdR6bpk2V0icH_Djjzq0AcOx8SNgT-EaXnM-f0zmxuIiZyaqBDNqO6UGkDm01j7iAcWeqk65cI3p3n8KIdP-SB3KQnifOl1MjQz_6ZoEsQvvm9QCdmjFH1lDKGdq4-rOJEQOoC0QeSGZD7IIruMGwyDVrptSnmSFyOVuKS7c71gNzGBdmx9dUbos_t-iJLQ14JP0P1NPxgF5VStKStzv4zNyKLY0NEEepAGOPJPDOOMd6LqPSIRZt97WWwYf0_9IAftKeKgtcVWJJcrVsCa5APOb1gN-b9sBZ18CQlnn7MckQyiLNgZWIqEhsQiS3B_BFOKts1GYUp3e50ImFvXtXi3YWnqsPktnjiLN1oMGuhDQkPEUfXb4D0pbE2N3w-z5LWEGoywVeBq1D64wmMAQ3w38pJNthkap_cZEJDbb0kwWz0pusghQbJ4i9ciwG5Y27Kb3DF5v4DtJVQNkQMHAzyNFJIM5NHI9oASvM7D5HPAz7Y6JZbNmp-LqfjYoyeRrPuBUZJs2f2isL8L_qQWKKNq_wjpNT8wTfXQ2WEDIxOcwZS60CxN3JrEHmAaGxgTnI82-ikCVaojj53oFKdfyQwXFalyPDntHowj_854plSnkvcI9fmvtJFVsIEl8x-7wPm46uBYZQQO6Ga8Uk-UVxRv-n4lgommg3ct4JhTT5lYy53V6ihtKX6oAz_iNAr800oD3Fk9v4iFmfyZdaenpwSdRCocALVkinh2KNaTMO8cQ4JWKY7FXleDpUwkC88JDEMBIbLctQlGQDNLfsQOKDs6c0AhpWu8EW10-d1T55OgddQucABFhOL0Dtzea1hjdAc6mpGi7a77z7H48INXpYA_gd1mpLG5ngwfP4q8huynmlewhQXHoIlRfKFt71t3dDWoOqw0reC-iH7gistwi3xHvlptFtVeE_MlhPCXv6Xk-4H9akyd5RLMY8MGoC78O-YHkK0Vm6RlFfQRJY9n46CZBLIovOmsiozGuAIHwANcw0ritbqRpgfLmfiUj38NWGeh6UZ0-Wk2yG_b8tBWmPtCQ9zNsS-0qZFk_dHbF4MIEZAGiDSVJA4c3rAOh9iH8Bf-AhmKxf5Xy1Xk8I__uVjfGclq8Lltalj3e46MpjyrhmewKZGvyOnpeJUV86Pz9C0QKtgoKbwCAOJIShOE_5TXNhpdPvmaWxMpWABPf2GA6yGlMmBtIapoEy_ihD443xaP1EHF3wzFNVQJKTcrz6B1iL6YMPWg2vCGTIqXmM_sc97iabpdpjP_efyEB6cN5Pe59f68hWlWwB_vdoiW5DfeBxSp0ZNAnSU4ZZ0H14tw-SCq6NCtFFqQviRSV5xr8ENKphESYZpbI1mQmHvTEYhzObFyqGndVuCHRw6o6nCfDovYBS0HJGGV4PH9oyeLsDU4bvzg1UTSnIY47jf451R_upI1um3eFwuhhESPw-V0CxFRSsg1RebUr9uatOIww3pbYClVlzyZgWBdiWdji8zl2GachHWs",
            alternativeName = "The Dark Knight",
            englishName = "The Dark Knight",
            year = 2008,
            description = "Бэтмен сражается с Джокером, чтобы спасти Готэм.",
            shortDescription = "Бэтмен против Джокера.",
            ratingKinopoisk = 8.5f,
            votesKinopoisk = 50312,
            ratingImdb = 9.0f,
            votesImdb = 231223,
            movieLengthMin = 152,
            genres = listOf("Экшн", "Криминал", "Драма", "Комиксы"),
            countries = listOf("США", "Великобритания"),
            topCast = listOf("Кристиан Бейл", "Хит Леджер", "Аарон Экхарт"),
            directors = listOf("Кристофер Нолан")
        ), MovieDetailsDemo(
            kinopoiskId = 34567,
            imdbId = 89012,
            movieName = "Властелин колец: Возвращение короля",
            alternativeName = "The Lord of the Rings: The Return of the King",
            englishName = "The Lord of the Rings: The Return of the King",
            year = 2003,
            description = "Заключительная часть эпической трилогии.",
            shortDescription = "Финал эпической трилогии.",
            ratingKinopoisk = 6.7f,
            votesKinopoisk = 400000,
            ratingImdb = 8.9f,
            votesImdb = 1600000,
            movieLengthMin = 201,
            genres = listOf("Фэнтези", "Приключения", "Драма"),
            countries = listOf("США", "Новая Зеландия", "Великобритания", "Австралия"),
            topCast = listOf("Элайджа Вуд", "Вигго Мортенсен", "Иэн Маккеллен"),
            directors = listOf("Питер Джексон", "Кристофер Нолан")
        )
    )

    fun autoSwipeLeft() {
        swipeCardLayoutManager.swipeLeft()
    }

    fun autoSwipeRight() {
        swipeCardLayoutManager.swipeRight()
    }

    fun revertSwipe() {
        swipeCardLayoutManager.revertSwipe()
    }

    private companion object {
        const val BOTTOMSHEET_PEEK_HEIGHT_112_DP = 112
    }
}
