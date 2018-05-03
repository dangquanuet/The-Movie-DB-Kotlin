package com.quanda.moviedb.data.repository.impl

import com.quanda.moviedb.data.remote.response.GetMovieListResponse
import com.quanda.moviedb.data.repository.IMovieRepository
import com.quanda.moviedb.data.remote.ApiService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(val apiService: ApiService) : IMovieRepository {

    override fun getMovieList(hashMap: HashMap<String, String>): Single<GetMovieListResponse> {
        return apiService.getMovieList(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}