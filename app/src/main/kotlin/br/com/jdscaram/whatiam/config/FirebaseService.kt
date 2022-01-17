package br.com.jdscaram.whatiam.config

interface FirebaseService {
    suspend fun init(block: (Boolean) -> Unit)
}