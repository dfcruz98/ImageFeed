package com.example.imagefeed.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagefeed.data.Group
import com.example.imagefeed.data.Photo
import com.example.imagefeed.remote.IPhotosRepository
import com.example.imagefeed.utils.ActionResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val repository: IPhotosRepository
) : ViewModel() {

    private val _loading = MutableLiveData(true)
    private val _refreshing = MutableLiveData(false)
    private val _resultsLiveData = MutableLiveData<List<Group>?>()
    private val _errorLiveData = MutableLiveData<String>()

    val loading: LiveData<Boolean>
        get() = _loading

    val refreshing: LiveData<Boolean>
        get() = _refreshing

    val resultsLiveData: LiveData<List<Group>?>
        get() = _resultsLiveData

    val errorLiveData: LiveData<String>
        get() = _errorLiveData


    init {
        // Start fetching data when instantiated
        _loading.value = true
        fetchData()
    }

    /**
     * Fetch new data when the user swipes
     */
    fun refreshData() {
        _refreshing.value = true
        fetchData()
    }

    /**
     *  Asynchronous fetch new data
     */
    private fun fetchData() {
        viewModelScope.launch {
            // With supervisorScope in case of any coroutine fails,
            // the remaining ones are not canceled
            supervisorScope {
                val call1 = async { repository.getPhotos("cats", 5) }
                val call2 = async { repository.getPhotos("dogs", 7) }
                val call3 = async { repository.getPhotos("birds", 4) }
                val call4 = async { repository.getPhotos("beach", 5) }

                val response1 = call1.await()
                val response2 = call2.await()
                val response3 = call3.await()
                val response4 = call4.await()

                val list = arrayListOf<Group>()

                if (response1 is ActionResult.Success) {
                    list.add(Group("Cats", response1.data))
                } else if (response1 is ActionResult.Error) {
                    _errorLiveData.postValue(response1.message)
                }

                if (response2 is ActionResult.Success) {
                    list.add(Group("Dogs", response2.data))
                } else if (response2 is ActionResult.Error) {
                    _errorLiveData.postValue(response2.message)
                }

                if (response3 is ActionResult.Success) {
                    list.add(Group("Birds", response3.data))
                } else if (response3 is ActionResult.Error) {
                    _errorLiveData.postValue(response3.message)
                }

                if (response4 is ActionResult.Success) {
                    list.add(Group("Beach", response4.data))
                } else if (response4 is ActionResult.Error) {
                    _errorLiveData.postValue(response4.message)
                }

                _loading.value = false
                _refreshing.value = false
                _resultsLiveData.value = list
            }
        }
    }

    fun getGroups(): List<Group> {
        val images = mutableListOf(
            Photo(
                "51332717705", "192094018@N06", "51bbacf5de", "65535", 66, "Jamie",
                public = true,
                friend = false,
                family = false
            ),
            Photo(
                "51332398944", "16854222@N05", "086e84285b", "65535", 66, "Happy Caturday",
                public = true,
                friend = false,
                family = false
            ),
            Photo(
                "51330720432", "182230076@N02", "212436b4c3", "65535", 66, "140-365 Mavro Cat",
                public = true,
                friend = false,
                family = false
            ),
            Photo(
                "51331921404",
                "192094018@N06",
                "67c53f489a",
                "65535",
                66,
                "They Know How To Have Fu",
                public = true,
                friend = false,
                family = false
            ),
            Photo(
                "51331196206",
                "192094018@N06",
                "6d6fe081f0",
                "65535",
                66,
                "They Know How To Have Fu",
                public = true,
                friend = false,
                family = false
            ),
        )

        val group = Group("Cats", images)

        return listOf(group, group, group, group)
    }

}