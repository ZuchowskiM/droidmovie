package com.mzuch.droidmovie.movies.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mzuch.droidmovie.data.movies.model.Results
import com.mzuch.droidmovie.databinding.FragmentMoviesBinding

class MoviesFragment : Fragment() {
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMovieList()
    }

    private fun setupMovieList() {
        val mov1 = Results().apply {
            id = 1
            title = "siem"
        }
        val mov2 = Results().apply {
            id = 2
            title = "elo420"
        }
        val adapter = MovieAdapter().apply {
            submitList(
                listOf(
                    mov1,
                    mov2,
                )
            )
        }
        binding.moviesRv.adapter = adapter
    }
}