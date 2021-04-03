package ru.samoilov.geoquiz

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider


// Создаем переменные для объектов интерфейса...
private lateinit var prevButton: ImageButton
private lateinit var nextButton: ImageButton
private lateinit var questionTextView: TextView
private lateinit var radioButton1: RadioButton
private lateinit var radioButton2: RadioButton
private lateinit var radioButton3: RadioButton
private lateinit var cheatButton: Button


// Банк вопросов по криптографии с 3 вариантами ответа, первый из которых верный...
private val questionBank = listOf(
        Question(R.string.question_1, R.string.answer_1_1, R.string.answer_1_2, R.string.answer_1_3),
        Question(R.string.question_2, R.string.answer_2_1, R.string.answer_2_2, R.string.answer_2_3),
        Question(R.string.question_3, R.string.answer_3_1, R.string.answer_3_2, R.string.answer_3_3),
        Question(R.string.question_4, R.string.answer_4_1, R.string.answer_4_2, R.string.answer_4_3),
        Question(R.string.question_5, R.string.answer_5_1, R.string.answer_5_2, R.string.answer_5_3),
        Question(R.string.question_6, R.string.answer_6_1, R.string.answer_6_2, R.string.answer_6_3),
        Question(R.string.question_7, R.string.answer_7_1, R.string.answer_7_2, R.string.answer_7_3),
        Question(R.string.question_8, R.string.answer_8_1, R.string.answer_8_2, R.string.answer_8_3),
        Question(R.string.question_9, R.string.answer_9_1, R.string.answer_9_2, R.string.answer_9_3),
        Question(R.string.question_10, R.string.answer_10_1, R.string.answer_10_2, R.string.answer_10_3))

// Переменная для подсчета номера вопроса...
private var currentIndex = 0


class MainActivity : AppCompatActivity() {

    @SuppressLint("ClickableViewAccessibility")

    // Основная функция при запуске приложения...
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Присвоение переменным элементам интерфейса activity_main...
        prevButton = findViewById(R.id.prev_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)
        radioButton1 = findViewById(R.id.radio_1)
        radioButton2 =findViewById(R.id.radio_2)
        radioButton3 = findViewById(R.id.radio_3)
        cheatButton = findViewById(R.id.cheat_button)

        // Обновляем в начале для скрытия кнопки назад...
        updateQuestion()

        // Выбор предыдущего вопроса с вариантами ответов при нажатии на кнопку prev...
        prevButton.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                     if (currentIndex != 0) {
                         currentIndex--
                         updateQuestion()
                     }

                }
            }

            v?.onTouchEvent(event) ?: true
        }

        // Выбор следующего вопроса с вариантами ответов при нажатии на кнопку next...
        nextButton.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_UP -> {
                        if (currentIndex != questionBank.size - 1) {
                            currentIndex++
                            updateQuestion()
                    }

                    }
            }

            v?.onTouchEvent(event) ?: true
        }

        // Вывод сообщения верно или нет выбран ответ при нажатии на кнопку проверить...
        cheatButton.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    when {
                        radioButton1.isChecked -> checkAnswer(radioButton1.text.toString())
                        radioButton2.isChecked -> checkAnswer(radioButton2.text.toString())
                        else -> checkAnswer(radioButton3.text.toString())
                    }
                }
            }

            v?.onTouchEvent(event) ?: true
        }

        updateQuestion()

    }


    // Отображение текущего вопроса в поле TextView, ответов в качестве текста у RadioButton.
    // Номер правильного ответа чередуется в такой последовательности 1 - 2 - 1 - 2 - etc.
    // Проверка для отображения кнопок и скрытия для первого и последнего вопроса соотвественно...
    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId

        val answer1 = questionBank[currentIndex].answer1
        val answer2 = questionBank[currentIndex].answer2
        val answer3 = questionBank[currentIndex].answer3

        questionTextView.setText(questionTextResId)

        if (currentIndex % 2 == 0) {
            radioButton1.setText(answer2)
            radioButton2.setText(answer1)
            radioButton3.setText(answer3)
        }

        else {
            radioButton1.setText(answer1)
            radioButton2.setText(answer2)
            radioButton3.setText(answer3)
        }

        prevButton.isVisible = true
        nextButton.isVisible = true

        if (currentIndex == 0)
            prevButton.isVisible = false

        if (currentIndex == questionBank.size - 1)
            nextButton.isVisible = false


        radioButton1.isChecked = false
        radioButton2.isChecked = false
        radioButton3.isChecked = false

    }

    // Функция для проверки ответа, переданного в качестве параметра функции...
    private fun checkAnswer(userAnswer: String) {
        
        val correctAnswer = getString(questionBank[currentIndex].answer1)
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

    }

}