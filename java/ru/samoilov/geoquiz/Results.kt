package ru.samoilov.geoquiz

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private lateinit var exit: Button

private var results: IntArray = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

class Results : AppCompatActivity() {

  @SuppressLint("ClickableViewAccessibility")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_results)

    val intent = intent
    results = intent.getIntArrayExtra(RESULTS)!!

    exit = findViewById(R.id.exit)

    val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.adapter = CustomRecyclerAdapter(results)

    // Выбор следующего вопроса с вариантами ответов при нажатии на кнопку next...
    exit.setOnTouchListener { v, event ->
      when (event.action) {
        MotionEvent.ACTION_UP -> {
          this.finish()
        }
      }

      v?.onTouchEvent(event) ?: true
    }
  }

  companion object {
    const val RESULTS = "results"
  }

}
