import com.example.raspbrrry_fridge.android.data.Product
import com.example.raspbrrry_fridge.android.data.RawProduct
import com.example.raspbrrry_fridge.android.network.BarcodeApiClient
import com.example.raspbrrry_fridge.android.viewModel.BarcodeScannerViewModel
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class BarcodeScannerViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var viewModel: BarcodeScannerViewModel

    @Before
    fun runTest() {
        Dispatchers.setMain(testDispatcher)
        viewModel = BarcodeScannerViewModel()
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test fetchProductData`() = testScope.runTest {
        val result = "1234567890"
        val expectedProduct = RawProduct(product_name_de = "Test", _id = result)

        val apiResponse = "{\"product\": {\"_id\": \"$result\", \"product_name_de\": \"Test\"}}"

        val mockedApiClient = mockk<BarcodeApiClient>()
        every { mockedApiClient.getProductByEan(result) } returns apiResponse

        viewModel.barcodeApiClient = mockedApiClient
        viewModel.showInputPopup.value = false
        viewModel.selectedIndex.intValue = 1
        viewModel.selectedProduct.value = Product(name = "Test")

        viewModel.onBarcodeScanned(result)

        assertEquals(expectedProduct, viewModel.scannedRawProduct)
        assertTrue(viewModel.showInputPopup.value)
        assertEquals(-1, viewModel.selectedIndex.intValue)
        assertEquals(Product(), viewModel.selectedProduct.value)
    }

/*    @Test
    fun `test getAllProductsWithEan`() = testScope.runTest {
        val expectedEan = "1234567890" // Set the expected EAN value

        val expectedProducts = listOf(
            Product(ean = expectedEan, weight = 20, mhd = "test"),
            Product(ean = expectedEan, weight = 10, mhd = "test")
        )

        val mockedApiClient = mockk<ProductClient>()
        coEvery { mockedApiClient.getByEan(expectedEan) } returns expectedProducts


        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.eanProducts.collect { actualProducts ->
                assertEquals(expectedProducts, actualProducts)
            }
        }

        viewModel.getAllProductsWithEan() // Call the function under test

    }*/

}
