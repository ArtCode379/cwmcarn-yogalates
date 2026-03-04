package app.skeleton.sporttrackerskeleton.ui.state

sealed class DataUiState<out T> {

    object Initial : DataUiState<Nothing>()
    object Empty : DataUiState<Nothing>()

    data class Data<T : Any>(val data: T) : DataUiState<T>()
}