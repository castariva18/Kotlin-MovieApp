package com.miro.practicekotlin.network

import com.miro.practicekotlin.model.MovieDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>
}