package com.catnip.goplaytmdb.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.catnip.goplaytmdb.databinding.ItemMoviePosterBinding
import com.catnip.goplaytmdb.databinding.ItemMoviePosterGridBinding
import com.catnip.goplaytmdb.domain.viewparam.MovieViewParam

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class MovieAdapter(
    private val isGridLayout: Boolean = false,
    private val itemClick: (MovieViewParam) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: MutableList<MovieViewParam> = mutableListOf()

    fun setItems(items: List<MovieViewParam>) {
        clearItems()
        addItems(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<MovieViewParam>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (!isGridLayout) {
            PosterViewHolderImpl(
                ItemMoviePosterBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                itemClick
            )
        } else {
            GridPosterViewHolderImpl(
                ItemMoviePosterGridBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                itemClick
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PosterViewHolder) {
            holder.bindView(items[position])
        }
    }

    override fun getItemCount(): Int = items.size


    interface PosterViewHolder {
        fun bindView(item: MovieViewParam)
    }

    class PosterViewHolderImpl(
        private val binding: ItemMoviePosterBinding,
        val itemClick: (MovieViewParam) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), PosterViewHolder {

        override fun bindView(item: MovieViewParam) {
            with(item) {
                binding.ivPoster.load(item.posterPath)
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }

    class GridPosterViewHolderImpl(
        private val binding: ItemMoviePosterGridBinding,
        val itemClick: (MovieViewParam) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), PosterViewHolder {

        override fun bindView(item: MovieViewParam) {
            with(item) {
                binding.ivPoster.load(item.posterPath)
                itemView.setOnClickListener { itemClick(this) }
            }

        }
    }

}