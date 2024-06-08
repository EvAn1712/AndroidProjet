package fr.epf.mm.gestionclient

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import fr.epf.mm.gestionclient.databinding.ActivityCountryListBinding

class CountryListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountryListBinding
    private val viewModel: CountryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.countryListRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.countries.observe(this, Observer { countries ->
            recyclerView.adapter = CountryAdapter(countries)
        })

        viewModel.fetchCountries()
    }
}
