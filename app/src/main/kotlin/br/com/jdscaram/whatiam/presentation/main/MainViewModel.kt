package br.com.jdscaram.whatiam.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.jdscaram.whatiam.R
import br.com.jdscaram.whatiam.config.RemoteConfig
import br.com.jdscaram.whatiam.domain.Gender
import br.com.jdscaram.whatiam.presentation.main.model.GenderUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val remoteConfig: RemoteConfig
) : ViewModel() {

    val countDownTime = MutableLiveData<Long>()
    val gender = MutableLiveData<GenderUiModel>()

    init {
        viewModelScope.launch {
            val revelationDate = remoteConfig.getRevelationDate()
            countDownTime.postValue(revelationDate)
        }
    }

    fun onCountdownIsOver() {
        val uiModel = when (remoteConfig.getGender()) {
            Gender.FEMALE -> GenderUiModel(
                titleRes = R.string.what_is_girl,
                descriptionRes = R.string.is_girl,
                color = R.color.girl_color
            )
            Gender.MALE -> GenderUiModel(R.string.what_is_boy, R.string.is_boy, R.color.boy_color)
            else -> GenderUiModel(R.string.what_is_error, R.string.no_binary, R.color.white)
        }
        gender.postValue(uiModel)
    }
}
