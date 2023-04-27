package com.example.foodrecipeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodTypeAdapter(private var exampleList: MutableList<FoodType>) :
    RecyclerView.Adapter<FoodTypeAdapter.ExampleViewHolder>(), Filterable {
    private var exampleListFull: List<FoodType> = exampleList.toList()

    inner class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.image_view)
        var textView1: TextView = itemView.findViewById(R.id.text_view1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.single_item, parent, false)
        return ExampleViewHolder(v)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem: FoodType = exampleList[position]

        holder.imageView.setImageResource(currentItem.imageResource)
        holder.textView1.text = currentItem.text1
    }

    override fun getItemCount(): Int {
        return exampleList.size
    }

    override fun getFilter(): Filter {
        return exampleFilter
    }

    private val exampleFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: MutableList<FoodType> = ArrayList()

            if ((constraint == null) || constraint.isEmpty()) {
                filteredList.addAll(exampleListFull)
            } else {
                val filterPattern = constraint.toString().lowercase().trim { it <= ' ' }

                for (item in exampleListFull) {
                    if (item.text1.lowercase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }

            val results = FilterResults()
            results.values = filteredList

            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            exampleList.clear()
            exampleList.addAll(results.values as List<FoodType>)
            notifyDataSetChanged()
        }
    }
}
