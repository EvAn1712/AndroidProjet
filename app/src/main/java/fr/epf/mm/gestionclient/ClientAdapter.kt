package fr.epf.mm.gestionclient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.epf.mm.gestionclient.model.Client

class ClientViewHolder(view : View) : RecyclerView.ViewHolder(view)

//public class ClientViewHolder extends RecyclerView.ViewHolder{
//
//    public ClientViewHolder(View view){
//        super(view)
//    }
//}

class ClientAdapter(val clients: List<Client>) : RecyclerView.Adapter<ClientViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.client_view, parent, false)
        return ClientViewHolder(view)
    }

    override fun getItemCount() = clients.size


    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        val client = clients[position]
        val view = holder.itemView
        val clientNameTextView = view.findViewById<TextView>(R.id.client_view_textview)
        clientNameTextView.text = "${client.firstName} ${client.lastName}"
    }
}