package com.example.moviedb.ui.screen.moviedetail

import android.os.Bundle
import com.example.moviedb.R
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.FragmentMovieDetailBinding
import com.example.moviedb.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import org.koin.androidx.viewmodel.ext.viewModel

class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding, MovieDetailViewModel>() {

    companion object {
        const val MOVIE = "MOVIE"
        const val TAG = "MovieDetailFragment"

        fun newInstance(movie: Movie) = MovieDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(MOVIE, movie)
            }
        }
    }

    override val layoutId: Int = R.layout.fragment_movie_detail

    override val viewModel: MovieDetailViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        button_favorite.setOnClickListener {
            viewModel.favoriteMovie()
        }
        arguments?.apply {
            getParcelable<Movie>(MOVIE)?.apply {
                viewModel.movie.value = this
            }
        }
    }

    /**
     * demo kotlin coroutine
     */

    /*
    suspend fun doSomethingUsefulOne(): Int {
        delay(1000L) // pretend we are doing something useful here
        return 13
    }

    suspend fun doSomethingUsefulTwo(): Int {
        delay(1000L) // pretend we are doing something useful here, too
        return 29
    }

    fun sequential() {
        async {
            val time = measureTimeMillis {
                val one = doSomethingUsefulOne()
                val two = doSomethingUsefulTwo()
                logError("The answer is ${one + two}")
            }
            logError("Completed in $time ms")
//            The answer is 42
//            Completed in 2017 ms
        }
    }

    fun concurrent() {
        async {
            val time = measureTimeMillis {
                val one = async { doSomethingUsefulOne() }
                val two = async { doSomethingUsefulTwo() }
                logError("The answer is ${one.await() + two.await()}")
            }
            logError("Completed in $time ms")
//            The answer is 42
//            Completed in 1017 ms
        }
    }

    fun setup() {
        val job = async(UI) {
            // launch coroutine in UI context
            for (i in 10 downTo 1) { // countdown from 10 to 1
                logError("Countdown $i ...") // update text
                delay(1000) // wait half a second
            }
            logError("Done!")
        }
        View.OnClickListener { job.cancel() } // cancel coroutine on click
    }

    fun createException() {
        throw Exception("Loi roi fix di")
    }

    //    sample 1,2,3 are execute sequentially
    fun sample1() {
        async(UI) {
            withContext(CommonPool) {
                delay(1000)
                logError("withContext")
            }
            logError("async")
        }
    }

    fun sample1b() {
        launch(UI) {
            createException()
        }
    }

    fun sample1c() {
        launch {
            createException()
        }
    }

    fun sample1d() {
        async(UI) {
            createException()
        }
    }

    fun sample1e() {
        async {
            createException()
        }
    }

    fun sample1f() {
        launch {
            try {
                createException()
            } catch (e: Exception) {
                logError(e.toString())
            }
        }
    }

    // when async has exception
    // if invoke await() -> need to try catch
    // if not invoke await() exception will be kept in Defered
    fun sample1g() {
        async {
            val job = async {
                try {
                    logError("truoc loi")
                    createException()
                    logError("sau loi")
                    "123"
                } catch (e: Exception) {
                    "456"
                }
            }
            logError(job.await())
        }
    }

    val exceptionHandler: CoroutineContext = CoroutineExceptionHandler { _, throwable ->
        logError(throwable.message ?: "")
    }

    val parentJob = Job()

    fun sample1h() {
        launch(CommonPool + parentJob + exceptionHandler) {
            createException()
        }
    }

    @Override
    override fun onDestroy() {
        super.onDestroy()
        parentJob.cancel()
    }

    fun sample1i() {
        val parentJob = Job()
        async {
            val job = async(parent = parentJob) {
                for (i in 10 downTo 1) { // countdown from 10 to 1
                    logError("Countdown $i ...") // update text
                    delay(1000) // wait half a second
                }
                logError("Done!")
            }
            val job2 = async {
                delay(3000)
                parentJob.cancel()
            }
            ""
        }
    }

    //    sample 1,2,3 are execute sequentially
    fun sample2() {
        async(UI) {
            async(CommonPool) {
                delay(1000)
                logError("withContext")
            }.await()
            logError("async")
        }
    }

    //    sample 1,2,3 are execute sequentially
    fun sample3() {
        async(UI) {
            withContext(CommonPool) {
                delay(1000)
                logError("withContext")
            }
            withContext(CommonPool) {
                delay(1000)
                logError("withContext2")
            }
            logError("async")
        }
    }

    fun sample4() {
        async(UI) {
            logError("start UI")
            async(CommonPool) {
                logError("start withContext 1")
                delay(1000)
                logError("end withContext 1")
            }
            async(CommonPool) {
                logError("start withContext 2")
                delay(1000)
                logError("end withContext 2")
            }
            logError("end UI")
        }
    }

    fun sample5() {
        launch(UI) {
            println(async(CommonPool) {
                delay(2000)
                "sau 2 giay async"
            }.await())
        }
    }

    fun sample5b() {
        launch(UI) {
            println(withContext(CommonPool) {
                delay(2000)
                "sau 2 giay withContext"
            })
        }
    }

    // lifecycle aware job

    val job: AndroidJob = AndroidJob(
            lifecycle)

    fun sampleLoadData() = async(parent = job) {

    }

    class AndroidJob(lifecycle: Lifecycle) : Job by Job(), LifecycleObserver {
        init {
            lifecycle.addObserver(this)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun destroy() = cancel()
    }
    */
}