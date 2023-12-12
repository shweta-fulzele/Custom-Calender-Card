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

package com.example.kalendercustom.components.header

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.sp
import com.example.kalendercustom.ui.theme.Primary
import com.example.kalendercustom.util.annotationclass.MultiplePreviews
import kotlinx.datetime.Month
import java.time.format.TextStyle
import java.util.Locale

/**
 * Composable function for displaying the header of the Kalendar, which includes the month and year.
 *
 * @param month The current month to display.
 * @param year The current year to display.
 * @param kalendarTextKonfig The configuration for styling the header text.
 * @param modifier The modifier for styling the header.
 * @param onPreviousClick The callback for when the previous arrow button is clicked.
 * @param onNextClick The callback for when the next arrow button is clicked.
 * @param arrowShown Determines whether the arrow buttons should be shown.
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CustomCalenderHeaderUI(
    month: Month,
    year: Int,
    modifier: Modifier = Modifier,
    onPreviousClick: () -> Unit = {},
    onNextClick: () -> Unit = {},
    arrowShown: Boolean = true
) {
    var isNext by remember { mutableStateOf(true) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(all = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val titleText = remember(month) { getTitleText(month) }

            Row(
                modifier = Modifier
                    .fillMaxWidth().padding(vertical = 6.dp)
                    .align(Alignment.CenterVertically),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = {
                        isNext = false
                        onPreviousClick()
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        tint = Primary,
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Previous Month"
                    )
                }

                AnimatedContent(
                    targetState = titleText,
                    transitionSpec = {
                        addAnimation(isNext = isNext).using(
                            SizeTransform(clip = false)
                        )
                    }
                ) { targetText ->
                    Text(
                        modifier = Modifier
                            .wrapContentHeight()
                            .wrapContentWidth()
                            .align(Alignment.CenterVertically),
                        color = Primary,
                        fontSize = 28.sp,
                        text = targetText,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )
                }

                IconButton(
                    onClick = {
                        isNext = true
                        onNextClick()
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        tint = Primary,
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = "Next Month"
                    )
                }
        }
    }
}

/**
 * Adds the animation to the content based on the given duration and direction.
 *
 * @param duration The duration of the animation in milliseconds.
 * @param isNext Determines the direction of the animation.
 * @return The content transformation with the specified animation.
 */
@OptIn(ExperimentalAnimationApi::class)
private fun addAnimation(duration: Int = 200, isNext: Boolean): ContentTransform {
    return slideInVertically(
        animationSpec = tween(durationMillis = duration)
    ) { height -> if (isNext) height else -height } + fadeIn(
        animationSpec = tween(durationMillis = duration)
    ) with slideOutVertically(
        animationSpec = tween(durationMillis = duration)
    ) { height -> if (isNext) -height else height } + fadeOut(
        animationSpec = tween(durationMillis = duration)
    )
}

/**
 * Returns the formatted title text for the Kalendar header.
 *
 * @param month The current month.
 * @param year The current year.
 * @return The formatted title text.
 */
private fun getTitleText(month: Month): String {
    val monthDisplayName = month.getDisplayName(TextStyle.FULL, Locale.getDefault())
        .lowercase()
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    return monthDisplayName
}

@MultiplePreviews
@Composable
fun CalenderHeaderUIPreview() {
    CustomCalenderHeaderUI(
        month = java.time.Month.APRIL,
        year = 2023,
    )
}
