package com.example.plexaarproject.ui.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.plexaarproject.databinding.FormLayoutBinding
import com.example.plexaarproject.databinding.SimpletextquestionlayoutBinding
import com.example.plexaarproject.listeners.SpinnerListener
import com.example.plexaarproject.model.QuestionX

class FormAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        var spinnerListener: SpinnerListener? = null
    }

    val formList = mutableListOf<QuestionX>()

    private val TAG = "FormAdapter"

    // Define view types
    private val VIEW_TYPE_NORMAL = 0
    private val VIEW_TYPE_WITH_CHILD = 1

    inner class NormalViewHolder(val binding: FormLayoutBinding) : RecyclerView.ViewHolder(binding.root)
    inner class ChildViewHolder(val binding: SimpletextquestionlayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val binding = FormLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                NormalViewHolder(binding)
            }
            VIEW_TYPE_WITH_CHILD -> {
                val binding = SimpletextquestionlayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ChildViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return formList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val question = formList[position]

        when (holder) {
            is NormalViewHolder -> {
                bindNormalViewHolder(holder, question)
            }
            is ChildViewHolder -> {
                bindChildViewHolder(holder, question)
            }
        }
    }

    private fun bindNormalViewHolder(holder: NormalViewHolder, question: QuestionX) {
        holder.binding.apply {
            tvQuestion.text = question.contentObject.questionText

            val spinner = tvOptions
            val options = mutableListOf("Please select").apply {
                addAll(question.contentObject.options.map { it.choiceText })
            }
            val adapter = ArrayAdapter(root.context, android.R.layout.simple_spinner_item, options)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    // Handle selected item
                    Log.d(TAG, "onItemSelected: ${options[position]}")
                    spinnerListener?.onItemSelected(options[position], objectId = question.contentObject.id, holder.adapterPosition)

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle nothing selected
                    Log.d(TAG, "onNothingSelected: ")
                    spinnerListener?.onNothingSelected()
                }
            }
        }
    }

    private fun bindChildViewHolder(holder: ChildViewHolder, question: QuestionX) {
        // Bind child view holder

        holder.binding.apply {
            tvChildQuestion.text = question.contentObject.questionText
        }


    }



    override fun getItemViewType(position: Int): Int {
        val question = formList[position]
        return if (question.contentType == "api | small text question") {
            VIEW_TYPE_WITH_CHILD
        } else {
            VIEW_TYPE_NORMAL
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFormData(data: List<QuestionX>) {
        formList.clear()
        formList.addAll(data)
        notifyDataSetChanged()
    }
}
