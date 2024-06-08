package fr.epf.mm.gestionclient

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private lateinit var countries: List<Country>
    private lateinit var currentCountry: Country

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val flagImageView = findViewById<ImageView>(R.id.flag_image_view)
        val countryGuessEditText = findViewById<EditText>(R.id.country_guess_edit_text)
        val submitButton = findViewById<Button>(R.id.submit_button)
        val resultTextView = findViewById<TextView>(R.id.result_text_view)

        // Récupérer la liste des pays depuis l'intent
        countries = intent.getParcelableArrayListExtra("countries") ?: emptyList()

        if (countries.isNotEmpty()) {
            setRandomCountry()
            Picasso.get().load(currentCountry.flags.png).into(flagImageView)
        }

        submitButton.setOnClickListener {
            val userGuess = countryGuessEditText.text.toString().trim()
            if (userGuess.equals(currentCountry.name.common, ignoreCase = true)) {
                resultTextView.text = "Bravo !"
                resultTextView.setTextColor(resources.getColor(R.color.correct_green))
            } else {
                resultTextView.text = "Incorrect, c'était ${currentCountry.name.common}."
                resultTextView.setTextColor(resources.getColor(R.color.incorrect_red))
            }
            resultTextView.visibility = TextView.VISIBLE
            countryGuessEditText.setText("")

            // Afficher un nouveau drapeau après quelques secondes
            resultTextView.postDelayed({
                setRandomCountry()
                Picasso.get().load(currentCountry.flags.png).into(flagImageView)
                resultTextView.visibility = TextView.GONE
            }, 2000)
        }
    }

    private fun setRandomCountry() {
        val randomIndex = Random.nextInt(countries.size)
        currentCountry = countries[randomIndex]
    }
}