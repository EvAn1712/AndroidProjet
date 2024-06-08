package fr.epf.mm.gestionclient

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import fr.epf.mm.gestionclient.databinding.ActivityCountryListBinding

class CountryListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountryListBinding
    private val viewModel: CountryViewModel by viewModels()
    private lateinit var adapter: CountryAdapter
    private var showFavoritesOnly: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.countryListRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.countries.observe(this, Observer { countries ->
            adapter = CountryAdapter(this, countries) { country ->
                // Gestion du clic sur un pays
                val intent = Intent(this, CountryDetailsActivity::class.java)
                intent.putExtra("country", country)
                startActivity(intent)
            }
            recyclerView.adapter = adapter
        })

        viewModel.fetchCountries()

        val searchView = binding.countrySearchView
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText.orEmpty(), showFavoritesOnly)
                return true
            }
        })

        binding.filterFavoritesButton.setOnClickListener {
            showFavoritesOnly = !showFavoritesOnly
            adapter.filter(binding.countrySearchView.query.toString(), showFavoritesOnly)
            updateFilterButton()
        }
    }

    private fun updateFilterButton() {
        if (showFavoritesOnly) {
            binding.filterFavoritesButton.text = "Retour"
        } else {
            binding.filterFavoritesButton.text = "Favorits"
        }
    }
}
