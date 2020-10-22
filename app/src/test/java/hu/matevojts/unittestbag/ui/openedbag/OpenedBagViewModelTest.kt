package hu.matevojts.unittestbag.ui.openedbag

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
}
