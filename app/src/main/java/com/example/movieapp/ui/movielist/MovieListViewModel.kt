package com.example.movieapp.ui.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.helper.MovieListFilter
import com.example.movieapp.data.remote.MovieRepository
import com.example.movieapp.data.locale.UserRepository
import com.example.movieapp.data.locale.model.User
import com.example.movieapp.data.remote.retrofit.ApiClient
import com.example.movieapp.data.remote.model.MovieResponse
import com.example.movieapp.data.remote.model.MoviesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val repository: MovieRepository = MovieRepository(ApiClient.getApiService())

    private var _listMovie = MutableLiveData<List<MovieResponse>>()
    val listMovie: LiveData<List<MovieResponse>> = _listMovie

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val user = MutableLiveData<User>()
    fun getUser(email: String): LiveData<User> {
        viewModelScope.launch {
            user.value = userRepository.getUserByEmail(email)
        }
        return user
    }

    fun getListMovie(filter: MovieListFilter) {
        val list = mutableListOf<MovieResponse>()
        _isLoading.value = true

        repository.getListMovie(filter).enqueue(object :
            Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                response.body()?.results?.forEach {
                    list.add(it)
                }
                _listMovie.value = list
                _isLoading.value = false
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }
}