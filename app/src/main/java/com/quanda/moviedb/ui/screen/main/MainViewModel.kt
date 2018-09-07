package com.quanda.moviedb.ui.screen.main

import com.quanda.moviedb.ui.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
) : BaseViewModel() {

    /**
     * test rx java2
     */
    /*
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
    */
}