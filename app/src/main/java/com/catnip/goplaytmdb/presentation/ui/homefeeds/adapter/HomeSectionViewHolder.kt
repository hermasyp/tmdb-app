package com.catnip.goplaytmdb.presentation.ui.homefeeds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.catnip.goplaytmdb.databinding.ItemSectionMovieBinding
import com.catnip.goplaytmdb.domain.viewparam.MovieViewParam
import com.catnip.goplaytmdb.presentation.common.adapter.MovieAdapter
import com.catnip.goplaytmdb.presentation.model.HomeUiModel

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class HomeSectionViewHolder(
    private val binding: ItemSectionMovieBinding,
    private val listener: HomeSectionClickListener,
    private val recyclerViewPool: RecyclerView.RecycledViewPool
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun getInstance(
            parent: ViewGroup,
            listener: HomeSectionClickListener,
            recyclerViewPool: RecyclerView.RecycledViewPool
        ): HomeSectionViewHolder {
            return HomeSectionViewHolder(
                ItemSectionMovieBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), listener, recyclerViewPool
            )
        }
    }

    private val movieAdapter: MovieAdapter by lazy {
        MovieAdapter {
            listener.onPosterClicked(it)
        }
    }

    fun bindView(item: HomeUiModel.MovieSectionUIModel) {
        if (item.isLoading) {
            //showLoading
            binding.shimmerSection.startShimmer()
            binding.shimmerSection.isVisible = true
            if(!item.movies.isNullOrEmpty()){
                showMovie(item)
            }
        } else if (item.error != null) {
            binding.shimmerSection.stopShimmer()
            binding.shimmerSection.isVisible = false
            if(!item.movies.isNullOrEmpty()){
                showMovie(item)
            }
        } else {
            showMovie(item)
        }
    }
    private fun showMovie(item: HomeUiModel.MovieSectionUIModel ){
        binding.shimmerSection.isVisible = false
        binding.clSectionLayout.isVisible = true
        binding.shimmerSection.stopShimmer()
        bindMovie(item)
    }

    private fun bindMovie(item: HomeUiModel.MovieSectionUIModel) {
        item.movies?.let {
            binding.tvTitleSection.text = itemView.context.getString(item.sectionNameRes)
            binding.ivMore.setOnClickListener {
                listener.onShowMoreClicked(item)
            }
            binding.rvContents.apply {
                setRecycledViewPool(recyclerViewPool)
                adapter = movieAdapter
                layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            }
            movieAdapter.clearItems()
            movieAdapter.setItems(it)
        }
    }

    interface HomeSectionClickListener {
        fun onPosterClicked(movieViewParam: MovieViewParam)
        fun onShowMoreClicked(section: HomeUiModel.MovieSectionUIModel)
    }
}