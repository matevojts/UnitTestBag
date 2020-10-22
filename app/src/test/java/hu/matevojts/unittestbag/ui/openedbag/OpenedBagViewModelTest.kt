package hu.matevojts.unittestbag.ui.openedbag

import hu.matevojts.unittestbag.R
import hu.matevojts.unittestbag.ResourceProvider
import hu.matevojts.unittestbag.datasource.BagDataSource
import hu.matevojts.unittestbag.model.Bag
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Maybe
import org.junit.Before
import org.junit.Test

class OpenedBagViewModelTest {

    private val bagDataSource: BagDataSource = mockk()
    private val resourceProvider: ResourceProvider = mockk()
    private val redTitle = "expectedRedTitle"
    private val redDescription = "expectedRedDescription"
    private val blueTitle = "expectedBlueTitle"
    private val blueDescription = "expectedBlueDescription"

    private val service by lazy { OpenedBagViewModel(bagDataSource, resourceProvider) }

    @Before
    fun setUp() {
        every { resourceProvider.getString(any()) } returns "defaultText"
        every { resourceProvider.getString(any(), any()) } returns "defaultText"
    }

    @Test
    fun thereIsNoBag_BagLoadedOnOpenedBagScreen_EmptyBagEventSent() {
        every { bagDataSource.getBag() } returns Maybe.empty()
        val emptyBagError = service.output.emptyBag.test()

        service.onViewResumed()
        emptyBagError.assertValueCount(1)
        emptyBagError.assertNotComplete()
    }

    @Test
    fun thereIsBagWithZeroValuesInIt_BagLoadedOnOpenedBagScreen_EmptyBagEventSent() {
        every { bagDataSource.getBag() } returns Maybe.just(Bag(0, 0))
        val emptyBagError = service.output.emptyBag.test()

        service.onViewResumed()
        emptyBagError.assertValueCount(1)
        emptyBagError.assertNotComplete()
    }

    @Test
    fun errorDuringGettingBag_BagLoadedOnOpenedBagScreen_EmptyBagEventSent() {
        every { bagDataSource.getBag() } returns Maybe.error(Throwable())
        val emptyBagError = service.output.emptyBag.test()

        service.onViewResumed()
        emptyBagError.assertValueCount(1)
        emptyBagError.assertNotComplete()
    }

    @Test
    fun unlimitedRedAndBlueItemsInBag_BagLoadedOnOpenedBagScreen_ProperBagItemsPopulated() {
        every { bagDataSource.getBag() } returns Maybe.just(Bag(10, 10))
        every { resourceProvider.getString(R.string.red_item_title) } returns redTitle
        every { resourceProvider.getString(R.string.red_item_description_unlimited) } returns redDescription
        every { resourceProvider.getString(R.string.blue_item_title) } returns blueTitle
        every { resourceProvider.getString(R.string.blue_item_description_unlimited) } returns blueDescription

        val expectedRedItem = BagItem(R.drawable.ball_red, redTitle, redDescription)
        val expectedBlueItem = BagItem(R.drawable.ball_blue, blueTitle, blueDescription)

        service.onViewResumed()
        assert(service.items.count() == 2)
        assert(service.items[0].bagItem == expectedRedItem)
        assert(service.items[1].bagItem == expectedBlueItem)
    }

    @Test
    fun nineRedAndBlueItemsInBag_BagLoadedOnOpenedBagScreen_ProperBagItemsPopulated() {
        every { bagDataSource.getBag() } returns Maybe.just(Bag(9, 9))
        every { resourceProvider.getString(R.string.red_item_title) } returns redTitle
        every { resourceProvider.getString(R.string.red_item_description_plural, 9) } returns redDescription
        every { resourceProvider.getString(R.string.blue_item_title) } returns blueTitle
        every { resourceProvider.getString(R.string.blue_item_description_plural, 9) } returns blueDescription

        val expectedRedItem = BagItem(R.drawable.ball_red, redTitle, redDescription)
        val expectedBlueItem = BagItem(R.drawable.ball_blue, blueTitle, blueDescription)

        service.onViewResumed()
        assert(service.items.count() == 2)
        assert(service.items[0].bagItem == expectedRedItem)
        assert(service.items[1].bagItem == expectedBlueItem)
    }
}
