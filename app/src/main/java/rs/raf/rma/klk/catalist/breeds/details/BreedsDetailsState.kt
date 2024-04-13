package rs.raf.rma.klk.catalist.breeds.details

import rs.raf.rma.klk.catalist.domain.BreedData
import rs.raf.rma.klk.catalist.repository.BreedsRepository

data class BreedsDetailsState (
    val breedId: String,
    val delimiter: String = BreedsRepository.DELIMITER,
    val fetching: Boolean = false,
    val breed: BreedData? = null,
    val error: ListError? = null,
    val imageUrl: String? = null
) {
    sealed class ListError {
        data class BreedFetchingFailed(val cause: Throwable? = null) : ListError()
    }
}