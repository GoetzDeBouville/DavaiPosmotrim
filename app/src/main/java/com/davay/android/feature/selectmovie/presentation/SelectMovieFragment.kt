package com.davay.android.feature.selectmovie.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import com.davay.android.R
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentSelectMovieBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.extensions.SwipeDirection
import com.davay.android.extensions.dpToPx
import com.davay.android.feature.selectmovie.di.DaggerSelectMovieFragmentComponent
import com.davay.android.feature.selectmovie.domain.models.MovieDetailsDemo
import com.davay.android.feature.selectmovie.presentation.adapters.MovieCardAdapter
import com.davay.android.feature.selectmovie.presentation.adapters.SwipeCallback
import com.davay.android.feature.selectmovie.presentation.adapters.SwipeableLayoutManager
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior

class SelectMovieFragment :
    BaseFragment<FragmentSelectMovieBinding, SelectMovieViewModel>(FragmentSelectMovieBinding::inflate) {

    override val viewModel: SelectMovieViewModel by injectViewModel<SelectMovieViewModel>()
    private var matchesCounter = 0
    private val cardAdapter = MovieCardAdapter(
        swipeLeft = { autoSwipeLeft() },
        swipeRight = { autoSwipeRight() },
        revert = { revertSwipe() },
        inflateMovieDetails = { movie -> inflateMovieDetails(movie) }
    )
    private val swipeCardLayoutManager = SwipeableLayoutManager()
    private var currentPosition = 0

    override fun diComponent(): ScreenComponent =
        DaggerSelectMovieFragmentComponent.builder().appComponent(AppComponentHolder.getComponent())
            .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSavedPositionAndUpdateStartPosition(savedInstanceState)
        initViews()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        currentPosition = swipeCardLayoutManager.getCurrentPosition()
        outState.putInt(CURRENT_POSITION_KEY, currentPosition)
    }

    private fun getSavedPositionAndUpdateStartPosition(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            currentPosition = it.getInt(CURRENT_POSITION_KEY, 0)
        }
        swipeCardLayoutManager.updateCurrentPosition(currentPosition)
    }

    private fun initViews() {
        buildRecyclerView()
        setToolbar()
        setBottomSheet()
    }

    private fun buildRecyclerView() {
        cardAdapter.setData(mockMovies)
        binding.rvFilmCard.apply {
            layoutManager = swipeCardLayoutManager
            adapter = cardAdapter
            // помимо установки позици на layputmanger дополнительно скролим до необходимой позиции
            scrollToPosition(currentPosition)
        }

        val itemTouchHelper = ItemTouchHelper(
            SwipeCallback(
                swipeCardLayoutManager,
                onSwipedLeft = {
                    // Add skip method
                    Log.e("MyLog", "swiped left")
                },
                onSwipedRight = {
                    // Add like method
                    Log.e("MyLog", "swiped right")
                }
            )
        )

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

                val screenHeight = activity?.resources?.displayMetrics?.heightPixels ?: 0
                val maxHeight = screenHeight - (cardTop + MARGIN_TOP_16_DP.dpToPx().toInt())

                clDetailsBottomSheet.layoutParams.height = maxHeight
                clDetailsBottomSheet.requestLayout()
            }
        }
    }

    private fun setToolbar() {
        binding.toolbarviewHeader.apply {
            updateMatchesDisplay(matchesCounter)
            addStatusBarSpacer()
        }
    }

    private fun autoSwipeLeft() {
        swipeCardLayoutManager.moveNextWithSwipeAndLayout(SwipeDirection.LEFT)
        cardAdapter.notifyDataSetChanged()
    }

    private fun autoSwipeRight() {
        swipeCardLayoutManager.moveNextWithSwipeAndLayout(SwipeDirection.RIGHT)
        cardAdapter.notifyDataSetChanged()
    }

    private fun revertSwipe() {
        swipeCardLayoutManager.shiftLeftWithRevertAndLayout()
        cardAdapter.notifyDataSetChanged()
    }

    private fun inflateMovieDetails(movie: MovieDetailsDemo) = with(binding) {
        tvDetailsDescription.text = movie.description
        setRates(movie)
        inflateCastList(fblDetailsTopCastList, movie.topCast)
        inflateCastList(fblDetailsDirectorList, movie.directors)
    }

    private fun setRates(movie: MovieDetailsDemo) = with(binding) {
        if (movie.ratingImdb == null) {
            mevDetailsImdbRate.visibility = View.GONE
        } else {
            mevDetailsImdbRate.apply {
                setRateNum(movie.ratingImdb)
                setNumberOfRatesString(movie.votesImdb ?: 0)
            }
        }

        if (movie.ratingKinopoisk == null) {
            mevDetailsKinopoiskRate.visibility = View.GONE
        } else {
            mevDetailsKinopoiskRate.apply {
                setRateNum(movie.ratingKinopoisk)
                setNumberOfRatesString(movie.votesKinopoisk ?: 0)
            }
        }
    }

    private fun inflateCastList(fbl: FlexboxLayout, list: List<String>) {
        fbl.removeAllViews()
        val castList = list.take(MAX_CAST_NUMBER_4)
        castList.forEach {
            val castView = LayoutInflater.from(requireContext()).inflate(
                R.layout.item_top_cast,
                fbl,
                false
            ) as TextView
            castView.text = it
            fbl.addView(castView)
        }
    }

    private companion object {
        const val BOTTOMSHEET_PEEK_HEIGHT_112_DP = 112
        const val MARGIN_TOP_16_DP = 16
        const val MAX_CAST_NUMBER_4 = 4
        const val CURRENT_POSITION_KEY = "currentPosition"
    }
}

