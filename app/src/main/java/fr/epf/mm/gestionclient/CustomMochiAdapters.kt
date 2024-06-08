package fr.epf.mm.gestionclient

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class ArrayListAdapter {
    @ToJson
    fun toJson(list: ArrayList<String>): List<String> = list

    @FromJson
    fun fromJson(list: List<String>): ArrayList<String> = ArrayList(list)
}

class HashMapAdapter {
    @ToJson
    fun toJson(map: HashMap<String, String>): Map<String, String> = map

    @FromJson
    fun fromJson(map: Map<String, String>): HashMap<String, String> = HashMap(map)
}

class CurrencyDetailAdapter {
    @ToJson
    fun toJson(map: HashMap<String, CurrencyDetail>): Map<String, CurrencyDetail> = map

    @FromJson
    fun fromJson(map: Map<String, CurrencyDetail>): HashMap<String, CurrencyDetail> = HashMap(map)
}
