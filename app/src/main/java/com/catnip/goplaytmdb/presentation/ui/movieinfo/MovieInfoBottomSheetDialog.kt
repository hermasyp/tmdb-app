package com.catnip.goplaytmdb.presentation.ui.movieinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import coil.load
import com.catnip.goplaytmdb.databinding.BottomSheetMovieInfoBinding
import com.catnip.goplaytmdb.domain.viewparam.MovieViewParam
import com.catnip.goplaytmdb.utils.CommonUtils
import com.catnip.goplaytmdb.utils.ext.subscribe
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class MovieInfoBottomSheetDialog(
    private val movie: MovieViewParam,
    private val favoriteActionCallback: ((Boolean) -> Unit)? = null
) : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetMovieInfoBinding
    private val viewModel: MovieInfoViewModel by inject()

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
        viewModel.checkIsMovieFavorited(movie)
    }


    private fun observeData() {
        viewModel.getFavoriteMovieResult().observe(this) {
            it.subscribe(
                doOnSuccess = { result ->
                    binding.pbLoadingWatchlist.isVisible = false
                    binding.ivWatchlist.isVisible = true
                    movie.isFavorited = (result.payload == true)
                    bindMovie(movie)
                    favoriteActionCallback?.invoke(movie.isFavorited)
                },
                doOnError = {
                    binding.pbLoadingWatchlist.isVisible = false
                    binding.ivWatchlist.isVisible = true
                },
                doOnLoading = {
                    binding.pbLoadingWatchlist.isVisible = true
                    binding.ivWatchlist.isVisible = false
                })
        }
        viewModel.isMovieFavoritedResult.observe(this) {
            it.subscribe(
                doOnSuccess = { result ->
                    movie.isFavorited = (result.payload == true)
                    bindMovie(movie)
                })
        }
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
            viewModel.addOrRemoveWatchlist(movie)
        }
    }
}