package com.example.kursushub.ui.status

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kursushub.R
import com.example.kursushub.data.repository.AppliedSchoolRepository
import com.example.kursushub.ui.adapter.SchoolAdapter

class StatusFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SchoolAdapter
    private lateinit var appliedSchoolRepository: AppliedSchoolRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_status, container, false)
        recyclerView = view.findViewById(R.id.rvAppliedSchools)
        setupRecyclerView()
        appliedSchoolRepository = AppliedSchoolRepository.getInstance()
        observeAppliedSchools()
        return view
    }

    private fun setupRecyclerView() {
        adapter = SchoolAdapter(mutableListOf())
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun observeAppliedSchools() {
        appliedSchoolRepository.getAppliedSchools().observe(viewLifecycleOwner, Observer { schools ->
            adapter.setData(schools)
        })
    }
}