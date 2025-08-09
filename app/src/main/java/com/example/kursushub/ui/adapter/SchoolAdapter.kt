package com.example.kursushub.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kursushub.R
import com.example.kursushub.data.model.School

class SchoolAdapter(private var schools: MutableList<School>) :
    RecyclerView.Adapter<SchoolAdapter.SchoolViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_school, parent, false)
        return SchoolViewHolder(view)
    }

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        val school = schools[position]
        holder.bind(school)
    }

    override fun getItemCount(): Int = schools.size

    fun setData(newSchools: List<School>) {
        schools.clear()
        schools.addAll(newSchools)
        notifyDataSetChanged()
    }

    class SchoolViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val schoolName: TextView = itemView.findViewById(R.id.tvSchoolName)
        private val schoolDetails: TextView = itemView.findViewById(R.id.tvSchoolDetails)

        fun bind(school: School) {
            schoolName.text = school.namaSekolah
            schoolDetails.text = "${school.jenjang} - ${school.kota}, ${school.provinsi}"
        }
    }
}