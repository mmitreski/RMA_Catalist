package rs.raf.rma.klk.catalist.domain.api

import retrofit2.http.GET
import retrofit2.http.Path
import rs.raf.rma.klk.catalist.domain.BreedData
import rs.raf.rma.klk.catalist.domain.ImageData

interface BreedsApi {
    @GET("breeds")
    suspend fun getAllBreeds(): List<BreedData>

    @GET("breeds/{id}")
    suspend fun getBreed(@Path("id") breedId: String): BreedData

    @GET("images/{id}")
    suspend fun getImage(@Path("id") imageId: String): ImageData
}