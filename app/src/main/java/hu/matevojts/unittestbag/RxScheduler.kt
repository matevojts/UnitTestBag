package hu.matevojts.unittestbag

import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers

fun <T> Maybe<T>.observeOnMainThread(): Maybe<T> = this.observeOn(AndroidSchedulers.mainThread())
