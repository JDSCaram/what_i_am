package br.com.jdscaram.whatiam.domain

interface MainRepository {
    fun getRevealDateTime(): Long
    fun getGender(): Gender
    fun setGenderAndDate(gender: Gender, time: Long)
    fun checkIfExistData(): Boolean
}