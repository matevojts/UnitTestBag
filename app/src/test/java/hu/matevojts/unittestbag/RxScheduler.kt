package hu.matevojts.unittestbag

import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers

fun <T> Maybe<T>.observeOnMainThread(): Maybe<T> = this.observeOn(Schedulers.trampoline())
