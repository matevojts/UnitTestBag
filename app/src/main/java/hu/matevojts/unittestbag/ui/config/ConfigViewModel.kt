package hu.matevojts.unittestbag.ui.config

import androidx.databinding.BaseObservable
import io.reactivex.disposables.CompositeDisposable

class ConfigViewModel : BaseObservable() {

    private var foregroundDisposables = CompositeDisposable()

    fun onViewResumed() {
        foregroundDisposables = CompositeDisposable()
    }

    fun onViewPaused() {
        foregroundDisposables.clear()
    }
}
