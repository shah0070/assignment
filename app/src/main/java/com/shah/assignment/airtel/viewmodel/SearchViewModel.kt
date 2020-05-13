package com.shah.assignment.airtel.viewmodel


import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.shah.assignment.airtel.di.DaggerApiComponent
import com.shah.assignment.airtel.model.AddressBaseModel
import com.shah.assignment.airtel.model.AddressList
import com.shah.assignment.airtel.repository.CountriesService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SearchViewModel : ViewModel() {

    @Inject
    lateinit var countriesService: CountriesService

    //remove garbage memory
    private val disposable = CompositeDisposable()

    // data variable
    var countries = MutableLiveData<List<AddressList>>()

    //loading and error
    var countryLoadError = MutableLiveData<Boolean>()

    //error variable
    var loading = MutableLiveData<Boolean>()


    init {
        DaggerApiComponent.create().viewModelInject(this)
        countryLoadError.value = false
        loading.value = false
        countries.value = arrayListOf()
    }


    fun getcontryList(): MutableLiveData<List<AddressList>> {
        return countries
    }

    fun getLoadingState(): MutableLiveData<Boolean> {
        return loading
    }

    fun getApiErrorState(): MutableLiveData<Boolean> {
        return countryLoadError
    }



    fun clearlist() {
        loading.value = false
        countries.value = arrayListOf()
    }

    fun fetchCountries(searchCountry: String) {
        loading.value = true
        disposable.add(
                countriesService.getCountries(searchCountry)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<AddressBaseModel>() {
                            override fun onSuccess(value: AddressBaseModel?) {
                                countries.value = value?.data?.addressList
                                countryLoadError.value = false
                                loading.value = false
                            }

                            override fun onError(e: Throwable?) {
                                countryLoadError.value = true
                                loading.value = false
                            }
                        })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}