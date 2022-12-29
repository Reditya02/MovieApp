package com.example.movieapp.data.remote

import com.example.helper.Const.API_KEY
import com.example.helper.MovieListFilter
import com.example.helper.MovieListFilter.*
import com.example.movieapp.data.remote.retrofit.ApiService

class MovieRepository(private val apiService: ApiService) {

    fun getListMovie(filter: MovieListFilter) = when (filter) {
        POPULAR -> apiService.getPopularMovie(API_KEY)
        TOP_RATED -> apiService.getTopRatedMovie(API_KEY)
        NOW_PLAYING -> apiService.getNowPlayingMovie(API_KEY)
        UPCOMING -> apiService.getUpcomingMovie(API_KEY)
    }
}