package br.com.jdscaram.whatiam.domain

import android.content.SharedPreferences
import br.com.jdscaram.whatiam.config.RemoteConfig
import javax.inject.Inject

private const val KEY_REVEAL_TIME = "KEY_REVEAL_TIME"
private const val KEY_REVEAL_GENDER = "KEY_REVEAL_GENDER"

class MainRepositoryImpl @Inject constructor(
    private val remoteConfig: RemoteConfig,
    private val sharedPreferences: SharedPreferences
) : MainRepository {

    override fun getRevealDateTime(): Long {
        val revealTime = sharedPreferences.getLong(KEY_REVEAL_TIME, -1)
        return if (revealTime == -1L) {
            remoteConfig.getRevelationDate()
        } else {
            revealTime
        }
    }

    override fun getGender(): Gender {
        val gender = sharedPreferences.getString(KEY_REVEAL_GENDER, null)
        return if (gender == null) {
            remoteConfig.getGender()
        } else {
            Gender.valueOf(gender)
        }
    }

    override fun setGenderAndDate(gender: Gender, time: Long) {
        sharedPreferences.edit().apply {
            putString(KEY_REVEAL_GENDER, remoteConfig.getGender().name)
            putLong(KEY_REVEAL_TIME, remoteConfig.getRevelationDate())
        }.apply()
    }

    override fun checkIfExistData(): Boolean {
        val revealTime = sharedPreferences.getLong(KEY_REVEAL_TIME, -1)
        val gender = sharedPreferences.getString(KEY_REVEAL_GENDER, null)
        return revealTime != -1L && gender != null
    }
}
