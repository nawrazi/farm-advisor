package com.enterprise.agino.ui.onboarding.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Slide(
    @DrawableRes
    val imageId: Int,
    @StringRes
    val title: Int,
    @StringRes
    val description: Int
)
