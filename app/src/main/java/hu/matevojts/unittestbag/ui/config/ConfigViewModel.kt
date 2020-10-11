package hu.matevojts.unittestbag.ui.config

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.jakewharton.rxrelay2.PublishRelay
import hu.matevojts.unittestbag.datasource.BagDataSource
import hu.matevojts.unittestbag.model.Bag
import hu.matevojts.unittestbag.trigger
import io.reactivex.disposables.CompositeDisposable

class ConfigViewModel(private val bagDataSource: BagDataSource) : BaseObservable() {

    @get:Bindable
    var red: String = ""
        set(value) {
            if (value != field) {
                field = value
                notifyPropertyChanged(BR.red)
            }
        }

    @get:Bindable
    var blue: String = ""
        set(value) {
            if (value != field) {
                field = value
                notifyPropertyChanged(BR.blue)
            }
        }

    val output = object : Output {
        override val openBag = PublishRelay.create<Unit>()
        override val invalidItems = PublishRelay.create<Unit>()
    }

    private var foregroundDisposables = CompositeDisposable()

    fun onViewResumed() {
        foregroundDisposables = CompositeDisposable()
    }

    fun onViewPaused() {
        foregroundDisposables.clear()
    }

    fun openBag() {
        try {
            val bag = Bag(red.toInt(), blue.toInt())
            bagDataSource.setBag(bag)
            output.openBag.trigger()
        } catch (e: Exception) {
            output.invalidItems.trigger()
        }
    }

    interface Output {
        val openBag: PublishRelay<Unit>
        val invalidItems: PublishRelay<Unit>
    }
}
