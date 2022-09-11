package com.catnip.goplaytmdb.presentation.ui.movielist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.catnip.goplaytmdb.databinding.ItemMoviePosterGridBinding
import com.catnip.goplaytmdb.domain.viewparam.MovieViewParam
import com.catnip.goplaytmdb.presentation.common.adapter.viewholder.GridPosterViewHolderImpl

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class PagedMoviesAdapter(private val itemClick: (MovieViewParam) -> Unit) :
    PagingDataAdapter<MovieViewParam, GridPosterViewHolderImpl>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<MovieViewParam>() {
            override fun areItemsTheSame(
                oldItem: MovieViewParam,
                newItem: MovieViewParam
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: MovieViewParam,
                newItem: MovieViewParam
            ): Boolean = oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: GridPosterViewHolderImpl, position: Int) {
        val item = getItem(position)
        holder.bindView(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridPosterViewHolderImpl {
        return GridPosterViewHolderImpl(
            ItemMoviePosterGridBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            ), itemClick
        )
    }


}