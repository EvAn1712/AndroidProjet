package fr.epf.mm.gestionclient

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    val name: Name,
    val capital: ArrayList<String>,
    val languages: HashMap<String, String>,
    val currencies: HashMap<String, CurrencyDetail>,
    val population: Long,
    val flags: Flags,
    val continents: ArrayList<String>
) : Parcelable

@Parcelize
data class Name(
    val common: String,
    val official: String
) : Parcelable

@Parcelize
data class Flags(
    val png: String
) : Parcelable

@Parcelize
class CurrencyDetail(
    val name: String?,
    val symbol: String?
) :Parcelable
