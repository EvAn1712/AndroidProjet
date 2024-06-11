package fr.epf.mm.gestionclient

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    val name: String = "",
    val topLevelDomain: List<String> = emptyList(),
    val alpha2Code: String = "",
    val alpha3Code: String = "",
    val callingCodes: List<String> = emptyList(),
    val capital: String? = null,
    val altSpellings: List<String> = emptyList(),
    val subregion: String = "",
    val region: String = "",
    val population: Long = 0,
    val latlng: List<Double> = emptyList(),
    val demonym: String = "",
    val area: Double = 0.0,
    val timezones: List<String> = emptyList(),
    val borders: List<String> = emptyList(),
    val nativeName: String = "",
    val numericCode: String = "",
    val flags: Flags = Flags(),
    val currencies: List<CurrencyDetail> = emptyList(),
    val languages: List<Language> = emptyList(),
    val translations: Translations = Translations(),
    val regionalBlocs: List<RegionalBloc> = emptyList(),
    val cioc: String? = null,
    val independent: Boolean = false,
    var isFavorite: Boolean = false
) : Parcelable {
    fun getDetail(detailType: String): String {
        return when (detailType) {
            "Monnaie" -> currencies.joinToString(separator = ", ") { it.name ?: "N/A" }
            "Capitale" -> capital ?: "N/A"
            "Langue" -> languages.joinToString(separator = ", ") { it.name }
            "Nom du pays" -> name
            "Population" -> population.toString()
            "Continent" -> region
            else -> "N/A"
        }
    }
}

@Parcelize
data class Flags(
    val svg: String = "",
    val png: String = ""
) : Parcelable

@Parcelize
data class CurrencyDetail(
    val code: String = "",
    val name: String? = null,
    val symbol: String? = null
) : Parcelable

@Parcelize
data class Language(
    val iso639_1: String = "",
    val iso639_2: String = "",
    val name: String = "",
    val nativeName: String = ""
) : Parcelable

@Parcelize
data class Translations(
    val br: String = "",
    val pt: String = "",
    val nl: String = "",
    val hr: String = "",
    val fa: String = "",
    val de: String = "",
    val es: String = "",
    val fr: String = "",
    val ja: String = "",
    val it: String = "",
    val hu: String = ""
) : Parcelable

@Parcelize
data class RegionalBloc(
    val acronym: String = "",
    val name: String = ""
) : Parcelable
