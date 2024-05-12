package com.example.plexaarproject.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.plexaarproject.databinding.FragmentHomeBinding
import com.example.plexaarproject.events.ApiResponse
import com.example.plexaarproject.listeners.SpinnerListener
import com.example.plexaarproject.model.QuestionX
import com.example.plexaarproject.ui.adapters.FormAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val TAG = "HomeFragment"
    private val viewModel: HomeViewModel by viewModels()

    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        FormAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.formData.collect { apiResponse ->
                    when (apiResponse) {
                        is ApiResponse.Loading -> {
                            // Handle loading state
                            Log.d(TAG, "onCreate: loading")
                            withContext(Dispatchers.Main){
                                binding.progressBar.visibility = View.VISIBLE
                            }
                        }

                        is ApiResponse.Success -> {
                            Log.d(
                                TAG,
                                "onCreate success: ${apiResponse.data.result.currentSection.questions}"
                            )
                            withContext(Dispatchers.Main) {
                                binding.progressBar.visibility = View.GONE
                                setUpRecyclerView(apiResponse.data.result.currentSection.questions.map {
                                    it.question
                                })
                            }
                        }

                        is ApiResponse.Failure -> {
                            // Handle failure state
                            Log.d(TAG, "onCreate: failure ${apiResponse.error}")
                            binding.progressBar.visibility = View.GONE
                            binding.formRecyclerView.visibility = View.GONE
                            binding.emptyView.visibility = View.VISIBLE
                        }

                        else -> {
                            // Handle empty state
                            Log.d(TAG, "onCreate: empty")
                        }
                    }

                }
            }
        }

    }

    private fun setUpRecyclerView(questions: List<QuestionX>) {
        adapter.setFormData(questions)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        initListeners()
    }

    private fun initListeners() {
        FormAdapter.spinnerListener = object : SpinnerListener {
            override fun onItemSelected(value: String, objectId: Int, position: Int) {
                Log.d(TAG, "onItemSelected: $value")
                val itemsToAdd = mutableListOf<QuestionX>()
                var isAddedRecently = false
                when (value) {
                    "Yes" -> {
                        // Case Yes (option selected is Yes)
                        adapter.formList.forEach { item ->
                            if (item.contentObject.id == objectId) {
                                if (item.contentObject.options[0].haschild) {
                                    Log.d(
                                        TAG,
                                        "onItemSelected: ${item.contentObject.options[0].childs[0].question}"
                                    )
                                    itemsToAdd.add(item.contentObject.options[0].childs[0].question)
                                    isAddedRecently = true  // Set flag to true
                                }
                            }
                        }

                        if (isAddedRecently) {
                            // if form list already contains child then do not add again
                            if (adapter.formList.contains(itemsToAdd[0])) {
                                return
                            }
                            adapter.formList.addAll(position + 1, itemsToAdd)
                            adapter.notifyItemRangeInserted(position + 1, itemsToAdd.size)
                            isAddedRecently = false  // Reset flag
                        }

                    }

                    "No" -> {
                        // Case No (option selected is No)
                        // remove same child from list
                        if (position + 1 < adapter.formList.size && adapter.formList[position + 1].contentType.equals("api | small text question", true)) {
                            adapter.formList.removeAt(position + 1)
                            adapter.notifyItemRemoved(position + 1)
                        }
                    }

                    else -> {
                        //  other options (option selected is Any other type)
                        /*
                            for our case here we are getting empty child when option is ANY OTHER TYPE
                            so we are not adding anything
                            but if changes in future we can add here
                         */
                    }
                }
            }

            override fun onNothingSelected() {
                Log.d(TAG, "onNothingSelected: ")
            }
        }
    }

    private fun init() {
        viewModel.getForm()
        binding.formRecyclerView.adapter = adapter
    }

}