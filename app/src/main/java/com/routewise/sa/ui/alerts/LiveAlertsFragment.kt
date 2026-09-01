package com.routewise.sa.ui.alerts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.routewise.sa.R
import com.routewise.sa.databinding.FragmentLiveAlertsBinding

class LiveAlertsFragment : Fragment() {

    private var _binding: FragmentLiveAlertsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLiveAlertsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup Province Spinner
        val provinces = arrayOf(
            "All Provinces", "Gauteng", "Western Cape", "KwaZulu-Natal",
            "Eastern Cape", "Free State", "Limpopo", "Mpumalanga", "North West", "Northern Cape"
        )
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, provinces)
        binding.spinnerProvinceFilter.adapter = spinnerAdapter

        binding.rvIncidents.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
