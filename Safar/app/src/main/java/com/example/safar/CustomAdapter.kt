package com.example.safar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

interface OnItemClickListener {
    fun onItemClick(item: SampleData)
}

class CustomAdapter(private val dataList: List<SampleData>, private val listener: OnItemClickListener) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val busNameTextView: TextView = itemView.findViewById(R.id.busNameTextView)
        val seatsLeftTextView: TextView = itemView.findViewById(R.id.seatsLeftButton)
        val departureTimeTextView: TextView = itemView.findViewById(R.id.departureTimeTextView)
        val travelTimeTextView: TextView = itemView.findViewById(R.id.travelTimeTextView)
        val arrivalTimeTextView: TextView = itemView.findViewById(R.id.arrivalTimeTextView)
        val departurePlaceTextView: TextView = itemView.findViewById(R.id.departurePlaceTextView)
        val arrivalPlaceTextView: TextView = itemView.findViewById(R.id.arrivalPlaceTextView)
        val oneWayTextView: TextView = itemView.findViewById(R.id.oneWayTextView)
        val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(dataList[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]

        holder.busNameTextView.text = data.busName
        holder.seatsLeftTextView.text = data.seatsLeft
        holder.departureTimeTextView.text = data.departureTime
        holder.travelTimeTextView.text = data.travelTime
        holder.arrivalTimeTextView.text = data.arrivalTime
        holder.departurePlaceTextView.text = data.departurePlace
        holder.arrivalPlaceTextView.text = data.arrivalPlace
        holder.oneWayTextView.text = data.oneWay
        holder.priceTextView.text = data.price
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
