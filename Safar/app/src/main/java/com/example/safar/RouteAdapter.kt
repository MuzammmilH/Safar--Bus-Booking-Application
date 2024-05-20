package com.example.safar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RouteAdapter(private val routeList: List<Route>) : RecyclerView.Adapter<RouteAdapter.RouteViewHolder>() {

    class RouteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewBus: ImageView = itemView.findViewById(R.id.imageViewBus)
        val textViewRoute: TextView = itemView.findViewById(R.id.textViewRoute)
      //  val imageViewStars: ImageView = itemView.findViewById(R.id.imageViewStars)
        val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)
        val textViewAddress: TextView = itemView.findViewById(R.id.textViewAddress)
        val textViewPrice: TextView = itemView.findViewById(R.id.textViewPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.popular_routes_recycler_view, parent, false)
        return RouteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RouteViewHolder, position: Int) {
        val currentItem = routeList[position]
        holder.imageViewBus.setImageResource(currentItem.imageResource)
        holder.textViewRoute.text = currentItem.route
//        holder.imageViewStars.setImageResource(currentItem.ratingResource)
        holder.textViewDate.text = currentItem.date
        holder.textViewAddress.text = currentItem.address
        holder.textViewPrice.text = currentItem.price
    }

    override fun getItemCount() = routeList.size
}
