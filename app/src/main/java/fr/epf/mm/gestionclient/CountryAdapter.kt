package fr.epf.mm.gestionclient

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.util.Locale
class CountryAdapter(
    private val context: Context,
    private var countries: List<Country>,
    private val onItemClick: (Country) -> Unit
) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    private var filteredCountries: List<Country> = countries.sortedBy { it.name }

    inner class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.country_name_textview)
        val flagImageView: ImageView = view.findViewById(R.id.country_flag_imageview)
        val favoriteButton: ImageButton = view.findViewById(R.id.favorite_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.country_view, parent, false)
        return CountryViewHolder(view)
    }

    override fun getItemCount() = filteredCountries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = filteredCountries[position]
        holder.nameTextView.text = country.name
        Picasso.get().load(country.flags.png).into(holder.flagImageView)

        if (country.isFavorite) {
            holder.favoriteButton.setImageResource(R.drawable.ic_star_yellow)
        } else {
            holder.favoriteButton.setImageResource(R.drawable.ic_star_outline)
        }

        holder.itemView.setOnClickListener {
            onItemClick(country)
        }

        holder.favoriteButton.setOnClickListener {
            toggleFavorite(holder.adapterPosition)
        }
    }

    fun filter(query: String, onlyFavorites: Boolean = false) {
        filteredCountries = if (query.isEmpty()) {
            if (onlyFavorites) {
                countries.filter { it.isFavorite }
            } else {
                countries
            }
        } else {
            val filtered = countries.filter { country ->
                country.name.toLowerCase(Locale.getDefault()).contains(query.toLowerCase(Locale.getDefault())) ||
                        (country.capital?.toLowerCase(Locale.getDefault())?.contains(query.toLowerCase(Locale.getDefault())) ?: false)
            }
            if (onlyFavorites) {
                filtered.filter { it.isFavorite }
            } else {
                filtered
            }
        }
        filteredCountries = filteredCountries.sortedBy { it.name }
        notifyDataSetChanged()
    }

    fun toggleFavorite(position: Int) {
        val country = filteredCountries[position]
        country.isFavorite = !country.isFavorite
        notifyItemChanged(position)
    }

    fun setCountries(countries: List<Country>) {
        this.countries = countries.sortedBy { it.name }
        this.filteredCountries = this.countries
        notifyDataSetChanged()
    }
}