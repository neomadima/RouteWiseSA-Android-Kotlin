package com.routewise.sa.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routewise.sa.data.model.IncidentEntity
import com.routewise.sa.data.repository.CalculatedRoute
import com.routewise.sa.data.repository.IncidentRepository
import com.routewise.sa.data.repository.RouteRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MapViewModel(
    private val routeRepository: RouteRepository,
    private val incidentRepository: IncidentRepository
) : ViewModel() {

    private val _activeRoute = MutableLiveData<CalculatedRoute?>()
    val activeRoute: LiveData<CalculatedRoute?> = _activeRoute

    private val _currentStepIndex = MutableLiveData<Int>(0)
    val currentStepIndex: LiveData<Int> = _currentStepIndex

    private val _isVoiceMuted = MutableLiveData<Boolean>(false)
    val isVoiceMuted: LiveData<Boolean> = _isVoiceMuted

    private val _incidents = MutableLiveData<List<IncidentEntity>>()
    val incidents: LiveData<List<IncidentEntity>> = _incidents

    init {
        loadIncidents()
    }

    private fun loadIncidents() {
        viewModelScope.launch {
            incidentRepository.getIncidents(null).collectLatest {
                _incidents.postValue(it)
            }
        }
    }

    fun setCalculatedRoute(route: CalculatedRoute) {
        _activeRoute.value = route
        _currentStepIndex.value = 0
    }

    fun nextStep() {
        val current = _currentStepIndex.value ?: 0
        val max = (_activeRoute.value?.steps?.size ?: 1) - 1
        if (current < max) {
            _currentStepIndex.value = current + 1
        }
    }

    fun prevStep() {
        val current = _currentStepIndex.value ?: 0
        if (current > 0) {
            _currentStepIndex.value = current - 1
        }
    }

    fun toggleVoice() {
        _isVoiceMuted.value = !(_isVoiceMuted.value ?: false)
    }

    fun clearRoute() {
        _activeRoute.value = null
        _currentStepIndex.value = 0
    }
}
