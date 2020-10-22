package hu.matevojts.unittestbag.ui.openedbag

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import com.jakewharton.rxrelay2.PublishRelay
import hu.matevojts.unittestbag.R
import hu.matevojts.unittestbag.ResourceProvider
import hu.matevojts.unittestbag.datasource.BagDataSource
import hu.matevojts.unittestbag.model.Bag
import hu.matevojts.unittestbag.observeOnMainThread
import hu.matevojts.unittestbag.trigger
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import timber.log.Timber

class OpenedBagViewModel(
    private val bagDataSource: BagDataSource,
    private val resourceProvider: ResourceProvider
) : BaseObservable() {

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
                    render(bag)
                },
                {
                    Timber.e(it)
                    output.emptyBag.trigger()
                },
                { output.emptyBag.trigger() }
            )
            .addTo(foregroundDisposables)
    }

    private fun render(bag: Bag) {
        if (bag.red == 0 && bag.blue == 0) {
            output.emptyBag.trigger()
        } else {
            addRedBagItem(bag)
            addBlueBagItem(bag)
        }
    }

    private fun addRedBagItem(bag: Bag) {
        val description = when (bag.red) {
            in Int.MIN_VALUE..0 -> return
            1 -> resourceProvider.getString(R.string.red_item_description_singular, bag.red)
            in 2..Bag.ITEM_MAX_COUNT -> resourceProvider.getString(R.string.red_item_description_plural, bag.red)
            else -> resourceProvider.getString(R.string.red_item_description_unlimited)
        }

        val title = resourceProvider.getString(R.string.red_item_title)

        items.add(BagItemViewModel(BagItem(R.drawable.ball_red, title, description)))
    }

    private fun addBlueBagItem(bag: Bag) {
        val description = when (bag.blue) {
            in Int.MIN_VALUE..0 -> return
            1 -> resourceProvider.getString(R.string.blue_item_description_singular, bag.blue)
            in 2..Bag.ITEM_MAX_COUNT -> resourceProvider.getString(R.string.blue_item_description_plural, bag.blue)
            else -> resourceProvider.getString(R.string.blue_item_description_unlimited)
        }

        val title = resourceProvider.getString(R.string.blue_item_title)

        items.add(BagItemViewModel(BagItem(R.drawable.ball_blue, title, description)))
    }

    fun onViewPaused() {
        foregroundDisposables.clear()
    }

    interface Output {
        val emptyBag: PublishRelay<Unit>
    }
}
