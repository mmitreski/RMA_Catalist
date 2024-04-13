package rs.raf.rma.klk.catalist.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BreedData(
    @SerialName("id")
    val breedId: String,
    val name: String,
    @SerialName("alt_names")
    val altNames: String? = null,
    val description: String,
    val temperament: String,
    val origin: String,
    val weight: Weight? = null,
    val rare: Int,
    @SerialName("life_span")
    val lifeSpan: String,
    @SerialName("wikipedia_url")
    val wikipediaLink: String? = null,
    @SerialName("reference_image_id")
    val imageId: String? = null,
    val adaptability: Int,
    val grooming: Int,
    val intelligence: Int,
    val vocalisation: Int,
    @SerialName("energy_level")
    val energy: Int
) {
    @Serializable
    data class Weight (
        val imperial: String,
        val metric: String,
    )
}
