package com.catnip.goplaytmdb.presentation.ui.homefeeds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
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
            binding.shimmerHeader.startShimmer()
            bindMovie(item.movie)
        } else if (item.error != null) {
            binding.shimmerHeader.stopShimmer()
            bindMovie(item.movie)
        } else {
            binding.shimmerHeader.stopShimmer()
            bindMovie(item.movie)
        }

    }
    private fun bindMovie(movie : MovieViewParam?){
        movie?.let {
            binding.ivHeaderMovie.load(movie.posterPath)
            binding.tvTitleMovie.text = movie.title
            binding.tvInfoHeader.setOnClickListener {
                headerClickListener.onInfoClicked(movie)
            }
            binding.tvAddToWatchlistHeader.setOnClickListener {
                headerClickListener.onMyListClicked(movie)
            }
            binding.cvPlayHeader.setOnClickListener {
                headerClickListener.onPlayMovieClicked(movie)
            }
        }
    }

    interface HomeHeaderClickListener {
        fun onMyListClicked(movieViewParam: MovieViewParam)
        fun onPlayMovieClicked(movieViewParam: MovieViewParam)
        fun onInfoClicked(movieViewParam: MovieViewParam)
    }
}