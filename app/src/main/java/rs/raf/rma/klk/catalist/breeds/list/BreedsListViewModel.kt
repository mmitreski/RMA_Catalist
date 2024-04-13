package rs.raf.rma.klk.catalist.breeds.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rs.raf.rma.klk.catalist.repository.BreedsRepository
import java.io.IOException

class BreedsListViewModel(
    private val repository: BreedsRepository = BreedsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(BreedsListState())
    val state = _state.asStateFlow()
    private fun setState(reducer: BreedsListState.() -> BreedsListState) = _state.getAndUpdate(reducer)

    init {
        fetchBreedDetails()
    }

    private fun fetchBreedDetails() {
        viewModelScope.launch {
            setState { copy(fetching = true) }
            try {
                val breeds = withContext(Dispatchers.IO) {
                    repository.fetchBreeds()
                }
                setState { copy(breeds = breeds) }
            } catch (error: IOException) {
                setState {
                    copy(error = BreedsListState.ListError.BreedListFetchingFailed(cause = error))
                }
            } finally {
                setState { copy(fetching = false) }
            }
        }
    }

    fun onSearchTextChange(text: String) {
        setState { copy(searchText = text) }
    }

    fun onClearFocus() {
        setState { copy(searchText = "") }
    }
}