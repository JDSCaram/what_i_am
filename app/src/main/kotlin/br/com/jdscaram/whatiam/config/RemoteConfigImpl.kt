package br.com.jdscaram.whatiam.config

import br.com.jdscaram.whatiam.domain.Gender
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import java.util.*
import javax.inject.Inject

class RemoteConfigImpl @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig
) : RemoteConfig {
    override fun getRevelationDate(): Long {
        val revelationDate = 1642371285000 // ja acabou
//        val revelationDate = remoteConfig.getLong("revelation_date")
        val currentDateTime = Date().time
        return revelationDate.minus(currentDateTime)
    }

    override fun getGender(): Gender {
        return when {
            remoteConfig.getBoolean("is_girl") -> Gender.FEMALE
            remoteConfig.getBoolean("is_boy") -> Gender.MALE
            else -> Gender.NO_BINARY
        }
    }
}
