/*
 * Copyright 2023 Kalendar Contributors (https://www.himanshoe.com). All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.kalendercustom.customcalender

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kalendercustom.components.day.onDayClicked
import com.example.kalendercustom.components.header.CustomCalenderHeaderUI
import com.example.kalendercustom.components.day.CalenderDayModifier
import com.example.kalendercustom.components.day.CustomCalenderDay
import com.example.kalendercustom.components.ocenic.util.getNext7Dates
import com.example.kalendercustom.components.ocenic.util.getPrevious7Dates
import com.example.kalendercustom.util.annotationclass.MultiplePreviews
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

/**
 * Creates a composable function for the KalendarOceanic component.
 * @param modifier The modifier for styling or positioning the component.
 * @param daySelectionMode The mode for selecting days in the calendar.
 * @param currentDay The current day to highlight in the calendar.
 * @param showLabel Flag indicating whether to show labels for days of the week.
 * @param kalendarHeaderTextKonfig The configuration for styling the calendar header text.
 * @param kalendarColors The colors to be used in the calendar.
 * @param events The events to be displayed in the calendar.
 * @param labelFormat The format function for generating labels for days of the week.
 * @param kalendarDayKonfig The configuration for styling individual days in the calendar.
 * @param dayContent The content to be displayed for each day in the calendar.
 * @param headerContent The content to be displayed in the calendar header.
 * @param onDayClick The callback function when a day is clicked.
 * @param onRangeSelected The callback function when a range of days is selected.
 * @param onErrorRangeSelected The callback function when there is an error in selecting a range of days.
 */
@Composable
internal fun CustomWeekCalender(
    modifier: Modifier = Modifier,
    currentDay: LocalDate? = null,
    labelFormat: (DayOfWeek) -> String = {

        if (it.name == "SATURDAY") {
            "Sa"
        } else if (it.name == "SUNDAY") {
            "Su"
        } else if (it.name == "THURSDAY") {
            "Th"
        } else {
            it.getDisplayName(
                TextStyle.NARROW,
                Locale.getDefault()
            )
        }
    },
    calenderDayModifier: CalenderDayModifier = CalenderDayModifier.default(),
    dayContent: @Composable ((LocalDate) -> Unit)? = null,
    headerContent: @Composable ((Month, Int) -> Unit)? = null,
    onDayClick: (LocalDate) -> Unit = { _ -> }
) {
    val today = currentDay ?: Clock.System.todayIn(TimeZone.currentSystemDefault())
//    val weekValue = remember { mutableStateOf(today.getNext7Dates()) }
    val weekValue = remember { mutableStateOf(today.getNext7Dates()) }
    val yearAndMonth = getCurrentMonthAndYear(weekValue.value)
    var selectedDate by remember { mutableStateOf(today) }
    val currentMonthIndex = yearAndMonth.first.value.minus(1)
    var date: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())

    Column(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(all = 8.dp)
    ) {
        if (headerContent != null) {
            headerContent(yearAndMonth.first, yearAndMonth.second)
        } else {
            CustomCalenderHeaderUI(
                month = yearAndMonth.first,
                year = yearAndMonth.second,
                onPreviousClick = {
                    val firstDayOfDisplayedWeek = weekValue.value.first()
                    weekValue.value = firstDayOfDisplayedWeek.getPrevious7Dates()
                },
                onNextClick = {
                    val lastDayOfDisplayedWeek = weekValue.value.last().plus(1, DateTimeUnit.DAY)
                    weekValue.value = lastDayOfDisplayedWeek.getNext7Dates()
                },
            )
        }
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            content = {
                itemsIndexed(weekValue.value) { _, item ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            modifier = Modifier,
                            color = calenderDayModifier.textColor,
                            fontSize = calenderDayModifier.textSize,
                            text = labelFormat(item.dayOfWeek),
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )


                        Spacer(modifier = Modifier.padding(vertical = 4.dp))

                        if (dayContent != null) {
                            dayContent(item)
                        } else {
                            CustomCalenderDay(
                                date = item,
                                onDayClick = { clickedDate ->
                                    onDayClicked(
                                        clickedDate,
                                        onDayClick = { newDate ->
                                            selectedDate = newDate
                                            onDayClick(newDate)
                                        }
                                    )
                                },
                                selectedDate = selectedDate
                            )
                        }
                    }
                }
            }
        )
    }
}

/**
 * Calculates the current month and year based on a list of dates representing a week.
 * @param weekValue The list of dates representing a week.
 * @return A pair containing the current month and year.
 */
private fun getCurrentMonthAndYear(weekValue: List<LocalDate>): Pair<Month, Int> {
    val month = weekValue.first().month
    val year = weekValue.first().year
    return Pair(month, year)
}

@MultiplePreviews
@Composable
fun CustomWeekCalenderPreview() {
    CustomWeekCalender(
        currentDay = Clock.System.todayIn(
            TimeZone.currentSystemDefault()
        ),
    )
}
