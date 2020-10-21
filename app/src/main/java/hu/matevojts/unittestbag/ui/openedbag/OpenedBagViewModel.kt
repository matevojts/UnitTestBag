package hu.matevojts.unittestbag.ui.openedbag

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import com.jakewharton.rxrelay2.PublishRelay
import hu.matevojts.unittestbag.R
import hu.matevojts.unittestbag.datasource.BagDataSource
import hu.matevojts.unittestbag.model.Bag
import hu.matevojts.unittestbag.observeOnMainThread
import hu.matevojts.unittestbag.trigger
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import timber.log.Timber

class OpenedBagViewModel(private val bagDataSource: BagDataSource) : BaseObservable() {

    val items: ObservableList<BagItemViewModel> = ObservableArrayList()

    private var foregroundDisposables = CompositeDisposable()

    val output = object : Output {
        override val emptyBag = PublishRelay.create<Unit>()
    }

    fun onViewResumed() {
        foregroundDisposables = CompositeDisposable()

        items.clear()

        bagDataSource
            .getBag()
            .observeOnMainThread()
            .subscribe(
                { bag ->
                    if (bag.red == 0 && bag.blue == 0) {
                        output.emptyBag.trigger()
                    } else {
                        populateBagItems(bag)
                    }
                },
                { Timber.e(it) },
                { output.emptyBag.trigger() }
            )
            .addTo(foregroundDisposables)
    }

    private fun populateBagItems(bag: Bag) {
        if (bag.red > 0) {
            items.add(BagItemViewModel(BagItem(R.drawable.ball_red, "Red Title", "Red description")))
        }
        if (bag.blue > 0) {
            items.add(BagItemViewModel(BagItem(R.drawable.ball_blue, "Blue Title", "Blue description")))
        }
    }

    private fun addRedBagItem(bag: Bag) {

    }

    fun onViewPaused() {
        foregroundDisposables.clear()
    }

    interface Output {
        val emptyBag: PublishRelay<Unit>
    }
}
