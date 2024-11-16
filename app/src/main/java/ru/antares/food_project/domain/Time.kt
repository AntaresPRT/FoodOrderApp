package ru.antares.food_project.domain

import com.google.firebase.database.PropertyName

data class Time(
    @PropertyName("Id") var id: Int = 0,
    @PropertyName("Value") var value: String = ""
) {
    override fun toString(): String {
        return value
    }
}
