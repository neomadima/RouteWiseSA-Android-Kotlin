package com.routewise.sa.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.routewise.sa.R
import com.routewise.sa.databinding.FragmentMapBinding

class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    // Sandton City, Johannesburg fallback coordinates
    private val sandtonCenter = Point.fromLngLat(28.0567, -26.1076)
    private var lastUserPoint: Point? = null
    private var isTrackingUser = true

    // Real-time GPS movement listener: tracks user live as they move
    private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener { point ->
        lastUserPoint = point
        if (isTrackingUser) {
            binding.mapView.mapboxMap.setCamera(
                CameraOptions.Builder()
                    .center(point)
                    .zoom(16.0)
                    .build()
            )
        }
    }

    private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener { bearing ->
        if (isTrackingUser) {
            binding.mapView.mapboxMap.setCamera(
                CameraOptions.Builder()
                    .bearing(bearing)
                    .build()
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load Mapbox Navigation Night Style (or Style.DARK)
        binding.mapView.mapboxMap.loadStyle(Style.DARK) { style ->
            setupLocationComponent()
        }

        // Detect user manual gestures to pause auto-follow until recenter
        binding.mapView.gestures.addOnMoveListener(object : com.mapbox.maps.plugin.gestures.OnMoveListener {
            override fun onMoveBegin(detector: com.mapbox.android.gestures.MoveGestureDetector) {
                isTrackingUser = false
            }
            override fun onMove(detector: com.mapbox.android.gestures.MoveGestureDetector): Boolean = false
            override fun onMoveEnd(detector: com.mapbox.android.gestures.MoveGestureDetector) {}
        })

        // Setup Floating Action Buttons
        binding.fabReportIncident.setOnClickListener {
            findNavController().navigate(R.id.action_map_to_report)
        }

        binding.fabAiAssistant.setOnClickListener {
            findNavController().navigate(R.id.action_map_to_chat)
        }

        binding.fabRecenter.setOnClickListener {
            isTrackingUser = true
            val target = lastUserPoint ?: sandtonCenter
            binding.mapView.mapboxMap.setCamera(
                CameraOptions.Builder()
                    .center(target)
                    .zoom(16.0)
                    .build()
            )
        }

        binding.fabTraffic.setOnClickListener {
            // Toggle between Dark Navigation and Streets
            binding.mapView.mapboxMap.loadStyle(Style.MAPBOX_STREETS)
        }
    }

    private fun setupLocationComponent() {
        binding.mapView.location.apply {
            updateSettings {
                enabled = true
                pulsingEnabled = true
            }
            addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
            addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.mapView.location.removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        binding.mapView.location.removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        _binding = null
    }
}
