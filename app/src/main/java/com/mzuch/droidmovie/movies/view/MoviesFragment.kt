package com.mzuch.droidmovie.movies.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
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
    private val adapter =
        MovieAdapter(
            onClick = {
                val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(it)
                findNavController().navigate(action)
            },
            markFavorite = {
                lifecycleScope.launch {
                    viewModel.moviesIntent.send(MoviesIntent.MarkAsFavorite(it))
                }
            },
            unMarkFavorite = {
                lifecycleScope.launch {
                    viewModel.moviesIntent.send(MoviesIntent.UnMarkAsFavorite(it))
                }
            }
        )

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
        setupToolbar()
        setupMovieList()
        setupSwipeToRefreshLayout()
        observeViewModel()
        fetchMovies()
    }

    private fun setupToolbar() {
        binding.tbMovieList.tvToolbarTitle.text = "Movies"
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.moviesState.collect {
                    when (it) {
                        is MoviesState.Error -> {
                            binding.stfLayMovies.isRefreshing = false
                        }
                        is MoviesState.Idle -> {}
                        is MoviesState.Success -> {
                            adapter.submitList(it.data)
                            binding.stfLayMovies.isRefreshing = false
                        }
                    }
                }
            }
        }
    }

    private fun setupMovieList() {
        binding.moviesRv.adapter = adapter
    }

    private fun setupSwipeToRefreshLayout() {
        binding.stfLayMovies.setOnRefreshListener {
            onMoviesRefresh()
        }
    }

    private fun fetchMovies() {
        binding.stfLayMovies.post {
            binding.stfLayMovies.isRefreshing = true
            onMoviesRefresh()
        }
    }

    private fun onMoviesRefresh() {
        lifecycleScope.launch {
            viewModel.moviesIntent.send(MoviesIntent.FetchData)
        }
    }
}