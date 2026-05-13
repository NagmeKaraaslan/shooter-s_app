package com.nagmekaraaslan.shooters_app.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.nagmekaraaslan.shooters_app.R

class ShootsAdapter(private val photoList: MutableList<Uri>) : RecyclerView.Adapter<ShootsAdapter.ShootViewHolder>() {

    class ShootViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imgShoot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShootViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shoot_layout, parent, false)
        return ShootViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShootViewHolder, position: Int) {
        holder.imageView.setImageURI(photoList[position])
    }

    override fun getItemCount(): Int = photoList.size

    fun addPhoto(uri: Uri) {
        photoList.add(0, uri)
        notifyItemInserted(0)
    }
}