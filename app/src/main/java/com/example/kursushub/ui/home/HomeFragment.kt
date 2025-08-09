package com.example.kursushub.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kursushub.R
import com.example.kursushub.ui.adapter.SchoolAdapter
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var schoolAdapter: SchoolAdapter
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        val rvSchools = view.findViewById<RecyclerView>(R.id.rvSchools)
        val etSearch = view.findViewById<EditText>(R.id.etSearch)
        val chipGroup = view.findViewById<ChipGroup>(R.id.chipGroupFilter)

        setupRecyclerView(rvSchools)
        observeViewModel()
        setupSearch(etSearch, chipGroup)
        setupFilter(chipGroup)

        if (savedInstanceState == null) {
            chipGroup.check(R.id.chip_all)
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        schoolAdapter = SchoolAdapter(emptyList())
        recyclerView.adapter = schoolAdapter
    }

    private fun observeViewModel() {
        viewModel.schools.observe(viewLifecycleOwner) { schools ->
            schoolAdapter.updateData(schools ?: emptyList())
        }
        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
        }
    }


    private fun setupFilter(chipGroup: ChipGroup) {
        chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val checkedId = checkedIds.firstOrNull() ?: R.id.chip_all

            val jenjang = when (checkedId) {
                R.id.chip_sd -> "SD"
                R.id.chip_smp -> "SMP"
                R.id.chip_sma -> "SMA"
                R.id.chip_smk -> "SMK"
                else -> null
            }
            view?.findViewById<EditText>(R.id.etSearch)?.text?.clear()
            viewModel.getSchools(jenjang)
        }
    }

    private fun setupSearch(editText: EditText, chipGroup: ChipGroup) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchJob?.cancel()
                searchJob = MainScope().launch {
                    delay(500)
                    val query = s.toString()
                    if (query.length > 2) {
                        chipGroup.clearCheck()
                        viewModel.searchSchools(query)
                    } else if (query.isEmpty()) {
                        chipGroup.check(R.id.chip_all)
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}