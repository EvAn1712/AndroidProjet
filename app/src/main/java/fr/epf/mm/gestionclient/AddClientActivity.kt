package fr.epf.mm.gestionclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class AddClientActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_client)

        val lastNameEditext = findViewById<EditText>(R.id.add_client_last_name_edittext)
        val addButton = findViewById<Button>(R.id.add_client_button)


        addButton.setOnClickListener{
            Log.d("EPF", "Nom : ${lastNameEditext.text}")
        }

    }
}