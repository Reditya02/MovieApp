package com.example.movieapp.ui.movielist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.helper.MovieListFilter
import com.example.movieapp.data.MovieRepository
import com.example.movieapp.data.remote.retrofit.ApiClient
import com.example.movieapp.data.remote.model.MovieResponse
import com.example.movieapp.data.remote.model.MoviesResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListViewModel : ViewModel() {
    private val repository: MovieRepository = MovieRepository(ApiClient.getApiService())

    private var _listMovie = MutableLiveData<List<MovieResponse>>()
    val listMovie: LiveData<List<MovieResponse>> = _listMovie

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

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
                Log.d("Reditya enqueue", Gson().toJson(list))
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })

        Log.d("Reditya ViewModel", Gson().toJson(listMovie))
    }
}