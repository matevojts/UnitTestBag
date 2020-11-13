package hu.matevojts.unittestbag.ui.openedbag

import hu.matevojts.unittestbag.R
import hu.matevojts.unittestbag.ResourceProvider
import hu.matevojts.unittestbag.datasource.BagDataSource
import hu.matevojts.unittestbag.model.Bag
import io.mockk.clearAllMocks
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

    private val viewModel by lazy { OpenedBagViewModel(bagDataSource, resourceProvider) }

    @Before
    fun setUp() {
        clearAllMocks()
        every { resourceProvider.getString(any()) } returns "defaultText"
        every { resourceProvider.getString(any(), any()) } returns "defaultText"
    }

    @Test
    fun thereIsNoBag_BagLoadedOnOpenedBagScreen_EmptyBagEventSent() {
        givenBagState(BagState.Empty)
        val emptyBagError = viewModel.output.emptyBag.test()

        viewModel.onViewResumed()
        emptyBagError.assertValueCount(1)
        emptyBagError.assertNotComplete()
    }

    @Test
    fun thereIsBagWithZeroValuesInIt_BagLoadedOnOpenedBagScreen_EmptyBagEventSent() {
        givenBagState(BagState.Error)
        val emptyBagError = viewModel.output.emptyBag.test()

        viewModel.onViewResumed()
        emptyBagError.assertValueCount(1)
        emptyBagError.assertNotComplete()
    }

    @Test
    fun errorDuringGettingBag_BagLoadedOnOpenedBagScreen_EmptyBagEventSent() {
        every { bagDataSource.getBag() } returns Maybe.error(Throwable())
        val emptyBagError = viewModel.output.emptyBag.test()

        viewModel.onViewResumed()
        emptyBagError.assertValueCount(1)
        emptyBagError.assertNotComplete()
    }

    @Test
    fun unlimitedRedAndBlueItemsInBag_BagLoadedOnOpenedBagScreen_ProperBagItemsPopulated() {
        every { bagDataSource.getBag() } returns Maybe.just(Bag(10, 10))
        every { resourceProvider.getString(R.string.red_item_title) } returns redTitle
        every { resourceProvider.getString(R.string.red_item_description_unlimited) } returns redDescription
        every { resourceProvider.getString(R.string.blue_item_title_multiple) } returns blueTitle
        every { resourceProvider.getString(R.string.blue_item_description_unlimited) } returns blueDescription

        val expectedRedItem = BagItem(R.drawable.ball_red, redTitle, redDescription)
        val expectedBlueItem = BagItem(R.drawable.ball_blue, blueTitle, blueDescription)

        viewModel.onViewResumed()
        assert(viewModel.items.count() == 2)
        assert(viewModel.items[0].bagItem == expectedRedItem)
        assert(viewModel.items[1].bagItem == expectedBlueItem)
    }

    @Test
    fun nineRedAndBlueItemsInBag_BagLoadedOnOpenedBagScreen_ProperBagItemsPopulated() {
        every { bagDataSource.getBag() } returns Maybe.just(Bag(9, 9))
        every { resourceProvider.getString(R.string.red_item_title) } returns redTitle
        every { resourceProvider.getString(R.string.red_item_description_plural, 9) } returns redDescription
        every { resourceProvider.getString(R.string.blue_item_title_multiple) } returns blueTitle
        every { resourceProvider.getString(R.string.blue_item_description_plural, 9) } returns blueDescription

        val expectedRedItem = BagItem(R.drawable.ball_red, redTitle, redDescription)
        val expectedBlueItem = BagItem(R.drawable.ball_blue, blueTitle, blueDescription)

        viewModel.onViewResumed()
        assert(viewModel.items.count() == 2)
        assert(viewModel.items[0].bagItem == expectedRedItem)
        assert(viewModel.items[1].bagItem == expectedBlueItem)
    }

    @Test
    fun oneRedAndZeroBlueItemsInBag_BagLoadedOnOpenedBagScreen_ProperBagItemsPopulated() {
        every { bagDataSource.getBag() } returns Maybe.just(Bag(red = 1, blue = 0))
        every { resourceProvider.getString(R.string.red_item_title) } returns redTitle
        every { resourceProvider.getString(R.string.red_item_description_singular, 1) } returns redDescription

        val expectedRedItem = BagItem(R.drawable.ball_red, redTitle, redDescription)

        viewModel.onViewResumed()
        assert(viewModel.items.count() == 1)
        assert(viewModel.items[0].bagItem == expectedRedItem)
    }

    @Test
    fun zeroRedAndOneBlueItemsInBag_BagLoadedOnOpenedBagScreen_ProperBagItemsPopulated() {
        every { bagDataSource.getBag() } returns Maybe.just(Bag(red = 0, blue = 1))
        every { resourceProvider.getString(R.string.blue_item_title_single) } returns blueTitle
        every { resourceProvider.getString(R.string.blue_item_description_singular, 1) } returns blueDescription

        val expectedBlueItem = BagItem(R.drawable.ball_blue, blueTitle, blueDescription)

        viewModel.onViewResumed()
        assert(viewModel.items.count() == 1)
        assert(viewModel.items[0].bagItem == expectedBlueItem)
    }

    private fun givenBagState(state: BagState) {
        when (state) {
            BagState.Empty -> every { bagDataSource.getBag() } returns Maybe.empty()
            BagState.Error -> every { bagDataSource.getBag() } returns Maybe.error(Throwable())
            is BagState.MockedBag -> TODO()
        }
    }

    sealed class BagState {
        object Empty : BagState()
        object Error : BagState()
        class MockedBag(val red: Int, val blue: Int) : BagState()
    }
}
