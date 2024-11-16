package ru.antares.food_project.domain

import com.google.firebase.database.PropertyName
import java.io.Serializable

data class Foods(
    @PropertyName("CategoryId") var categoryId: Int = 0,
    @PropertyName("Description") var description: String = "",
    @PropertyName("BestFood") var bestFood: Boolean = true,
    @PropertyName("Id") var id: Int = 0,
    @PropertyName("LocationId") var locationId: Int = 0,
    @PropertyName("Price") var price: Int = 0,
    @PropertyName("ImagePath") var imagePath: String = "",
    @PropertyName("PriceId") var priceId: Int = 0,
    @PropertyName("Star") var star: Double = 0.0,
    @PropertyName("TimeId") var timeId: Int = 0,
    @PropertyName("Title") var title: String = "",
    @PropertyName("NumberInCart") var numberInCart: Int = 0,
) : Serializable {
    constructor() : this(0,"",true,0,0,0,"",0,0.0,0,"",0)

    override fun toString(): String {
        return title
    }
}
