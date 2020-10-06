package hu.matevojts.unittestbag.ui.openedbag

import androidx.databinding.BaseObservable
import hu.matevojts.unittestbag.datasource.BagDataSource
import io.reactivex.disposables.CompositeDisposable

class OpenedBagViewModel(private val bagDataSource: BagDataSource) : BaseObservable() {

    private var foregroundDisposables = CompositeDisposable()

    fun onViewResumed() {
        foregroundDisposables = CompositeDisposable()
    }

    fun onViewPaused() {
        foregroundDisposables.clear()
    }
}
