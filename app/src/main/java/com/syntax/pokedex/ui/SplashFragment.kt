package com.syntax.pokedex.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.syntax.pokedex.PokemonViewModel
import com.syntax.pokedex.adapter.HomeAdapter
import com.syntax.pokedex.adapter.TypeAdapter
import com.syntax.pokedex.data.local.databasemodel.DatabasePokemon
import com.syntax.pokedex.data.local.getDatabase
import com.syntax.pokedex.databinding.FragmentHomeBinding
import com.syntax.pokedex.databinding.FragmentSplashBinding

class SplashFragment: Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private val viewModel: PokemonViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSplashBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

}