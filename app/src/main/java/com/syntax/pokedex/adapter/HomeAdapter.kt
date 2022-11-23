package com.syntax.pokedex.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.syntax.pokedex.PokemonViewModel
import com.syntax.pokedex.R
import com.syntax.pokedex.data.local.databasemodel.DatabasePokemon
import com.syntax.pokedex.data.model.PokemonListItem
import com.syntax.pokedex.data.model.pokemon.Pokemon
import com.syntax.pokedex.ui.HomeFragmentDirections
import java.util.*

class HomeAdapter() : RecyclerView.Adapter<HomeAdapter.ItemViewHolder>() {

    private var dataset = listOf<DatabasePokemon>()

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.txt_pokename)
        val image: ImageView = view.findViewById(R.id.img_pokemon)
        val layout: ConstraintLayout = view.findViewById(R.id.layout_pokemon)
        val cardView: CardView = view.findViewById(R.id.cv_pokemon)
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


        holder.title.text = item.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }

        //Setzt die image Url auf die jeweilige Position und lädt das bild

        if (item.picture != null) {
            holder.image.load(item.picture){
                transformations(RoundedCornersTransformation(10f))
                error(R.drawable.ic_round_broken_image_24)
                placeholder(R.drawable.ic_launcher_foreground)
                crossfade(true)
            }
        }

        when(item.primaryType){
            "fire" -> holder.cardView.setBackgroundResource(R.drawable.gradient_fire)
            "grass" -> holder.cardView.setBackgroundResource(R.drawable.gradient_grass)
            "water" -> holder.cardView.setBackgroundResource(R.drawable.gradient_water)
            "bug" -> holder.cardView.setBackgroundResource(R.drawable.gradient_bug)
            "poison" -> holder.cardView.setBackgroundResource(R.drawable.gradient_poison)
            "psychic" -> holder.cardView.setBackgroundResource(R.drawable.gradient_psychic)
            "electric" -> holder.cardView.setBackgroundResource(R.drawable.gradient_electric)
            "fighting" -> holder.cardView.setBackgroundResource(R.drawable.gradient_fighting)
            "dragon" -> holder.cardView.setBackgroundResource(R.drawable.gradient_dragon)
            "dark" -> holder.cardView.setBackgroundResource(R.drawable.gradient_dark)
            "normal" -> holder.cardView.setBackgroundResource(R.drawable.gradient_normal)
            "fairy" -> holder.cardView.setBackgroundResource(R.drawable.gradient_fairy)
            "flying" -> holder.cardView.setBackgroundResource(R.drawable.gradient_flying)
            "steel" -> holder.cardView.setBackgroundResource(R.drawable.gradient_steel)
            "ice" -> holder.cardView.setBackgroundResource(R.drawable.gradient_ice)
            "rock" -> holder.cardView.setBackgroundResource(R.drawable.gradient_rock)
            "ghost" -> holder.cardView.setBackgroundResource(R.drawable.gradient_ghost)
            "ground" -> holder.cardView.setBackgroundResource(R.drawable.gradient_ground)
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