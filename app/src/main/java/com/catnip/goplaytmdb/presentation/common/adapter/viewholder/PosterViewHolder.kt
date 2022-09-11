package com.catnip.goplaytmdb.presentation.common.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.catnip.goplaytmdb.databinding.ItemMoviePosterBinding
import com.catnip.goplaytmdb.databinding.ItemMoviePosterGridBinding
import com.catnip.goplaytmdb.domain.viewparam.MovieViewParam

interface PosterViewHolder {
    fun bindView(item: MovieViewParam?)
}

class PosterViewHolderImpl(
    private val binding: ItemMoviePosterBinding,
    val itemClick: (MovieViewParam) -> Unit
) : RecyclerView.ViewHolder(binding.root), PosterViewHolder {

    override fun bindView(item: MovieViewParam?) {
        item?.let { movie ->
            binding.ivPoster.load(movie.getFullPosterPath())
            itemView.setOnClickListener { itemClick(movie) }
        }
    }
}

class GridPosterViewHolderImpl(
    private val binding: ItemMoviePosterGridBinding,
    val itemClick: (MovieViewParam) -> Unit
) : RecyclerView.ViewHolder(binding.root), PosterViewHolder {

    override fun bindView(item: MovieViewParam?) {
        item?.let { movie ->
            binding.ivPoster.load(movie.getFullPosterPath())
            itemView.setOnClickListener { itemClick(movie) }
        }
    }
}
