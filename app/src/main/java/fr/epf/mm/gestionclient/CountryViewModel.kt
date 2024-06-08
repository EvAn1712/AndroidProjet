package fr.epf.mm.gestionclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.Moshi
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class CountryViewModel : ViewModel() {
    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>> = _countries

    private val countryService: CountryService

    init {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()

        val moshi = Moshi.Builder()
            .add(ArrayListAdapter())
            .add(HashMapAdapter())
            .add(CurrencyDetailAdapter())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.com/v3.1/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()

        countryService = retrofit.create(CountryService::class.java)
    }

    suspend fun tryToFetchCountry(): List<Country> {
        val maxRetries = 5
        var currentRetry = 0
        var success = false
        var countriesList: List<Country> = emptyList()

        while (currentRetry < maxRetries && !success) {
            try {
                val response = countryService.getCountries()
                if (response.isNotEmpty()) {
                    countriesList = response
                    success = true
                }
            } catch (e: Exception) {
                currentRetry++
            }
        }
        return countriesList
    }

    fun fetchCountries() {
        viewModelScope.launch {
            try {
                val response = tryToFetchCountry()
                // Trier les pays par nom commun avant de mettre à jour LiveData
                val sortedCountries = response.sortedBy { it.name.common }
                _countries.value = sortedCountries
            } catch (e: Exception) {
                // Gérer les erreurs
            }
        }
    }
}

