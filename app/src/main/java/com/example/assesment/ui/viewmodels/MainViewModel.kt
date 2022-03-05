package com.example.assesment.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assesment.api.model.PostsResponseItem
import com.example.assesment.api.model.UserPost
import com.example.assesment.api.model.UsersResponseItem
import com.example.assesment.api.repository.Repository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class MainViewModel(private  val repository: Repository):ViewModel() {

    private val userData :MutableLiveData<List<UserPost>> = MutableLiveData()
    val _userLiveData  : LiveData<List<UserPost>>
        get() = userData

    fun fetchDetails() = viewModelScope.launch {
        val _fetchUser = async { repository.userDetails() }
        val _fetchPosts = async { repository.postDetails() }
        val _listDetails = listOf(_fetchUser, _fetchPosts)
        _listDetails.awaitAll()
        val detailsList: ArrayList<UserPost> = ArrayList()

        (_listDetails?.get(0)
            ?.getCompleted() as? List<UsersResponseItem>?)?.forEach { userResponse ->
            val _userPost = UserPost()
            _userPost.company = userResponse.company.name
            val _searchId = (_listDetails?.get(1)
                ?.getCompleted() as? List<PostsResponseItem>?)?.first { it.id == userResponse.id }
            _searchId?.let {
                _userPost.title = it.title
                _userPost.body = it.body
                detailsList.add(_userPost)
            }
        }
        userData.postValue(detailsList)


    }

}