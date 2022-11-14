package com.syntax.pokedex.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import coil.load
import com.syntax.pokedex.PokemonViewModel
import com.syntax.pokedex.data.model.pokemon.Types
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
            }
        )


        /*if (name != null) {
            viewModel.loadPokeDetails(name)
            viewModel.pokemon.observe(
                viewLifecycleOwner,
                Observer {
                    binding.imgPokemonPic.load(it.sprites.other.officialArtwork.front_default)
                    binding.txtPokeId.text = it.id.toString()
                    binding.txtPokeHeight.text = it.height.toString()
                    binding.txtPokeWeight.text = it.weight.toString()
                    binding.txtPokeName.text = it.name
                    binding.txtPokeType.text = it.types.toString()
                }
            )
        }*/

    }
}