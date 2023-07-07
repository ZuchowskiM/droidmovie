package com.mzuch.droidmovie.moviedetails.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.mzuch.droidmovie.databinding.FragmentMovieDetailsBinding

class MovieDetailsFragment : Fragment() {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: MovieDetailsFragmentArgs by navArgs()

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
            tbMovieDetails.tvToolbarTitle.text = "Overview"
            tbMovieDetails.ibtnBack.isVisible = true
            tbMovieDetails.ibtnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            tbMovieDetails.btnFavorite.isVisible = true
            tbMovieDetails.btnFavorite.isChecked = args.movieDetailsArgs.isFavorite
        }
    }

    private companion object {
        const val postersUrl = "https://image.tmdb.org/t/p/original"
    }
}