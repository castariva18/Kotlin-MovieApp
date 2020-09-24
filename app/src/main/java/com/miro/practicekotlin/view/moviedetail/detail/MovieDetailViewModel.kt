package com.miro.practicekotlin.view.moviedetail.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.miro.practicekotlin.model.MovieDetails
import com.miro.practicekotlin.network.NetworkState
import com.miro.practicekotlin.view.moviedetail.detail.MovieDetailsRepository
import io.reactivex.disposables.CompositeDisposable

class MovieDetailViewModel(private val movieRepository: MovieDetailsRepository, movieId: Int) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movieDetails: LiveData<MovieDetails> by lazy {
        movieRepository.fetchSingleMovieDetails(compositeDisposable, movieId)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}