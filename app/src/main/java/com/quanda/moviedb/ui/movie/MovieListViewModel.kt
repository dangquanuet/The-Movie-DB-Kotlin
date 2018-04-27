package com.quanda.moviedb.ui.movie

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.util.Log
import com.quanda.moviedb.MainApplication
import com.quanda.moviedb.base.viewmodel.BaseDataLoadMoreRefreshViewModel
import com.quanda.moviedb.constants.ApiParam
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.source.UserRepository
import com.quanda.moviedb.data.source.remote.response.GetMovieListResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MovieListViewModel(application: Application,
        val movieListNavigator: MovieListNavigator) : BaseDataLoadMoreRefreshViewModel<Movie>(
        application) {

    class CustomFactory(val application: Application,
            val movieListNavigator: MovieListNavigator) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MovieListViewModel(application, movieListNavigator) as T
        }
    }

    @Inject
    lateinit var userRepository: UserRepository

    init {
        MainApplication.appComponent.inject(this)
    }

    override fun loadData(page: Int) {
        val hashMap = HashMap<String, String>()
        hashMap.put(ApiParam.PAGE, page.toString())

        userRepository.getMovieList(
                hashMap).subscribe(object : DisposableSingleObserver<GetMovieListResponse>() {
            override fun onSuccess(response: GetMovieListResponse) {
                currentPage = page
                if (currentPage == 1) listItem.clear()
                if (isRefreshing.value == true) resetLoadMore()
                listItem.addAll(response.results)
                onLoadSuccess(response)
            }

            override fun onError(e: Throwable) {
                onLoadFail(e)
            }
        });
    }

    fun demoRxJava() {
        Observable.just(1, 2, 3, 4, 5, 6)
                .doOnNext { Log.e("RXJAVA", "Emitting $it") }
                .map { it -> it * 3 }
                .filter { it -> it % 2 == 0 }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { Log.e("RXJAVA", "Consuming $it") }

        Observable.create<String> {

        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                }

        Observable.concat(Observable.create<String> {
            it.onNext("mon")
            Thread.sleep(200)
            it.onNext("tue")
            Thread.sleep(400)
            it.onNext("wed")
        }, Observable.just(2, 3, 4).doOnNext {
            Thread.sleep(100)
        })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.e("==> ", it.toString())
                }


        val observer1 = Observable.fromArray(1, 2, 3, 4, 5).doOnNext { Thread.sleep(500) }
        val observer2 = Observable.fromArray(6, 7, 8, 9, 10).doOnNext { Thread.sleep(400) }
        Observable.merge<Int>(observer1, observer2)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.e("==> ", it.toString())
                }

        Observable.interval(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.e("==> ", it.toString())
                }

        Observable.create<Long> {
            it.onNext(2)
            it.onNext(5)
        }.doAfterNext { Thread.sleep(3000) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).doOnNext {
                    Log.e("==> ", it.toString())
                }
                .observeOn(Schedulers.computation()).map { it ->
                    it * it
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.e("==> ", it.toString())
                }

        Observable.defer {
            Observable.create<Long> {
                it.onNext(2)
                it.onNext(5)
            }
        }.doAfterNext { Thread.sleep(3000) }
                .flatMap {
                    Observable.just(it)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map { it -> it * it }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { Log.e("==> ", it.toString()) }
    }
}