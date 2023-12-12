package com.example.kalendercustom.presentation.screens

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.kalendercustom.customcalender.HistoryCalender
import com.example.kalendercustom.ui.theme.KalenderCustomTheme
import com.example.kalendercustom.ui.theme.Primary
import com.example.kalendercustom.ui.theme.Secondary
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

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Primary)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(12.dp)
                .shadow(
                    elevation = 5.dp,
                    shape = RoundedCornerShape(20.dp)
                )
                .background(
                    color = Secondary
                )
                .align(alignment = Alignment.TopCenter)
        ) {
            HistoryCalender(
                currentDay = Clock.System.todayIn(
                    TimeZone.currentSystemDefault()
                ), onDayClick = { localDate ->
                    Toast.makeText(activity, "I have click on $localDate date.", Toast.LENGTH_SHORT).show()
                })
        }
    }


}

