package com.syntax.pokedex.ui

import android.os.Bundle
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import coil.load
import com.syntax.pokedex.PokemonViewModel
import com.syntax.pokedex.R
import com.syntax.pokedex.data.local.databasemodel.DatabasePokemon
import com.syntax.pokedex.databinding.FragmentDetailBinding


class DetailFragment: Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: PokemonViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name: String = requireArguments().getString("name", "test")

        viewModel.loadPokeDetails(name)

        viewModel.pokemonDetails.observe(
            viewLifecycleOwner,
            Observer {
                binding.imgPokemonPic.load(it.picture)
                binding.txtPokeId.text = it.pokeId.toString()
                binding.txtPokeName.text = it.name
                binding.cvPokemonDetail.setBackgroundResource(
                    when(it.primaryType){
                        "fire" -> R.drawable.gradient_fire
                        "grass" -> R.drawable.gradient_grass
                        "water" -> R.drawable.gradient_water
                        "bug" -> R.drawable.gradient_bug
                        "poison" -> R.drawable.gradient_poison
                        "psychic" ->R.drawable.gradient_psychic
                        "electric" -> R.drawable.gradient_electric
                        "fighting" -> R.drawable.gradient_fighting
                        "dragon" -> R.drawable.gradient_dragon
                        "dark" -> R.drawable.gradient_dark
                        "normal" -> R.drawable.gradient_normal
                        "fairy" -> R.drawable.gradient_fairy
                        "flying" -> R.drawable.gradient_flying
                        "steel" -> R.drawable.gradient_steel
                        "ice" -> R.drawable.gradient_ice
                        "rock" -> R.drawable.gradient_rock
                        "ghost" -> R.drawable.gradient_ghost
                        "ground" -> R.drawable.gradient_ground
                        else -> R.drawable.gradient_normal

                    }
                )

            }
        )

    }
}