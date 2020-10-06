package hu.matevojts.unittestbag.ui.config

import androidx.databinding.BaseObservable
import com.jakewharton.rxrelay2.PublishRelay
import hu.matevojts.unittestbag.datasource.BagDataSource
import hu.matevojts.unittestbag.trigger
import io.reactivex.disposables.CompositeDisposable

class ConfigViewModel(private val bagDataSource: BagDataSource) : BaseObservable() {

    private var foregroundDisposables = CompositeDisposable()
    val openBag = PublishRelay.create<Unit>()

    fun onViewResumed() {
        foregroundDisposables = CompositeDisposable()
    }

    fun onViewPaused() {
        foregroundDisposables.clear()
    }

    fun openBag() = openBag.trigger()
}
