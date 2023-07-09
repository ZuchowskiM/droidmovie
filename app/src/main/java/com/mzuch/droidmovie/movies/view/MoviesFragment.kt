package com.mzuch.droidmovie.movies.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.mzuch.droidmovie.R
import com.mzuch.droidmovie.databinding.FragmentMoviesBinding
import com.mzuch.droidmovie.movies.intent.MoviesIntent
import com.mzuch.droidmovie.movies.viewmodel.MoviesViewModel
import com.mzuch.droidmovie.movies.viewstate.MoviesState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MoviesViewModel by viewModels()
    private val pagingAdapter =
        MoviePagingDataAdapter(
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
        observeMovies()
        observeLoadingState()
        observeViewModel()
    }

    private fun setupToolbar() {
        binding.tbMovieList.tvToolbarTitle.setText(R.string.movies)
    }

    private fun setupMovieList() {
        binding.moviesRv.adapter = pagingAdapter.withLoadStateFooter(
            footer = MovieLoadStateAdapter { pagingAdapter.retry() },
        )
    }

    private fun observeLoadingState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                pagingAdapter.loadStateFlow.collectLatest {
                    if (it.refresh is LoadState.Error) {
                        viewModel.moviesIntent.send(MoviesIntent.RefreshError)
                    }
                    binding.stfLayMovies.isRefreshing = it.refresh is LoadState.Loading
                }
            }
        }
    }

    private fun observeMovies() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.moviesPagedFlow.collect {
                    pagingAdapter.submitData(it)
                }
            }
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.moviesEvents.asSharedFlow().collect {
                    when (it) {
                        is MoviesState.LoadError -> {
                            Toast.makeText(
                                requireContext(),
                                R.string.movie_load_error,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        is MoviesState.Idle -> {}
                    }
                }
            }
        }
    }

    private fun setupSwipeToRefreshLayout() {
        binding.stfLayMovies.setOnRefreshListener {
            onMoviesRefresh()
        }
    }

    private fun onMoviesRefresh() {
        viewLifecycleOwner.lifecycleScope.launch {
            pagingAdapter.refresh()
        }
    }
}