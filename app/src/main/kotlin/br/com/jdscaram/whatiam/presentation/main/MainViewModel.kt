package br.com.jdscaram.whatiam.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.jdscaram.whatiam.R
import br.com.jdscaram.whatiam.domain.Gender
import br.com.jdscaram.whatiam.domain.MainRepository
import br.com.jdscaram.whatiam.presentation.main.model.GenderUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    val countDownTime = MutableLiveData<Long>()
    val gender = MutableLiveData<GenderUiModel>()

    init {
        viewModelScope.launch {
            val revelationDate = mainRepository.getRevealDateTime()
            countDownTime.postValue(revelationDate)
        }
    }

    fun onCountdownIsOver() {
        val uiModel = when (mainRepository.getGender()) {
            Gender.FEMALE -> GenderUiModel(R.string.is_girl, R.color.girl_color)
            Gender.MALE -> GenderUiModel(R.string.is_boy, R.color.boy_color)
            else -> GenderUiModel(R.string.no_binary, R.color.white)
        }
        gender.postValue(uiModel)
    }
}
