package com.miro.practicekotlin.view.moviedetail.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miro.practicekotlin.R
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.miro.practicekotlin.network.POSTER_BASE_URL
import com.miro.practicekotlin.network.ApiClient
import com.miro.practicekotlin.network.ApiService
import com.miro.practicekotlin.network.NetworkState
import com.miro.practicekotlin.model.MovieDetails
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.movie_list_item.*
import java.util.*


class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var movieRepository: MovieDetailsRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val movieId: Int = intent.getIntExtra("id",1)

        val apiService : ApiService = ApiClient.getClient()
        movieRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE

        })

    }

    fun bindUI( it: MovieDetails){
        tv_title_detail.text = it.title
        tv_tagline.text = it.tagline
        tv_release_date.text = it.releaseDate
        tv_rating.text = it.rating.toString()
        tv_run_time.text = it.runtime.toString() + " minutes"
        tv_overview.text = it.overview

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(img_detail);


    }


    private fun getViewModel(movieId:Int): MovieDetailViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieDetailViewModel(movieRepository,movieId) as T
            }
        })[MovieDetailViewModel::class.java]
    }
}
