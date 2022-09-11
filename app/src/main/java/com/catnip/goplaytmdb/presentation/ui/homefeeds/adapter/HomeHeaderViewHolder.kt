package com.catnip.goplaytmdb.presentation.ui.homefeeds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.catnip.goplaytmdb.databinding.ItemHeaderHomeBinding
import com.catnip.goplaytmdb.domain.viewparam.MovieViewParam
import com.catnip.goplaytmdb.presentation.model.HomeUiModel

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class HomeHeaderViewHolder(
    private val binding: ItemHeaderHomeBinding,
    private val headerClickListener: HomeHeaderClickListener
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun getInstance(parent: ViewGroup,headerClickListener: HomeHeaderClickListener): HomeHeaderViewHolder {
            return HomeHeaderViewHolder(
                ItemHeaderHomeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),headerClickListener
            )
        }
    }

    fun bindView(item: HomeUiModel.HeaderSectionUiModel) {
        if (item.isLoading) {
            //showLoading
            binding.tvInfoHeader.isVisible = false
            binding.tvShare.isVisible = false
            binding.shimmerHeader.startShimmer()
            bindMovie(item.movie)
        } else if (item.error != null) {
            binding.tvInfoHeader.isVisible = false
            binding.tvShare.isVisible = false
            binding.shimmerHeader.stopShimmer()
            bindMovie(item.movie)
        } else {
            binding.tvInfoHeader.isVisible = true
            binding.tvShare.isVisible = true
            binding.shimmerHeader.stopShimmer()
            bindMovie(item.movie)
        }

    }
    private fun bindMovie(movie : MovieViewParam?){
        movie?.let {
            binding.tvGenreMovie.text = movie.overview
            binding.ivHeaderMovie.load(movie.getFullPosterPath())
            binding.tvTitleMovie.text = movie.title
            binding.tvInfoHeader.setOnClickListener {
                headerClickListener.onInfoClicked(movie)
            }
            binding.tvShare.setOnClickListener {
                headerClickListener.onShareClicked(movie)
            }

        }
    }

    interface HomeHeaderClickListener {
        fun onShareClicked(movieViewParam: MovieViewParam)
        fun onInfoClicked(movieViewParam: MovieViewParam)
    }
}