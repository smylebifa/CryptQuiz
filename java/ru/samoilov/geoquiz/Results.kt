package ru.samoilov.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class Results : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_results)

    val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.adapter = CustomRecyclerAdapter(fillList())
  }

  private fun fillList(): List<String> {
    val data = mutableListOf<String>()
    (1..10).forEach { i -> data.add("$i") }
    return data
  }

//  companion object {
//
//    const val TOTAL_COUNT = "total_count"
//
//  }

}
