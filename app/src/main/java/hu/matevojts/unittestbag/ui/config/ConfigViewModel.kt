package hu.matevojts.unittestbag.ui.config

import androidx.databinding.BaseObservable
import hu.matevojts.unittestbag.datasource.BagDataSource
import io.reactivex.disposables.CompositeDisposable

class ConfigViewModel(private val bagDataSource: BagDataSource) : BaseObservable() {

    private var foregroundDisposables = CompositeDisposable()

    fun onViewResumed() {
        foregroundDisposables = CompositeDisposable()
    }

    fun onViewPaused() {
        foregroundDisposables.clear()
    }
}
