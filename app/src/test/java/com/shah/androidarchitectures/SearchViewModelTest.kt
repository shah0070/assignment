package com.shah.androidarchitectures

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.shah.assignment.airtel.model.AddressBaseModel
import com.shah.assignment.airtel.model.AddressList
import com.shah.assignment.airtel.model.ModelData
import com.shah.assignment.airtel.repository.CountriesService
import com.shah.assignment.airtel.viewmodel.SearchViewModel
import io.reactivex.Single
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*

class SearchViewModelTest {

    @Rule @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    var countriesService: CountriesService? = null
    var viewModel: SearchViewModel? = null

    // data variable
    @Mock
    var errorObserver: Observer<Boolean>? = null

    @Mock
    var lifecycleOwner: LifecycleOwner? = null
    var lifecycle: Lifecycle? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = SearchViewModel()
    }

    @Test
    fun testNull() {
        Mockito.`when`(countriesService!!.getCountries("india")).thenReturn(null)
        Assert.assertNotNull(viewModel!!.getApiErrorState())
        Assert.assertEquals(false, viewModel!!.getApiErrorState().value)
        Assert.assertEquals(false, viewModel!!.loading.value)
        Assert.assertEquals(0, viewModel!!.countries.value!!.size)
    }

    @Test
    fun testApiFetchDataError() {
        //replace errordata to any string that generate api erro then only errorstate will get true
        Mockito.`when`(countriesService!!.getCountries("errordata")).thenReturn(null)
        viewModel!!.fetchCountries("errordata")
        Assert.assertEquals(false, viewModel!!.getApiErrorState().value)
    }

    @Test
    fun testApiFetchDataSuccess() {
        val v: Double? = null
        val vv: Double? = null
        val list: MutableList<AddressList> = ArrayList()
        list.add(AddressList("id", "city", "address", v, vv, "lable"))
        val data = AddressBaseModel("requestid",
                ModelData("completestring", "focusword", list))
        Mockito.`when`(countriesService!!.getCountries("india")).thenReturn(Single.just(data))
        viewModel!!.fetchCountries("india")
        Assert.assertEquals(false, viewModel!!.getApiErrorState().value)
    }



    @After
    @Throws(Exception::class)
    fun tearDown() {
        countriesService = null
        viewModel = null
    }
}