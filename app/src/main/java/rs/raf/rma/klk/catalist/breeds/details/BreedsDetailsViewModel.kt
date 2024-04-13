package rs.raf.rma.klk.catalist.breeds.details

import android.util.Log
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

class BreedsDetailsViewModel(
    private val breedId: String,
    private val repository: BreedsRepository = BreedsRepository
) : ViewModel() {
    private val _state = MutableStateFlow(BreedsDetailsState(breedId = breedId))
    val state = _state.asStateFlow()
    private fun setState(reducer: BreedsDetailsState.() -> BreedsDetailsState) = _state.getAndUpdate(reducer)

    init {
        fetchBreedAndImage()
    }

    private fun fetchBreedAndImage() {
        viewModelScope.launch {
            setState { copy(fetching = true) }
            try {
                val breed = withContext(Dispatchers.IO) {
                    repository.fetchBreed(breedId)
                }
                val imageUrl = withContext(Dispatchers.IO) {
                    if(breed.imageId != null) {
                        repository.fetchBreedImage(breed.imageId)
                    } else null
                }
                Log.d("TAG", "here")
                setState { copy(breed = breed, imageUrl = imageUrl) }
            } catch (error: IOException) {
                setState {
                    copy(error = BreedsDetailsState.ListError.BreedFetchingFailed(cause = error))
                }
            } finally {
                setState { copy(fetching = false) }
            }
        }
    }

}