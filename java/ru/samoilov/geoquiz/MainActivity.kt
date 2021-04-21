package ru.samoilov.geoquiz

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible


// Создаем переменные для объектов интерфейса...
private lateinit var prevButton: ImageButton
private lateinit var nextButton: ImageButton
private lateinit var questionTextView: TextView
private lateinit var radioButton1: RadioButton
private lateinit var radioButton2: RadioButton
private lateinit var radioButton3: RadioButton
private lateinit var cheatButton: Button
private lateinit var showResult: Button
private lateinit var exit: Button


// Банк вопросов и ответов по криптографии, включающий в себя 1 вопрос и 3 варианта ответа,
// первый из которых верный...
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

// Переменная currentIndex для подсчета номера вопроса...
private var currentIndex = 0

// Переменная countOfCheats для подсчета количества использованных подсказок...
private var countOfCheats = 0

// Переменная results для сохранения ответов пользователя...
private var results: IntArray = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

// Переменная viewed для сохранения и отображения выбранных пользователем вариантов ответа...
private var viewed: IntArray = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

// Переменная rnds для генерации случайного порядка расположения вариантов ответов...
private var rnds: Int = 0


class MainActivity : AppCompatActivity() {

  @SuppressLint("ClickableViewAccessibility")
  
  // Основная функция при запуске приложения...
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Установка макета activity_main.xml в качестве представления для MainActivity...
    setContentView(R.layout.activity_main)

    // Присвоение переменным элементов интерфейса activity_main...
    prevButton = findViewById(R.id.prev_button)
    nextButton = findViewById(R.id.next_button)
    questionTextView = findViewById(R.id.question_text_view)
    radioButton1 = findViewById(R.id.radio_1)
    radioButton2 =findViewById(R.id.radio_2)
    radioButton3 = findViewById(R.id.radio_3)
    cheatButton = findViewById(R.id.cheat_button)
    showResult = findViewById(R.id.show_result)
    exit = findViewById(R.id.exit)

    // Используем функцию для скрытия кнопки назад и завершить тест...
    updateQuestion()

    // Выбор предыдущего вопроса с вариантами ответов при нажатии на кнопку prev...
    prevButton.setOnTouchListener { v, event ->
      when (event.action) {
        MotionEvent.ACTION_UP -> {

          // Если текущий вопрос не первый в списке, возвращаем предыдущий...
          if (currentIndex != 0) {

            // Сохраняем выбранный вариант ответа на данном вопросе...
            saveResult()

            // Возвращяем предыдущий вопрос...
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

          // Если текущий вопрос не последний в списке, возвращаем следующий...
          if (currentIndex != questionBank.size - 1) {

            // Сохраняем выбранный вариант ответа на данном вопросе...
            saveResult()

            // Возвращяем следующий вопрос...
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

          // Если количество попыток проверки ответа не превышает 3, 
          // вызываем функцию проверки ответа и передаем выбранный вариант...
          if (countOfCheats < 3) {
            when {
              radioButton1.isChecked -> checkAnswer(radioButton1.text.toString())
              radioButton2.isChecked -> checkAnswer(radioButton2.text.toString())
              else -> checkAnswer(radioButton3.text.toString())
            }

            // Увеличиваем количество попыток на единицу...
            countOfCheats++

          } 

          // Иначе выводим сообщение о превышении количества попыток проверки ответа...
          else {
            Toast.makeText(this, R.string.limit_cheats, Toast.LENGTH_SHORT).show()
          }
        }
      }

      v?.onTouchEvent(event) ?: true
    }


    // Завершение работы активности...
    exit.setOnTouchListener { v, event ->
      when (event.action) {
        MotionEvent.ACTION_UP -> {
          this.finish()
        }
      }

      v?.onTouchEvent(event) ?: true
    }

    // Кнопка отображения результатов, появляющаяся на последнем вопросе...
    showResult.setOnTouchListener { v, event ->
      when (event.action) {
        MotionEvent.ACTION_UP -> {

          // Создаем интент с передачей параметра результатов пользователя 
          // и запускаем новую активность Results...
          val intent = Intent(this, Results::class.java)
          intent.putExtra(Results.RESULTS, results)
          startActivity(intent)
        }
      }

      v?.onTouchEvent(event) ?: true
    }

  }

  // Сохраняем результат пользователя и вариант выбранного ответа 
  // для дальнейшнего отображения при просмотре выбранных вариантов...
  private fun saveResult() {
    when {

      // Выбран 1-ый radiobutton...
      radioButton1.isChecked -> {

        if (radioButton1.text.toString() == getString(questionBank[currentIndex].answer1))
          results[currentIndex] = 1

        viewed[currentIndex] = 1

      }

      // Выбран 2-ой radiobutton...
      radioButton2.isChecked -> {

        if (radioButton2.text.toString() == getString(questionBank[currentIndex].answer1))
          results[currentIndex] = 1

        viewed[currentIndex] = 2

      }

      // Выбран 3-ий radiobutton...
      radioButton3.isChecked -> {

        if (radioButton3.text.toString() == getString(questionBank[currentIndex].answer1))
          results[currentIndex] = 1

        viewed[currentIndex] = 3

      }

    }
  }

  // Обновление вопроса и ответов...
  private fun updateQuestion() {

    // Отображение нового вопроса...
    val questionTextResId = questionBank[currentIndex].textResId
    questionTextView.setText(questionTextResId)

    // Сохраняем варианты ответов для дальнейшего отображения...
    val answer1 = questionBank[currentIndex].answer1
    val answer2 = questionBank[currentIndex].answer2
    val answer3 = questionBank[currentIndex].answer3

    // Устанавливаем по умолчанию первый вариант ответа для radiobutton...
    radioButton1.isChecked = true

    // Генерируем в диапазоне от 1 до 3 случайное целое число...
    rnds = (1..3).random()

    // Если вопрос новый, тогда генерируем варианты ответов в случайном порядке...
    if (viewed[currentIndex] == 0) {
      when (rnds) {
        1 -> {
          radioButton1.setText(answer1)
          radioButton2.setText(answer2)
          radioButton3.setText(answer3)
        }
        2 -> {
          radioButton1.setText(answer2)
          radioButton2.setText(answer3)
          radioButton3.setText(answer1)
        }
        3 -> {
          radioButton1.setText(answer3)
          radioButton2.setText(answer1)
          radioButton3.setText(answer2)
        }
      }

    }

    // Если текущий вопрос уже был просмотрен пользователем, например, при переходе 
    // на предудущий вопрос, устанавливаем сохраненный вариант выбранного ответа...
    else {
      when {
        viewed[currentIndex] == 1 -> radioButton1.isChecked = true
        viewed[currentIndex] == 2 -> radioButton2.isChecked = true
        viewed[currentIndex] == 3 -> radioButton3.isChecked = true
      }
    }

    // Скрываем кнопку завершения теста...
    showResult.isVisible = false

    // Отображаем кнопки вперед и назад...
    prevButton.isVisible = true
    nextButton.isVisible = true

    // В случае если текущий вопрос - первый, скрываем кнопку назад...
    if (currentIndex == 0)
      prevButton.isVisible = false

    // В случае если текущий вопрос - последний, скрываем кнопку вперед и показываем 
    // кнопку отображения результаов...
    if (currentIndex == questionBank.size - 1) {
      nextButton.isVisible = false
      showResult.isVisible = true
    }

  }

  // Функция для проверки ответа, переданного в качестве параметра функции и вывода сообщений
  // верно или не верно выбран ответ...
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