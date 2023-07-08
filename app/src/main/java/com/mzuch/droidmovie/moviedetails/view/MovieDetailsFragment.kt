package com.mzuch.droidmovie.moviedetails.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.mzuch.droidmovie.databinding.FragmentMovieDetailsBinding
import com.mzuch.droidmovie.moviedetails.intent.MovieDetailsIntent
import com.mzuch.droidmovie.moviedetails.viewmodel.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: MovieDetailsFragmentArgs by navArgs()
    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupToolbar()
        setupFavoriteButton()
    }

    private fun setupView() {
        binding.run {
            posterIv.load("$postersUrl${args.movieDetailsArgs.posterPath}") {
                error(com.google.android.material.R.drawable.mtrl_ic_error)
            }
            movieTitleTv.text = args.movieDetailsArgs.title
            releaseDateTv.text = args.movieDetailsArgs.releaseDate
            scoreTv.text = args.movieDetailsArgs.score
            overviewTv.text = args.movieDetailsArgs.overview
        }
    }

    private fun setupToolbar() {
        binding.run {
            tbMovieDetails.tvToolbarTitle.text = "Overview"
            tbMovieDetails.ibtnBack.isVisible = true
            tbMovieDetails.ibtnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun setupFavoriteButton() {
        binding.run {
            tbMovieDetails.btnFavorite.isVisible = true
            tbMovieDetails.btnFavorite.isChecked = args.movieDetailsArgs.isFavorite
            tbMovieDetails.btnFavorite.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    lifecycleScope.launch {
                        viewModel
                            .moviesDetailsIntent
                            .send(MovieDetailsIntent.MarkAsFavorite(args.movieDetailsArgs.uid))
                    }
                } else {
                    lifecycleScope.launch {
                        viewModel
                            .moviesDetailsIntent
                            .send(MovieDetailsIntent.UnMarkAsFavorite(args.movieDetailsArgs.uid))
                    }
                }
            }
        }
    }

    private companion object {
        const val postersUrl = "https://image.tmdb.org/t/p/original"
    }
}