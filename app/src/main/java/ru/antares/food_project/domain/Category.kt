package ru.antares.food_project.domain

import com.google.firebase.database.PropertyName
import java.io.Serializable

data class Category(
    @PropertyName("Id") var id: Int = 0,
    @PropertyName("ImagePath") var imagePath: String = "",
    @PropertyName("Name") var name: String = ""
) : Serializable
