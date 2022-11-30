package com.syntax.pokedex.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import androidx.recyclerview.widget.RecyclerView
import com.syntax.pokedex.PokemonViewModel
import com.syntax.pokedex.R
import com.syntax.pokedex.data.local.databasemodel.DatabasePokemon
import com.syntax.pokedex.data.model.TypeRessource


class TypeAdapter(): RecyclerView.Adapter<TypeAdapter.ItemViewHolder>() {

    private var dataset = listOf<TypeRessource>()

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val typeImage: ImageView = view.findViewById(R.id.imgType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_type, parent, false)

        return TypeAdapter.ItemViewHolder(itemLayout)
    }

    fun submitList(list: List<TypeRessource>) {
        dataset = list
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: TypeRessource = dataset[position]

        holder.typeImage.setImageResource(item.typePicture)


    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}