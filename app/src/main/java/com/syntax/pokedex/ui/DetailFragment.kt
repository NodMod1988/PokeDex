package com.syntax.pokedex.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.syntax.pokedex.PokemonViewModel
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

        val name: String? = requireArguments().getString("name")

        if (name != null) {
            viewModel.loadPokeArtwork(name)
            val image = binding.imgPokemonPic
            image.load(image)
        }

    }
}