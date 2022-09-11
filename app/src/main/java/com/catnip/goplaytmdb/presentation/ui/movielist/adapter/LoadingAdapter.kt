package com.catnip.goplaytmdb.presentation.ui.movielist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.catnip.goplaytmdb.R
import com.catnip.goplaytmdb.core.exception.ApiErrorException
import com.catnip.goplaytmdb.core.exception.NoInternetConnectionException
import com.catnip.goplaytmdb.databinding.ItemLoadingBinding

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class LoadingAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadingAdapter.LoaderViewHolder>() {

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        return LoaderViewHolder.getInstance(parent, retry)
    }

    class LoaderViewHolder(private val binding: ItemLoadingBinding, private val retry: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun getInstance(parent: ViewGroup, retry: () -> Unit): LoaderViewHolder {
                return LoaderViewHolder(
                    ItemLoadingBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), retry
                )
            }
        }


        fun bind(loadState: LoadState) {
            with(binding) {
                if (loadState is LoadState.Error) {
                    ivError.setImageResource(
                        when (loadState.error) {
                            is NoInternetConnectionException -> R.drawable.ic_error_no_connection
                            is ApiErrorException -> R.drawable.ic_error_general
                            else -> R.drawable.ic_error_unknown
                        }
                    )
                }
                ivError.isVisible = loadState is LoadState.Error
                pbLoading.isVisible = loadState is LoadState.Loading
                btnRetry.isVisible = loadState is LoadState.Error
                btnRetry.setOnClickListener {
                    retry.invoke()
                }
            }
        }
    }
}