// В коде используются моковые данные
@Suppress(
    "Detekt.MaxLineLength",
    "Detekt.StringLiteralDuplication",
    "Detekt.ArgumentListWrapping",
    "Detekt.Wrapping",
    "Detekt.UnderscoresInNumericLiterals"
)
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
        ratingImdb = 9.6f,
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
        description = "Бэтмен поднимает ставки в войне с криминалом. С помощью лейтенанта Джима Гордона и прокурора Харви Дента он намерен очистить улицы Готэма от преступности. Сотрудничество оказывается эффективным, но скоро они обнаружат себя посреди хаоса, развязанного восходящим криминальным гением, известным напуганным горожанам под именем Джокер.",
        shortDescription = "Бэтмен против Джокера.",
        ratingKinopoisk = 8.5f,
        votesKinopoisk = 50312,
        ratingImdb = 9.0f,
        votesImdb = 231223,
        movieLengthMin = 152,
        genres = listOf("Экшн", "Криминал", "Драма", "Комиксы", "Детектив", "Мелодрама", "Байопик"),
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
        posterUrl = "https://kinopoisk-ru.clstorage.net/q16Zn1136/6f24c6b4xm/4L1PkIrF2UWxiuivpK_AEO9-C9xtrDXcdIDX5Zv5mqrMhG2BJ4TOjizUU8r6IHkeYZ5txyFH5J7nNsBjNiGaKqLoCZfVjsYR4NSg4_b2lxSiMgSOLuV9jzCdxLXFprWa5LZE8wG75q4AtRKPvT8mRz7efJosAe3T9CayXScu-XbfiWiymfK4ZrySq6y8puASzTUH9mHzOMp3-_7HltSIkcadTPg5o_yhIrYsrqh0FH823WqhKRbpsOMaukVADexlz45coK_8uk-4u6GCrqjtJfMzN9xW7QHyYeXN4LD3vIPuvXHFKpvMmSSvN4ygcDlCAvdHkTAc46rHHrRSdiXmX8OXSKSh0qZbrIiQtbywyT3AJSDeds037WL87vmn2byi3IZk5z67244NkhyRuUYiexPmYLEVN-Cz1CG_c3EX1UrikUKen8yZZ5ibtaKEsfUy4RwHyGPgKMhn9cTKpNKlqPGcUuc8gs2eBJ0Im5BXAkkS9V6FDALfi8UFn2ZgAsxmwZBCt5n1gHyZlLGkiLTWL801LfxAxxzRdtvUyp7uo7LSnVzAIq3suyauH4u7aBpKO-dKvAYazrHEK6xMaCzGdvOITJ661aNShq2Eg7ySzw3vFRT8QdoazEv5-fqG76aWwJ5cwCKRxYcNmBCKv3kdTAX1T600H-m15QGoYEUU72Lnu1aEodSfYJKIqIOoptQB5x4C80HuNf9ByPDlrNeQsuC_bOkUmMCsKJEGrIRLP0s0-me7PTvMqMQErHd2ENlt179zuZ_5nlqxnKG7orTHONUGMtNu1jz-b8f54JreoYPRh0D5L4zdpD63LrW5ciZGPuBdsDQH_JrSP5ljTijHRfCfXaGw_758mq-KrrGL7S_FESnKafcHzEjU6OCn44Oq9Ydc1T-h5L4PtSq_jWUgbAD8X502N-OM7hCmQEgx1EjetEiemt2ySqGhnp2HuO0Ywzk0ylDoI_VYz8b-h_S_idaPSOgvp-OeILEmvrhyO0Yg-Vi2JwPWrvYdmFN0LeZz75Rjm7HgpF2eibSEnaLQEfwVH-xK5yHsTNXy54XCq5HBm0DvCJHHvR66Iaygcix3F_RbmTU_x4viH6tWbTnubuWPVIuZ3oRunq6WsaaCyx_aBQbtYfIY0Gba1-6axI-N671i-CGI2oM4jAKxoVgLexjma5gKAMeO8wGjZ38G00_eq0KjrtyvToKds6yAkdw6_h8WymztOvB0zv7JhfyDg-CPcPs7qOSFL5Amk5BVJHw75k--MRnIlegenGR_Ast_95Z3k57CtmOHj7-ZgZHgMeA9CeF6wCTWZdTM_7jEp5bGhFLlK77opzOLMauDWyF8Nd9dszIo96TFF6l2TwLzQfumeJWOwZVppJqKrq2mzR30MgnoYfEI1mXM2uCP3pmqxJJxxzyk2ZEkjQKZg18KVh7SVKIXPf65xTS6UU4EzmHEuU2olMauSoGPrLKUjc0m5RMIy13zIv5L5s_ZnNytvfCYSsErg-aIEYwzkKtlF2M94EGRNDvxlP4chXFLNetK4Y9Mma7OhGy7mre8q7DIDOYpCtF47TzLed735JHHr6fJk2v6K4r8pzu5EKqrVy9aPeRdpQo-zJHKEaFrQRPMctWuU7So2JZOhI-ov7OAwx7gOy3Yc_sE8lPq2u-N3YmAwplP6xiM2YwslyGegXgDaQXZRJg_GtyU7Da3T2kM2V_AmG68n9S0UJiAiaOmkcYy_hgW9XPgH_Bx597hrNiZlcewVt44neKrD7MImaVyGXwc4mScIybCisgbgmdvAfJj869pv4_ssXycupWcporWHNYeF8t11x_xdsbM3bPhpaTchHzkKa78sC2ZPZaLXyRMF_RYpwET1K7QPqVnXRXSbcKqT5ew_plRvayMlJC53D3zAwDufdcI4HjA8vyH-6Kz3Jhc3C-fw4MitAi_hksjQz_Ga4QnJvSu3BynZ2oi-mbakW6_s8OxYqyap5mLg-8h-AYdwFj7Oc1RwtDmktuYqe2DXvkMguCRO5Mjor1VGWQS32iaJhXDheUnjnZGNNlk35VIp4r5o1CDoK6wl5blBd0YLN9e1wDXSfnt1aDsuq_VhE_7PY7ViiiTDp6gUzx5BN1Ntycg0qfJOrRjUiHHROKaebWKwr5eurinl4mZzBLNABzQXMsz3HDl1NS_wKm2zbhI2hqY4rkuugK_uGAMSzPZbqEOH8ucyBisYGUN8ULerWKzq8SOTIKrtKCNp8gS7wYX0WnwONdX_d_frdCHk--ka-MPk-CIBa4Rq7peNHsmwXyFFwTUpewVumxxKvRW_ZJfs4nRuVi7l6isj7LCEfMyPNNWxxv-aOz52pzRraDduFzqKbz3hTORDpCFXQd5HvV2swoG96rlPI5lUxH6dvW4SqmJ9I5gopSNg6Si3gzNABfeSss063D0xf6E84mtz6Ni_D-51Yggki-Ion8AVifkdp0zPtae_zihT18w20ravlSLr-OeYIKPoJmWhe8h-igE32rMG-lT1fD5jMeghMmkUsUgu-GNMasLrIZUDEUT5GKlBhD-isw4jEZxM8Riz7ZWo4z0rmqkjZWklrneBOAHLNFu2wLNRdry1obhmLDmsVP8K63YuwWaIKKtex99MvVahyA2yJvfOZdUfTDZa_y3bIGO8KFNvZ-wl42E_gTjGxPPcu4D22rH_uSH8rCTz6Jn_yW82qAvgTaOklg0SCX_f4cMM8K73BiebF8I1VbssU-njdOie42NkJ-cucY8xgE97U_tONdFwvPanuymq8yyYuo7qsCnO4IWvYF0KWA6yEqgIwHkmMMLvktXI-dw3qlKl6r-mV2SoLSap4_hIO8YLuxd0RrMQvnm9J7AhZfCtkTgJYz9mC2QDJOleQFpP8JpgT0w16XhFaNnQjHXRPOmeIO-_JNqpqCHu5Cu3RLPPwjKdeQ_13f78uK-4YSty7trwzmEy7sKgiGygWcCbyfLWYYKKs2VxQo",
        ratingKinopoisk = 6.7f,
        votesKinopoisk = 400000,
        ratingImdb = 8.9f,
        votesImdb = 198728,
        movieLengthMin = 201,
        genres = listOf("Фэнтези", "Приключения", "Драма"),
        countries = listOf("США", "Новая Зеландия", "Великобритания", "Австралия"),
        topCast = listOf("Элайджа Вуд", "Вигго Мортенсен", "Иэн Маккеллен"),
        directors = listOf("Питер Джексон", "Кристофер Нолан")
    ), MovieDetailsDemo(
        kinopoiskId = 3457,
        imdbId = 8912,
        posterUrl = "https://avatars.mds.yandex.net/get-kinopoisk-image/1777765/cb430f00-1734-4078-abd2-6688a94749a5/3840x",
        movieName = "Доктор Стрейнджлав, или Как я научился не волноваться и полюбил атомную бомбу",
        alternativeName = "Dr. Strangelove or: How I Learned to Stop Worrying and Love the Bomb",
        englishName = "Dr. Strangelove or: How I Learned to Stop Worrying and Love the Bomb",
        year = 1963,
        description = "Одержимый мыслью, что коммунисты намереваются украсть у американцев их «бесценные телесные соки», командир военно-воздушной базы генерал Джек Д. Риппер посылает эскадрилью бомбардировщиков с ядерным оружием бомбить СССР. Президент США Маффли пытается спасти положение — он собирает своих советников, включая доблестного генерала Тергидсона и прикованного к инвалидной коляске бывшего нацистского ученого доктора Стрейнджлава.",
        ratingKinopoisk = 6.7f,
        votesKinopoisk = 4099199,
        ratingImdb = 5.9f,
        votesImdb = 16090201,
        movieLengthMin = 201,
        genres = listOf("комедия", "фантастика", "триллер"),
        countries = listOf("США", "Великобритания", "Австралия"),
        topCast = listOf(
            "Питер Селлерс",
            "Джордж К. Скотт",
            "Стерлинг Хейден",
            "Кинен Уинн",
            "Слим Пикенс",
            "Питер Булл",
            "Джеймс Эрл Джонс",
            "Трейси Рид",
            "Джек Крили",
            "Фрэнк Берри"
        ),
        directors = listOf("Стэнли Кубрик", "Терри Саузерн", "Питер Джордж")
    )
)
