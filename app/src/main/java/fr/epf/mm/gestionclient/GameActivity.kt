package fr.epf.mm.gestionclient

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.MapEventsOverlay

class GameActivity : AppCompatActivity() {

    private lateinit var mapView: MapView
    private lateinit var correctCountry: Country
    private lateinit var correctLocation: GeoPoint
    private lateinit var viewModel: CountryViewModel
    private lateinit var countryNameTextView: TextView
    private lateinit var detailTextView: TextView
    private lateinit var choiceButtons: List<Button>
    private var countryList: List<Country> = emptyList()
    private val details = listOf("Monnaie", "Capitale", "Langue", "Population", "Continent")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Configuration.getInstance().load(applicationContext, getSharedPreferences("OpenStreetMap", 0))

        setContentView(R.layout.activity_game)

        mapView = findViewById(R.id.map_view)
        countryNameTextView = findViewById(R.id.country_name_textview)
        detailTextView = findViewById(R.id.detail_textview)
        choiceButtons = listOf(
            findViewById(R.id.choice_button_1),
            findViewById(R.id.choice_button_2),
            findViewById(R.id.choice_button_3),
            findViewById(R.id.choice_button_4)
        )

        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)

        viewModel.countries.observe(this, Observer { countries ->
            countryList = countries
            startGame()
        })

        viewModel.fetchCountries()
    }

    private fun startGame() {

        mapView.overlays.clear()
        correctCountry = selectRandomCountry()
        correctLocation = GeoPoint(correctCountry.latlng[0], correctCountry.latlng[1])

        countryNameTextView.text = correctCountry.name

        val detailToGuess = details.random()
        detailTextView.text = "Quel est le/la $detailToGuess de ce pays ?"

        val choices = selectChoices(detailToGuess)

        val shuffledChoices = choices.shuffled()
        choiceButtons.forEachIndexed { index, button ->
            button.text = shuffledChoices[index]
            button.setOnClickListener {
                val isCorrect = checkAnswer(shuffledChoices[index], detailToGuess)
                val message = if (isCorrect) "Correct !" else "Incorrect. Le $detailToGuess de ${correctCountry.name} est ${correctCountry.getDetail(detailToGuess)}."
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                if (isCorrect) startGame()
            }
        }

        mapView.controller.setCenter(correctLocation)
        mapView.controller.setZoom(5.0)

        val countryMarker = Marker(mapView)
        countryMarker.position = correctLocation
        mapView.overlays.add(countryMarker)

        mapView.overlays.add(MapEventsOverlay(object : MapEventsReceiver {
            override fun singleTapConfirmedHelper(p: GeoPoint): Boolean {
                return false
            }

            override fun longPressHelper(p: GeoPoint): Boolean {
                return false
            }
        }))
    }

    private fun selectRandomCountry(): Country {
        val randomIndex = (0 until countryList.size).random()
        return countryList[randomIndex]
    }

    private fun selectChoices(detailToGuess: String): List<String> {
        val correctDetail = correctCountry.getDetail(detailToGuess)
        val wrongChoices = countryList
            .filterNot { it == correctCountry }
            .shuffled()
            .take(3)
            .map { it.getDetail(detailToGuess) }
        return wrongChoices + correctDetail
    }

    private fun checkAnswer(selectedChoice: String, detailToGuess: String): Boolean {
        val correctDetail = correctCountry.getDetail(detailToGuess)
        return selectedChoice == correctDetail
    }
}
