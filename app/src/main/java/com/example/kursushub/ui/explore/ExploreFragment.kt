package com.example.kursushub.ui.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.kursushub.R
import com.google.android.material.card.MaterialCardView

class ExploreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardTechnology: MaterialCardView = view.findViewById(R.id.cardTechnology)
        val cardBusiness: MaterialCardView = view.findViewById(R.id.cardBusiness)
        val cardArts: MaterialCardView = view.findViewById(R.id.cardArts)

        cardTechnology.setOnClickListener {
            Toast.makeText(context, "Working on progress!", Toast.LENGTH_SHORT).show()
        }

        cardBusiness.setOnClickListener {
            Toast.makeText(context, "Working on progress!", Toast.LENGTH_SHORT).show()
        }

        cardArts.setOnClickListener {
            Toast.makeText(context, "Working on progress!", Toast.LENGTH_SHORT).show()
        }
    }
}