package fr.epf.mm.gestionclient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import fr.epf.mm.gestionclient.databinding.ActivityCountryDetailsBinding

class CountryDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountryDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val country = intent.getParcelableExtra<Country>("country")
        country?.let {
            binding.countryNameTextview.text = it.name
            binding.countryCapitalTextview.text = it.capital ?: "N/A"
            binding.countryLanguageTextview.text = it.languages.joinToString { language -> language.name }
            binding.countryPopulationTextview.text = it.population.toString()
            binding.countryContinentTextview.text = it.region
            binding.countryCurrencyTextview.text = it.currencies.joinToString { currency -> currency.name ?: "N/A" }
            Picasso.get().load(it.flags.png).into(binding.countryFlagImageview)
        }
    }
}
