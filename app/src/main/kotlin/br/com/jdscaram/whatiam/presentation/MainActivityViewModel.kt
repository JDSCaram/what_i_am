package br.com.jdscaram.whatiam.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.jdscaram.whatiam.config.FirebaseService
import br.com.jdscaram.whatiam.domain.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val firebaseService: FirebaseService,
    private val mainRepository: MainRepository
) : ViewModel() {

    private val isSuccessful: MutableLiveData<Boolean> = MutableLiveData()

    init {
        viewModelScope.launch {
            firebaseService.init { result ->
                if (!result && mainRepository.checkIfExistData()) {
                    isSuccessful.postValue(true)
                } else {
                    isSuccessful.postValue(result)
                }
            }
        }
    }

    fun isSuccessRemoteConfig() = isSuccessful
}
