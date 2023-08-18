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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.himanshoe.kalendar.ui.component.day.CalenderDayModifier
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import java.time.Month

/**
 * Composable function that represents a calendar component.
 *
 * @param currentDay The current selected day in the calendar.
 * @param kalendarType The type of calendar to be displayed.
 * @param modifier The modifier for styling or positioning the calendar.
 * @param showLabel Determines whether to show the labels for days of the week.
 * @param kalendarHeaderTextKonfig The configuration for the header text in the calendar.
 * @param kalendarColors The colors used for styling the calendar.
 * @param kalendarDayKonfig The configuration for each day cell in the calendar.
 * @param dayContent The content to be displayed inside each day cell of the calendar.
 * @param daySelectionMode The mode for selecting days in the calendar.
 * @param headerContent The content to be displayed in the header of the calendar.
 * @param onDayClick Callback function triggered when a day cell is clicked.
 * @param onRangeSelected Callback function triggered when a range of days is selected.
 * @param onErrorRangeSelected Callback function triggered when an error occurs during range selection.
 */
@Composable
fun HistoryCalender(
    currentDay: LocalDate?,
    modifier: Modifier = Modifier,
    calenderDayModifier: CalenderDayModifier = CalenderDayModifier.default(),
    dayContent: (@Composable (LocalDate) -> Unit)? = null,
    headerContent: (@Composable (Month, Int) -> Unit)? = null,
    onDayClick: (LocalDate) -> Unit = { _ -> },
) {
    Calender(
        currentDay = currentDay,
        modifier = modifier,
        onDayClick = onDayClick,
        dayContent = dayContent,
        headerContent = headerContent)
}

/**
 * Composable function that represents a calendar component.
 *
 * @param currentDay The current selected day in the calendar.
 * @param kalendarType The type of calendar to be displayed.
 * @param modifier The modifier for styling or positioning the calendar.
 * @param showLabel Determines whether to show the labels for days of the week.
 * @param events The events to be displayed in the calendar.
 * @param kalendarHeaderTextKonfig The configuration for the header text in the calendar.
 * @param kalendarColors The colors used for styling the calendar.
 * @param kalendarDayKonfig The configuration for each day cell in the calendar.
 * @param daySelectionMode The mode for selecting days in the calendar.
 * @param dayContent The content to be displayed inside each day cell of the calendar.
 * @param headerContent The content to be displayed in the header of the calendar.
 * @param onDayClick Callback function triggered when a day cell is clicked.
 * @param onRangeSelected Callback function triggered when a range of days is selected.
 * @param onErrorRangeSelected Callback function triggered when an error occurs during range selection.
 */
@Composable
fun Calender(
    currentDay: LocalDate?,
    modifier: Modifier = Modifier,
    dayContent: (@Composable (LocalDate) -> Unit)? = null,
    headerContent: (@Composable (Month, Int) -> Unit)? = null,
    onDayClick: (LocalDate) -> Unit = { _ -> },

    ) {
    CustomWeekCalender(
        currentDay = currentDay,
        modifier = modifier,
        onDayClick = onDayClick,
        dayContent = dayContent,
        headerContent = headerContent,

        )
}

@Preview
@Composable
fun CustomCalenderWeekPreview() {

    Calender(currentDay = Clock.System.todayIn(TimeZone.currentSystemDefault()))
}
