package net.bouzuya.sample5

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.bouzuya.sample5.data.Bookmark
import net.bouzuya.sample5.data.BookmarkRepository

class BookmarkListViewModel(private val _bookmarkRepository: BookmarkRepository) : ViewModel() {

    private val _editBookmarkEvent = MutableLiveData<Event<Bookmark>>()
    val editBookmarkEvent: LiveData<Event<Bookmark>> = _editBookmarkEvent

    private val _bookmarkList = MutableLiveData<List<Bookmark>>()
    val bookmarkList: LiveData<List<Bookmark>> = _bookmarkList

    private val _createBookmarkEvent = MutableLiveData<Event<Unit>>()
    val createBookmarkEvent: LiveData<Event<Unit>> = _createBookmarkEvent

    init {
        viewModelScope.launch {
            refreshList()
        }
    }

    fun edit(bookmark: Bookmark) {
        _editBookmarkEvent.value = Event(bookmark)
    }

    fun create() {
        _createBookmarkEvent.value = Event(Unit)
    }

    fun refresh() = viewModelScope.launch {
        refreshList()
    }

    private suspend fun refreshList() {
        _bookmarkList.value = _bookmarkRepository.findAll()
    }
}
