package com.syntax.pokedex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.syntax.pokedex.R
import com.syntax.pokedex.data.model.pokemon.Pokemon
import com.syntax.pokedex.data.model.pokemon.PokemonList

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.ItemViewHolder>() {

    private var dataset = listOf<PokemonList>()

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val title: TextView = view.findViewById(R.id.txt_pokename)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home, parent, false)

        return ItemViewHolder(itemLayout)
    }

    fun submitList(list: List<PokemonList>) {
        dataset = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: PokemonList = dataset[position]

        holder.title.text = item.name


    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}