package com.catnip.goplaytmdb.presentation.ui.mylist

import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.catnip.goplaytmdb.R
import com.catnip.goplaytmdb.core.base.BaseFragment
import com.catnip.goplaytmdb.databinding.FragmentMyListBinding
import com.catnip.goplaytmdb.presentation.ui.homefeeds.adapter.MovieAdapter
import com.catnip.goplaytmdb.presentation.ui.movieinfo.MovieInfoBottomSheetDialog
import com.catnip.goplaytmdb.utils.ext.subscribe
import org.koin.android.ext.android.inject

class MyListFragment :
    BaseFragment<FragmentMyListBinding, MyListViewModel>(FragmentMyListBinding::inflate) {
    override val viewModel: MyListViewModel by inject()

    private val movieAdapter: MovieAdapter by lazy {
        MovieAdapter(true) {
            MovieInfoBottomSheetDialog(it) { isFavorited ->
                if (!isFavorited) {
                    viewModel.getFavoritedList()
                }
            }.show(childFragmentManager, null)
        }
    }

    override fun initView() {
        setupList()
        viewModel.getFavoritedList()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden){
            viewModel.getFavoritedList()
        }
    }

    private fun setupList() {
        binding.rvMovies.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }
    }

    override fun observeData() {
        super.observeData()
        viewModel.favoritedListResult.observe(this) {
            it.subscribe(
                doOnSuccess = { result ->
                    binding.pbMovies.isVisible = false
                    binding.rvMovies.isVisible = true
                    binding.tVErrorMovies.isVisible = false
                    result.payload?.let { movies ->
                        if (movies.isNotEmpty()) {
                            movieAdapter.clearItems()
                            movieAdapter.setItems(movies)
                        }else{
                            movieAdapter.clearItems()
                            binding.rvMovies.isVisible = false
                            binding.tVErrorMovies.isVisible = true
                            binding.tVErrorMovies.text = requireContext().getString(R.string.text_movie_list_empty)
                        }
                    }
                }, doOnError = {
                    binding.pbMovies.isVisible = false
                    binding.rvMovies.isVisible = false
                    binding.tVErrorMovies.isVisible = true
                }, doOnLoading = {
                    binding.pbMovies.isVisible = true
                    binding.rvMovies.isVisible = false
                    binding.tVErrorMovies.isVisible = false
                })
        }
    }

}