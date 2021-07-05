package io.github.mishrilal.foodrunner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.github.mishrilal.foodrunner.R
import io.github.mishrilal.foodrunner.model.Restaurant

class RestaurantRecyclerAdapter (val context: Context, val itemList: ArrayList<Restaurant>) :
    RecyclerView.Adapter<RestaurantRecyclerAdapter.HomeViewHolder>() {

    class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtResName: TextView = view.findViewById(R.id.txtResName)
        val txtResPrice: TextView = view.findViewById(R.id.txtResPrice)
        val txtResRating: TextView = view.findViewById(R.id.txtResRating)
        val imgResImage: ImageView = view.findViewById(R.id.imgResImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recylcer_restaurant_single_row, parent, false)

        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val restaurant = itemList[position]
        holder.txtResName.text = restaurant.resName
        holder.txtResPrice.text = restaurant.resPrice
        holder.txtResRating.text = restaurant.resRating
        Picasso.get().load(restaurant.resImage).error(R.drawable.ic_default_res)
            .into(holder.imgResImage)


    }

}