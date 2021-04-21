package ru.samoilov.geoquiz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CustomRecyclerAdapter(private var results: IntArray) :
    RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // Переменные для объектов интерфейса отдельного элемента списка...
        var questionNumber: TextView? = null
        var answer: TextView? = null

        // Присвоение переменным элементов интерфейса recyclerview_item через идентификаторы...
        init {
            questionNumber = itemView.findViewById(R.id.questionNumber)
            answer = itemView.findViewById(R.id.answer)
        }
    }

    // Указание идентификатора используемого макета для отдельного элемента списка...
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item, parent, false)

        return MyViewHolder(itemView)
    }

    // Присвоение текстовым меткам данных - номера вопроса и значение ответа...
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val questionNum = position + 1
        holder.questionNumber?.text = questionNum.toString()

        if (results[position] == 1) holder.answer?.text = "+"
        else holder.answer?.text = "-"

    }
    
    // Функция получения количество записей для распределения ресурсов и подготовки
    // для отображения их на экране...
    override fun getItemCount() = results.size
}