package fr.epf.mm.gestionclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

class AddCountryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_country)

        val countryNameEditText = findViewById<EditText>(R.id.add_country_name_edittext)
        val addButton = findViewById<Button>(R.id.add_country_button)

        val continentRadioGroup = findViewById<RadioGroup>(R.id.add_country_continent_radiogroup)
        continentRadioGroup.check(R.id.add_country_continent_asia_radiobutton)

        val populationSeekBar = findViewById<SeekBar>(R.id.add_country_population_seekbar)
        val populationTextView = findViewById<TextView>(R.id.add_country_population_textview)
        val governmentTypeSpinner = findViewById<Spinner>(R.id.add_country_government_type_spinner)

        populationSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
                populationTextView.text = progress.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) = Unit
            override fun onStopTrackingTouch(p0: SeekBar?) = Unit
        })

        addButton.setOnClickListener {
            Log.d("EPF", "Nom du pays : ${countryNameEditText.text}")
            val continent = when (continentRadioGroup.checkedRadioButtonId) {
                R.id.add_country_continent_asia_radiobutton -> "Asie"
                R.id.add_country_continent_europe_radiobutton -> "Europe"
                else -> "Autre"
            }
            Log.d("EPF", "Continent : $continent")
            Log.d("EPF", "Type de gouvernement : ${governmentTypeSpinner.selectedItem}")

            Toast.makeText(this, R.string.add_country_message, Toast.LENGTH_LONG).show()

            finish()
        }
    }
}
