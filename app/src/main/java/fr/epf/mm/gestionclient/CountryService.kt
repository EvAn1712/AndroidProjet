package fr.epf.mm.gestionclient

import retrofit2.http.GET

interface CountryService {
    @GET("countries")
    suspend fun getCountries(): List<Country>
}
