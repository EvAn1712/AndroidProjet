package fr.epf.mm.gestionclient
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.epf.mm.gestionclient.CountryDetailsActivity
import fr.epf.mm.gestionclient.R

class CountryAdapter(private val countries: List<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

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
        val cardView = view.findViewById<CardView>(R.id.country_cardview) // Assurez-vous que le CardView est correctement initialisé

        nameTextView.text = country.name.toString()
        Picasso.get().load(country.flags.png).into(flagImageView)

        // Vérifiez si cardView est null avant de définir un OnClickListener
        cardView?.setOnClickListener {
            with(it.context) {
                val intent = Intent(this, CountryDetailsActivity::class.java)
                intent.putExtra("country", country)
                startActivity(intent)
            }
        }
    }

    class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view)
}