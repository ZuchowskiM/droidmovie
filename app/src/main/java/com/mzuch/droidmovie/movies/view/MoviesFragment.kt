package com.mzuch.droidmovie.movies.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mzuch.droidmovie.databinding.FragmentMoviesBinding
import com.mzuch.droidmovie.movies.intent.MoviesIntent
import com.mzuch.droidmovie.movies.viewmodel.MoviesViewModel
import com.mzuch.droidmovie.movies.viewstate.MoviesState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MoviesViewModel by viewModels()
    private val adapter = MovieAdapter()

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
        observeViewModel()
        setupMovieList()
        fetchMovies()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.moviesState.collect {
                when (it) {
                    MoviesState.Error -> { }
                    MoviesState.Idle -> { }
                    MoviesState.Loading -> { }
                    is MoviesState.Success -> {
                        adapter.submitList(it.data)
                    }
                }
            }
        }
    }

    private fun fetchMovies() {
        lifecycleScope.launch {
            viewModel.moviesIntent.send(MoviesIntent.FetchData)
        }
    }

    private fun setupMovieList() {
        binding.moviesRv.adapter = adapter
    }
}