package ru.antares.food_project.domain

import com.google.firebase.database.PropertyName

data class Location(
    @PropertyName("Id") var id: Int = 0,
    @PropertyName("loc") var loc: String = ""
) {
    override fun toString(): String {
        return loc
    }
}
