package ru.samoilov.geoquiz

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// Создаем переменные для объектов интерфейса...
private lateinit var exit: Button
private lateinit var score: TextView

// Переменная для хранения результатов пользователя...
private var results: IntArray = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

class Results : AppCompatActivity() {

  @SuppressLint("ClickableViewAccessibility")

  // Основная функция при запуске приложения...
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    // Установка макета activity_results.xml в качестве представления для Results...
    setContentView(R.layout.activity_results)

    // Создаем интент и получаем результаты в видема массива из MainActivity...
    val intent = intent
    results = intent.getIntArrayExtra(RESULTS)!!

    // Присвоение переменным элементов интерфейса activity_results...
    exit = findViewById(R.id.exit)
    score = findViewById(R.id.score)

    // Устанавливаем значение счета в виде соотношения верно введеных вариантов ответов ко всем...
    val scoreText: String = results.sum().toString() + "/" + results.count().toString()
    score.text = scoreText

    // Определение списка RecyclerView и создание адаптера для отображения данных в нем...
    val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.adapter = CustomRecyclerAdapter(results)

    // Завершение работы активности...
    exit.setOnTouchListener { v, event ->
      when (event.action) {
        MotionEvent.ACTION_UP -> {
          this.finish()
        }
      }

      v?.onTouchEvent(event) ?: true
    }
  }

  // Объект содержащий ключевое слово results для осуществления связи между активностями...
  companion object {
    const val RESULTS = "results"
  }

}
