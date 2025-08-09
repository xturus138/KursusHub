package com.example.kursushub.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kursushub.R
import com.example.kursushub.ui.adapter.SchoolAdapter
import com.example.kursushub.ui.profile.ProfileFragment
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var schoolAdapter: SchoolAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var searchJob: Job? = null
    private var currentJenjangFilter: String? = null

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
        val profileButton = view.findViewById<CardView>(R.id.cardViewProfile)

        setupRecyclerView(rvSchools)
        observeViewModel()
        setupSearch(etSearch, chipGroup)
        setupFilter(chipGroup)

        profileButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProfileFragment())
                .addToBackStack(null)
                .commit()
        }

        if (savedInstanceState == null) {
            chipGroup.check(R.id.chip_all)
            viewModel.loadSchools(currentJenjangFilter, reset = true)
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        schoolAdapter = SchoolAdapter(mutableListOf())
        recyclerView.adapter = schoolAdapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!viewModel.isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= 20) {
                        viewModel.loadSchools(currentJenjangFilter)
                    }
                }
            }
        })
    }

    private fun observeViewModel() {
        viewModel.schools.observe(viewLifecycleOwner) { schools ->
            schoolAdapter.setData(schools ?: emptyList())
        }
        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupFilter(chipGroup: ChipGroup) {
        chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            val checkedId = checkedIds.firstOrNull() ?: R.id.chip_all

            currentJenjangFilter = when (checkedId) {
                R.id.chip_sd -> "SD"
                R.id.chip_smp -> "SMP"
                R.id.chip_sma -> "SMA"
                R.id.chip_smk -> "SMK"
                else -> null
            }
            val searchText = view?.findViewById<EditText>(R.id.etSearch)?.text?.toString() ?: ""
            if (searchText.isEmpty()) {
                viewModel.loadSchools(currentJenjangFilter, reset = true)
            }
        }
    }

    private fun setupSearch(editText: EditText, chipGroup: ChipGroup) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchJob?.cancel()
                searchJob = MainScope().launch {
                    delay(500)
                    val query = s.toString().trim()
                    if (query.length > 2) {
                        viewModel.searchSchools(query)
                    } else if (query.isEmpty()) {
                        viewModel.loadSchools(currentJenjangFilter, reset = true)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}