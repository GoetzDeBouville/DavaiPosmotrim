package com.davai.uikit_sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.davai.uikit.SessionView

@Suppress("Detekt:MagicNumber")
class SessionExample : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val date = "1 января"
        val coincidences = 6
        val namesList = "Дима (вы), Петя, Вася, Катя, Маша, Тимофеевна"
        val coverUrl =
            "https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/6e9e2641-5750-4991-a52b-8c1cd0e80b4c/3840x"

        setContentView(R.layout.activity_session_example)

        findViewById<SessionView>(R.id.sv_example).apply {
            setDate(date)
            setCoincidences(coincidences)
            setNamesList(namesList)
            setCover(coverUrl)
        }

        findViewById<SessionView>(R.id.sv_example2).apply {
            setDate(date)
            setCoincidences(3)
            setNamesList(namesList)
            setCover("bla-bla-bla-bla-bla-bla-bla-bla")
        }
    }
}