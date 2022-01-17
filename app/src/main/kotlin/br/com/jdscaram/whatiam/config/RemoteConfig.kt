package br.com.jdscaram.whatiam.config

import br.com.jdscaram.whatiam.domain.Gender

interface RemoteConfig {
    fun getRevelationDate(): Long
    fun getGender(): Gender
}