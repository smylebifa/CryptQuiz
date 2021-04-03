package ru.samoilov.geoquiz

import androidx.annotation.StringRes

data class Question(@StringRes val textResId: Int, 
                    @StringRes val answer1: Int, 
                    @StringRes val answer2: Int, 
                    @StringRes val answer3: Int)