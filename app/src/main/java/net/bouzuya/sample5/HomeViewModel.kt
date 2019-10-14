package net.bouzuya.sample5

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class HomeViewModel(private val _bookmarkRepository: BookmarkRepository) : ViewModel() {

    private val _editBookmarkEvent = MutableLiveData<Bookmark>()
    val editBookmarkEvent: LiveData<Bookmark> = _editBookmarkEvent

    private val _goToTagEvent = MutableLiveData<Event<Unit>>()
    val goToTagEvent: LiveData<Event<Unit>> = _goToTagEvent

    private val _bookmarkCount = MutableLiveData<Int>()
    val bookmarkCount: LiveData<String> = Transformations.map(_bookmarkCount) { it.toString() }

    private val _bookmarkList = MutableLiveData<List<Bookmark>>()
    val bookmarkList: LiveData<List<Bookmark>> = _bookmarkList

    init {
        viewModelScope.launch {
            refreshList()
        }
    }

    fun deleteAll() = viewModelScope.launch {
        _bookmarkRepository.deleteAll()

        refreshList()
    }

    fun edit(bookmark: Bookmark) {
        _editBookmarkEvent.value = bookmark
    }

    fun goToTag() {
        _goToTagEvent.value = Event(Unit)
    }

    fun refresh() = viewModelScope.launch {
        refreshList()
    }

    private suspend fun refreshList() {
        _bookmarkList.value = _bookmarkRepository.findAll()
        _bookmarkCount.value = _bookmarkList.value?.size ?: 0
    }
}
