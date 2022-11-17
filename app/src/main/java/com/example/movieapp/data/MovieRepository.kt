package com.example.movieapp.data

import com.example.movieapp.helper.Const.API_KEY
import com.example.movieapp.helper.MovieListFilter
import com.example.movieapp.helper.MovieListFilter.*
import com.example.movieapp.data.remote.retrofit.ApiService

class MovieRepository(private val apiService: ApiService) {

    fun getListMovie(filter: MovieListFilter) = when (filter) {
        POPULAR -> apiService.getPopularMovie(API_KEY)
        TOP_RATED -> apiService.getTopRatedMovie(API_KEY)
        NOW_PLAYING -> TODO()
        UPCOMING -> TODO()
    }
}