package com.quanda.moviedb.ui.screen.main.moviedetail

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.view.View
import com.quanda.moviedb.R
import com.quanda.moviedb.data.constants.BundleConstants
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.databinding.ActivityMovieDetailBinding
import com.quanda.moviedb.ui.base.activity.BaseDataLoadActivity
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlin.system.measureTimeMillis

class MovieDetailActivity : BaseDataLoadActivity<ActivityMovieDetailBinding, MovieDetailViewModel>(), MovieDetailNavigator {

    override fun getLayoutId() = R.layout.activity_movie_detail

    override fun initViewModel(): MovieDetailViewModel {
        return ViewModelProviders.of(this,
                MovieDetailViewModel.CustomFactory(application, this)).get(
                MovieDetailViewModel::class.java)
    }

    override fun initData() {
        super.initData()
        binding.apply {
            viewModel = this@MovieDetailActivity.viewModel
            favoriteListener = View.OnClickListener { this@MovieDetailActivity.viewModel.favoriteMovie() }
        }

        intent.extras?.apply {
            getParcelable<Movie>(BundleConstants.MOVIE)?.apply {
                viewModel.updateNewMovie(this)
            }
        }
    }

    override fun onBackPressed() {
        if (viewModel.favoriteChanged.value == true) {
            setResult(Activity.RESULT_OK)
        }
        super.onBackPressed()
    }

    // demo kotlin coroutine

    suspend fun doSomethingUsefulOne(): Int {
        delay(1000L) // pretend we are doing something useful here
        return 13
    }

    suspend fun doSomethingUsefulTwo(): Int {
        delay(1000L) // pretend we are doing something useful here, too
        return 29
    }

    fun sequential() {
        launch {
            val time = measureTimeMillis {
                val one = doSomethingUsefulOne()
                val two = doSomethingUsefulTwo()
                println("The answer is ${one + two}")
            }
            println("Completed in $time ms")
//            The answer is 42
//            Completed in 2017 ms
        }
    }

    fun concurrent() {
        launch {
            val time = measureTimeMillis {
                val one = async { doSomethingUsefulOne() }
                val two = async { doSomethingUsefulTwo() }
                println("The answer is ${one.await() + two.await()}")
            }
            println("Completed in $time ms")
//            The answer is 42
//            Completed in 1017 ms
        }
    }

    fun setup() {
        val job = launch(UI) {
            // launch coroutine in UI context
            for (i in 10 downTo 1) { // countdown from 10 to 1
                println("Countdown $i ...") // update text
                delay(1000) // wait half a second
            }
            println("Done!")
        }
        View.OnClickListener { job.cancel() } // cancel coroutine on click
    }
}