package rs.raf.rma.klk.catalist.breeds.list

import androidx.compose.ui.focus.FocusRequester
import rs.raf.rma.klk.catalist.domain.BreedData
import rs.raf.rma.klk.catalist.repository.BreedsRepository

data class BreedsListState (
    val delimiter: String = BreedsRepository.DELIMITER,
    val fetching: Boolean = false,
    val breeds: List<BreedData> = emptyList(),
    val focusRequester: FocusRequester = FocusRequester(),
    val searchText: String = "",
    val error: ListError? = null,
) {
    sealed class ListError {
        data class BreedListFetchingFailed(val cause: Throwable? = null) : ListError()
    }
}
