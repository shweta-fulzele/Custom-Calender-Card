package com.example.kalendercustom

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat.startActivity
import com.example.kalendercustom.customcalender.HistoryCalender
import com.example.kalendercustom.ui.theme.KalenderCustomTheme
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KalenderCustomTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CustomCalenderUI(this)
                }
            }
        }
    }
}

@Composable
fun CustomCalenderUI(activity: MainActivity){
    Column{
        HistoryCalender(
            currentDay = Clock.System.todayIn(
                TimeZone.currentSystemDefault()
            ), onDayClick = { localDate ->
                val intent = Intent(activity, MainActivity2::class.java )
                intent.putExtra("LOCALDATE", localDate.dayOfMonth.toString())
                activity.startActivity(intent)
            })
    }

}

