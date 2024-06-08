package fr.epf.mm.gestionclient
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import fr.epf.mm.gestionclient.databinding.ActivityCountryListBinding
import fr.epf.mm.gestionclientimport.CountryAdapter


class CountryListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountryListBinding
    private val viewModel: CountryViewModel by viewModels()
    private lateinit var adapter: CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.countryListRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.countries.observe(this, Observer { countries ->
            adapter = CountryAdapter(this, countries) // Passer le contexte en premier argument
            recyclerView.adapter = adapter
        })

        viewModel.fetchCountries()

        // Configure le SearchView
        val searchView = binding.countrySearchView
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText.orEmpty())
                return true
            }
        })
    }
}

