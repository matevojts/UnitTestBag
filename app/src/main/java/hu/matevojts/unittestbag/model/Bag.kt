package hu.matevojts.unittestbag.model

data class Bag(val red: Int, val blue: Int) {
    companion object {
        const val ITEM_MAX_COUNT = 9
    }
}
