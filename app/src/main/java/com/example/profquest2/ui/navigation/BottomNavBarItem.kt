package com.example.profquest2.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class BottomNavBarItem(
    val route: String,
    @DrawableRes val icon: Int,
    @DrawableRes val selectedIcon: Int,
    @StringRes val name: Int
)