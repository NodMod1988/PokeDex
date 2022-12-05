package com.syntax.pokedex.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.syntax.pokedex.ApiStatus
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
        viewModel.loadPokeList()
        viewModel.loadPokeTypes()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var max = 0


        viewModel.maxCount.observe(
            viewLifecycleOwner,
            Observer {
                println("in maxcount observer")
            }
        )

        viewModel.counter.observe(
            viewLifecycleOwner,
            Observer {
                binding.loadingProgress.text = it.toString() + "/" + max.toString()
            }
        )


        viewModel.loading.observe(
            viewLifecycleOwner,
            Observer {
                if(it == ApiStatus.DONE){
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
                }
            }
        )

    }

}