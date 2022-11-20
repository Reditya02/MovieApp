package com.example.movieapp.data.remote.retrofit

import com.example.movieapp.data.remote.model.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    fun getPopularMovie(
        @Query("api_key") apiKey: String
    ): Call<MoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovie(
        @Query("api_key") apiKey: String
    ): Call<MoviesResponse>

    @GET("movie/now_playing")
    fun getNowPlayingMovie(
        @Query("api_key") apiKey: String
    ): Call<MoviesResponse>

    @GET("movie/popular")
    fun getUpcomingMovie(
        @Query("api_key") apiKey: String
    ): Call<MoviesResponse>
}