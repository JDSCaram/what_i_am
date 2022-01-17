package br.com.jdscaram.whatiam.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.jdscaram.whatiam.config.FirebaseService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val firebaseService: FirebaseService
) : ViewModel() {

    private val isSuccessful: MutableLiveData<Boolean> = MutableLiveData()

    init {
        viewModelScope.launch {
            firebaseService.init { isSuccessful ->
                this@MainActivityViewModel.isSuccessful.postValue(isSuccessful)
            }
        }
    }

    fun isSuccessRemoteConfig() = isSuccessful
}
