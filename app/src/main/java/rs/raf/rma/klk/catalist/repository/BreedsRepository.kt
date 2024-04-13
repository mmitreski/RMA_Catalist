package rs.raf.rma.klk.catalist.repository

import rs.raf.rma.klk.catalist.domain.api.BreedsApi
import rs.raf.rma.klk.catalist.networking.retrofit

object BreedsRepository {
    private val breedsApi: BreedsApi = retrofit.create(BreedsApi::class.java)
    const val DELIMITER = ", "

    suspend fun fetchBreeds() = breedsApi.getAllBreeds()

    suspend fun fetchBreed(id: String) = breedsApi.getBreed(id)

    suspend fun fetchBreedImage(id: String) = breedsApi.getImage(id).url
}