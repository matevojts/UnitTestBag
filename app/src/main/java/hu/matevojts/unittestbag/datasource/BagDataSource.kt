package hu.matevojts.unittestbag.datasource

import hu.matevojts.unittestbag.model.Bag
import io.reactivex.Maybe

class BagDataSource {

    private var bagCache: Bag? = null

    fun getBag(): Maybe<Bag> =
        Maybe.defer {
            bagCache.let { bag ->
                if (bag != null) {
                    Maybe.just(bag)
                } else {
                    Maybe.empty()
                }
            }
        }

    fun setBag(bag: Bag) {
        bagCache = bag
    }
}
