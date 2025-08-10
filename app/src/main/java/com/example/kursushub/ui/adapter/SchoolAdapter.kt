package com.example.kursushub.ui.adapter

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kursushub.R
import com.example.kursushub.data.model.School
import com.example.kursushub.data.repository.AppliedSchoolRepository

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
        private val btnSeeDetails: Button = itemView.findViewById(R.id.btnSeeDetails)

        fun bind(school: School) {
            schoolName.text = school.namaSekolah
            schoolDetails.text = "${school.jenjang} - ${school.kota}, ${school.provinsi}"

            btnSeeDetails.setOnClickListener {
                val context = itemView.context
                val dialog = Dialog(context)
                dialog.setContentView(R.layout.dialog_school_details)

                val dialogSchoolName: TextView = dialog.findViewById(R.id.tvDialogSchoolName)
                val dialogSchoolLevel: TextView = dialog.findViewById(R.id.tvDialogSchoolLevel)
                val dialogSchoolCity: TextView = dialog.findViewById(R.id.tvDialogSchoolCity)
                val dialogSchoolProvince: TextView = dialog.findViewById(R.id.tvDialogSchoolProvince)
                val dialogSchoolStatus: TextView = dialog.findViewById(R.id.tvDialogSchoolStatus)
                val btnApply: Button = dialog.findViewById(R.id.btnApply)

                dialogSchoolName.text = school.namaSekolah
                dialogSchoolLevel.text = "Jenjang: ${school.jenjang}"
                dialogSchoolCity.text = "Kota: ${school.kota}"
                dialogSchoolProvince.text = "Provinsi: ${school.provinsi}"

                // Cek jika sekolah memiliki status (berarti dari halaman status)
                if (school.status != null) {
                    dialogSchoolStatus.text = "Status: ${school.status}"
                    dialogSchoolStatus.visibility = View.VISIBLE
                    btnApply.visibility = View.GONE
                } else {
                    dialogSchoolStatus.visibility = View.GONE
                    btnApply.visibility = View.VISIBLE
                }

                btnApply.setOnClickListener {
                    school.status = "Pending" // Set status saat melamar
                    AppliedSchoolRepository.getInstance().addAppliedSchool(school)
                    Toast.makeText(context, "Berhasil melamar ke ${school.namaSekolah}", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }

                dialog.show()
            }
        }
    }
}