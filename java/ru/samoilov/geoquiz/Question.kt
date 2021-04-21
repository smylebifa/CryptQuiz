package ru.samoilov.geoquiz

import androidx.annotation.StringRes

// Специально созданный класс Question для хранения вопроса и 3 вариантов ответа...
data class Question(@StringRes val textResId: Int, 
                    @StringRes val answer1: Int, 
                    @StringRes val answer2: Int, 
                    @StringRes val answer3: Int)