package com.catnip.goplaytmdb.presentation.ui.movieinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.catnip.goplaytmdb.databinding.BottomSheetMovieInfoBinding
import com.catnip.goplaytmdb.domain.viewparam.MovieViewParam
import com.catnip.goplaytmdb.utils.CommonUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class MovieInfoBottomSheetDialog(private val movie: MovieViewParam) : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetMovieInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetMovieInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindMovie(movie)
        observeData()
    }


    private fun observeData() {
        //todo : observe favorite status
    }

    private fun bindMovie(movie: MovieViewParam) {
        setClickListener(movie)
        loadPoster(movie)
        loadInfoMovie(movie)
    }

    private fun loadInfoMovie(movie: MovieViewParam) {
        binding.tvMovieTitle.text = movie.title
        binding.tvShortDesc.text = movie.overview
        binding.tvAdditionalInfo.text =
            "${movie.releaseDate} • ${movie.voteAverage}★"
        binding.ivWatchlist.setImageResource(CommonUtils.getWatchlistIcon(movie.isFavorited))

    }

    private fun loadPoster(movie: MovieViewParam) {
        binding.ivHeaderMovie.load(movie.getFullBackdropPath())
        binding.ivPoster.load(movie.getFullPosterPath())
    }

    private fun setClickListener(movie: MovieViewParam) {
        binding.llShare.setOnClickListener {
            CommonUtils.shareFilm(requireContext(), movie)
        }
        binding.llMyList.setOnClickListener {
            //viewModel.addOrRemoveWatchlist(movie.isUserWatchlist, movie.id.toString())
            //todo : add or remove watchlist
        }
    }
}