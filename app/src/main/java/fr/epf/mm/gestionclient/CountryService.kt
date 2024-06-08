package fr.epf.mm.gestionclient

import retrofit2.http.GET

interface CountryService {
    @GET("all")
    suspend fun getCountries(): List<Country>
}
