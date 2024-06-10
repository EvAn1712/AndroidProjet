package fr.epf.mm.gestionclient

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.Moshi
import fr.epf.mm.gestionclient.Country
import fr.epf.mm.gestionclient.CountryService
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.util.concurrent.TimeUnit

class CountryViewModel(application: Application) : AndroidViewModel(application) {
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

        val moshi = Moshi.Builder().build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.apicountries.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()

        countryService = retrofit.create(CountryService::class.java)
    }

    private val favoritesFile: File
        get() = File(getApplication<Application>().filesDir, "favorites.json")

    fun saveFavoritesToFile(favorites: List<Country>) {
        val jsonString = Moshi.Builder().build().adapter<List<Country>>(List::class.java).toJson(favorites)
        FileWriter(favoritesFile).use { it.write(jsonString) }
    }

    fun loadFavoritesFromFile(): List<Country> {
        return try {
            val jsonString = FileReader(favoritesFile).readText()
            Moshi.Builder().build().adapter<List<Country>>(List::class.java).fromJson(jsonString) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
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
                val sortedCountries = response.sortedBy { it.name }
                _countries.value = sortedCountries
            } catch (e: Exception) {
                _countries.value = emptyList()
            }
        }
    }
}
