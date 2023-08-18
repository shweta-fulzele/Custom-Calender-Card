package com.himanshoe.kalendar.ui.component.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

//class DayOfWeekViewModel : ViewModel() {
//
//    private val _visibleDates =
//        MutableStateFlow(
//            calculateCollapsedCalendarDays(
//                startDate = LocalDate.now().getWeekStartDate().minusWeeks(1)
//            )
//        )
//    val visibleDates: StateFlow<Array<List<LocalDate>>> = _visibleDates
//}