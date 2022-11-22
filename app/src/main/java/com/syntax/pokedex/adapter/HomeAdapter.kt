package com.syntax.pokedex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.syntax.pokedex.PokemonViewModel
import com.syntax.pokedex.R
import com.syntax.pokedex.data.local.databasemodel.DatabasePokemon
import com.syntax.pokedex.data.model.PokemonListItem
import com.syntax.pokedex.data.model.pokemon.Pokemon
import com.syntax.pokedex.ui.HomeFragmentDirections

class HomeAdapter() : RecyclerView.Adapter<HomeAdapter.ItemViewHolder>() {

    private var dataset = listOf<DatabasePokemon>()

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.txt_pokename)
        val image: ImageView = view.findViewById(R.id.img_pokemon)
        val layout: ConstraintLayout = view.findViewById(R.id.layout_pokemon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home, parent, false)

        return ItemViewHolder(itemLayout)
    }

    fun submitList(list: List<DatabasePokemon>) {
        dataset = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: DatabasePokemon = dataset[position]


        holder.title.text = item.name

        //Setzt die image Url auf die jeweilige Position und lädt das bild


        if (item.picture != null) {
            holder.image.load(item.picture)
        }else{
            // Todo default bild in ressources anlegen
        }

        holder.layout.setOnClickListener {
            holder.itemView.findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(item.name))
        }

    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}