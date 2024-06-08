package fr.epf.mm.gestionclient

import android.app.Application

class MyApp : Application() {
    lateinit var countries: List<Country>
}
