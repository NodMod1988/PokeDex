package com.syntax.pokedex.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.syntax.pokedex.ApiStatus
import com.syntax.pokedex.PokemonViewModel
import com.syntax.pokedex.adapter.HomeAdapter
import com.syntax.pokedex.adapter.TypeAdapter
import com.syntax.pokedex.data.model.TypeRessource
import com.syntax.pokedex.databinding.FragmentHomeBinding
import java.util.*

enum class ApiStatus { LOADING, DONE, ERROR }

class HomeFragment: Fragment(), TypeInterface {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: PokemonViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.loadPokeList()
        viewModel.loadPokeTypes()
        binding = FragmentHomeBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val homeAdapter = HomeAdapter()
        binding.pokeRecycler.adapter = homeAdapter

        val typeAdapter = TypeAdapter(this)
        binding.pokeTypes.adapter = typeAdapter

        viewModel.types.observe(
            viewLifecycleOwner,
            Observer {
                typeAdapter.submitList(it)
            }
        )

        viewModel.pokemon.observe(
            viewLifecycleOwner,
            Observer {
                homeAdapter.submitList(it)
            }
        )

        viewModel.pokemonByName.observe(
            viewLifecycleOwner,
            Observer {
                homeAdapter.submitList(it)
            }
        )

        binding.searchPokemon.setOnQueryTextListener(object:OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchPokemon(newText!!)
                return true
            }
        })

        var isClicked = false

        binding.favorites.setOnClickListener {
            if(!isClicked){
                viewModel.getFavorites()
                isClicked = true
            }else{
                viewModel.getAllPokemon()
                isClicked = false
            }
        }
    }

    override fun getPokemonByType(type: String) {
        viewModel.getPokemonByType(type)
    }
}