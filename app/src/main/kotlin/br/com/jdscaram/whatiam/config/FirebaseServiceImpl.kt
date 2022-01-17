package br.com.jdscaram.whatiam.config

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import javax.inject.Inject

class FirebaseServiceImpl @Inject constructor(
    private val firebaseRemoteConfig: FirebaseRemoteConfig
) : FirebaseService {
    override suspend fun init(block: (Boolean) -> Unit) {
        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                block.invoke(true)
            } else {
                block.invoke(false)
            }
        }
    }
}
