package com.nagmekaraaslan.shooters_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nagmekaraaslan.shooters_app.R

class LatestShootsAdapter(private val shoots: List<String>) : RecyclerView.Adapter<LatestShootsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivShootImage: ImageView = view.findViewById(R.id.ivShootImage)
        val tvModelLink: TextView = view.findViewById(R.id.tvModelLink)
        val tvPhotographerLink: TextView = view.findViewById(R.id.tvPhotographerLink)
        val tvBrandName: TextView = view.findViewById(R.id.tvBrandName)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_latest_shoot, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Şu anlık dummy verilerle dolduruyoruz. 
        // İleride bu liste bir veri modeline (Data Class) sahip olacak.
        holder.tvModelLink.text = "Model: Nagme K."
        holder.tvPhotographerLink.text = "Photo: Nagme K."
        holder.tvBrandName.text = "Brand: Shooters Inc."
        holder.tvDate.text = "21.05.2024"
        
        // Link görünümü için altı çizgili veya farklı renk yapılabilir
        holder.tvModelLink.paint.isUnderlineText = true
        holder.tvPhotographerLink.paint.isUnderlineText = true
        
        holder.tvModelLink.setOnClickListener {
            // Profil sayfasına yönlendirme mantığı buraya gelecek
        }
    }

    override fun getItemCount(): Int = shoots.size
}