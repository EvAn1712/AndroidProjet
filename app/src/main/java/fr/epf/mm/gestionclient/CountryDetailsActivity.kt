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
            binding.countryNameTextview.text = it.name.common
            binding.countryCapitalTextview.text = it.capital.joinToString()
            Picasso.get().load(it.flags.png).into(binding.countryFlagImageview)
        }
    }
}
