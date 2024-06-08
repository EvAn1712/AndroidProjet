package fr.epf.mm.gestionclient

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view)

class CountryAdapter(private val countries: List<Country>) : RecyclerView.Adapter<CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.country_view, parent, false)
        return CountryViewHolder(view)
    }

    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        val view = holder.itemView
        val nameTextView = view.findViewById<TextView>(R.id.country_name_textview)
        val flagImageView = view.findViewById<ImageView>(R.id.country_flag_imageview)

        nameTextView.text = country.name.common
        Picasso.get().load(country.flags.png).into(flagImageView)

        val cardView = view.findViewById<CardView>(R.id.country_cardview)
        cardView.setOnClickListener {
            with(it.context) {
                val intent = Intent(this, CountryDetailsActivity::class.java)
                intent.putExtra("country", country)
                startActivity(intent)
            }
        }
    }
}
