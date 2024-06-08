package fr.epf.mm.gestionclient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val addButton = findViewById<Button>(R.id.home_add_button)
        val listButton = findViewById<Button>(R.id.home_list_button)

        addButton.setOnClickListener {
            val intent = Intent(this, AddCountryActivity::class.java)
            startActivity(intent)
        }

        listButton.setOnClickListener {
            val intent = Intent(this, CountryListActivity::class.java)
            startActivity(intent)
        }
    }
}
