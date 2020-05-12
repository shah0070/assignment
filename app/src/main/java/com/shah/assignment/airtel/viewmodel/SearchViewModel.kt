package com.shah.assignment.airtel.viewmodel


import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shah.assignment.airtel.di.DaggerApiComponent
import com.shah.assignment.airtel.model.AddressBaseModel
import com.shah.assignment.airtel.model.AddressList
import com.shah.assignment.airtel.repository.CountriesService
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
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
    private var searchViewDisposable: CompositeDisposable

    init {
        DaggerApiComponent.create().viewModelInject(this)
        searchViewDisposable = CompositeDisposable()
    }

    private val disposable = CompositeDisposable()

    // data variable
    val countries = MutableLiveData<List<AddressList>>()

    //loading and error
    val countryLoadError = MutableLiveData<Boolean>()

    //error variable
    val loading = MutableLiveData<Boolean>()


    fun setcloseView(searchview: ImageView) {
        searchViewDisposable.add(RxView.clicks(searchview)
                .subscribe {
                    getSearChView().let {
                        it?.text = null
                        clearlist()
                    }
                }
        )
    }

    fun getSearChView(): EditText? {
        return searchview
    }

    var searchview: EditText? = null
    fun setSearchViewwithFilter(searchview: EditText) {
        this.searchview = searchview
        searchViewDisposable.add(RxTextView.textChanges(searchview)
                .debounce(1, TimeUnit.SECONDS)
                .map { it.toString() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.isNotEmpty() && it.length > 2) {
                        fetchCountries(it.toString())
                    } else {
                        clearlist()
                    }
                }
        )
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
        searchViewDisposable.dispose()
        disposable.clear()
    }
}