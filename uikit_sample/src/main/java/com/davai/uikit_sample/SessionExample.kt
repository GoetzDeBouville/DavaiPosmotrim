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
        val coverUrl = "https://s5.afisha.ru/mediastorage/ba/60/3a2f91298ada4e1cafb1bf3460ba.jpg"

        setContentView(R.layout.activity_session_example)

        findViewById<SessionView>(R.id.sv_example).apply {
            setDate(date)
            setCoincidences(coincidences)
            setNamesList(namesList)
            setCover(coverUrl)
        }
    }
}