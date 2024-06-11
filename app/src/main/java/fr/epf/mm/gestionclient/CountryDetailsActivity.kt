package fr.epf.mm.gestionclient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import com.squareup.picasso.Picasso
import fr.epf.mm.gestionclient.databinding.ActivityCountryDetailsBinding
import org.osmdroid.views.overlay.Marker

class CountryDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountryDetailsBinding
    private lateinit var mapView: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val context = applicationContext
        val osmConfig = org.osmdroid.config.Configuration.getInstance()
        osmConfig.load(context, android.preference.PreferenceManager.getDefaultSharedPreferences(context))

        mapView = findViewById(R.id.map_view)
        mapView.setTileSource(TileSourceFactory.MAPNIK)


        val country = intent.getParcelableExtra<Country>("country")
        country?.let {

            val latLng = GeoPoint(it.latlng[0], it.latlng[1])

            mapView.controller.setCenter(latLng)
            mapView.controller.setZoom(5.0)

            val mapOverlay = Marker(mapView)
            mapOverlay.position = latLng
            mapView.overlays.add(mapOverlay)

            val country = intent.getParcelableExtra<Country>("country")
            country?.let {
                binding.countryNameTextview.text = it.name
                binding.countryCapitalTextview.text = it.capital ?: "N/A"
                binding.countryLanguageTextview.text =
                    it.languages.joinToString { language -> language.name }
                binding.countryPopulationTextview.text = it.population.toString()
                binding.countryContinentTextview.text = it.region
                binding.countryCurrencyTextview.text =
                    it.currencies.joinToString { currency -> currency.name ?: "N/A" }
                Picasso.get().load(it.flags.png).into(binding.countryFlagImageview)
            }
        }

    }
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
}
