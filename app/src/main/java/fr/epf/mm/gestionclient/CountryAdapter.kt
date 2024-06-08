package fr.epf.mm.gestionclientimport
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.epf.mm.gestionclient.Country
import fr.epf.mm.gestionclient.CountryDetailsActivity
import fr.epf.mm.gestionclient.R
import java.util.*

class CountryAdapter(private val context: Context, private var countries: List<Country>) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    private var filteredCountries: List<Country> = countries

    inner class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.country_name_textview)
        val flagImageView: ImageView = view.findViewById(R.id.country_flag_imageview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.country_view, parent, false)
        return CountryViewHolder(view)
    }

    override fun getItemCount() = filteredCountries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = filteredCountries[position]
        holder.nameTextView.text = country.name.common
        Picasso.get().load(country.flags.png).into(holder.flagImageView)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, CountryDetailsActivity::class.java)
            intent.putExtra("country", country)
            context.startActivity(intent)
        }
    }

    fun filter(query: String) {
        filteredCountries = if (query.isEmpty()) {
            countries
        } else {
            countries.filter { country ->
                country.name.common.toLowerCase(Locale.getDefault()).contains(query.toLowerCase(Locale.getDefault()))
            }
        }
        notifyDataSetChanged()
    }
}
