package com.catnip.goplaytmdb.presentation.ui.movielist

import android.content.Context
import android.content.Intent
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.catnip.goplaytmdb.core.base.BaseActivity
import com.catnip.goplaytmdb.databinding.ActivityMovieSectionListBinding
import com.catnip.goplaytmdb.presentation.ui.movieinfo.MovieInfoBottomSheetDialog
import com.catnip.goplaytmdb.presentation.ui.movielist.adapter.LoadingAdapter
import com.catnip.goplaytmdb.presentation.ui.movielist.adapter.PagedMoviesAdapter
import com.catnip.goplaytmdb.utils.ext.getErrorMessage
import com.catnip.goplaytmdb.utils.ext.withLoadStateAdapters
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MovieSectionListActivity :
    BaseActivity<ActivityMovieSectionListBinding, MovieSectionListViewModel>(
        ActivityMovieSectionListBinding::inflate
    ) {
    override val viewModel: MovieSectionListViewModel by inject()

    private val sectionName: String by lazy {
        intent.getStringExtra(EXTRAS_SECTION_NAME).orEmpty()
    }
    private val sectionType: String by lazy {
        intent.getStringExtra(EXTRAS_SECTION_TYPE).orEmpty()
    }
    private val pagedMoviesAdapter: PagedMoviesAdapter by lazy {
        PagedMoviesAdapter {
            MovieInfoBottomSheetDialog(it).show(supportFragmentManager, null)
        }
    }

    override fun initView() {
        setupToolbar()
        setupList()
        fetchMovies()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = sectionName
        enableHomeAsBack()
    }

    private fun fetchMovies() {
        lifecycleScope.launch {
            pagedMoviesAdapter.submitData(PagingData.empty())
            viewModel.getMoviesBySection(sectionType).distinctUntilChanged().collectLatest {
                pagedMoviesAdapter.submitData(it)
            }
        }

    }

    private fun setupList() {
        val loadingAdapterHeader = LoadingAdapter {
            pagedMoviesAdapter.retry()
        }
        val loadingAdapterFooter = LoadingAdapter {
            pagedMoviesAdapter.retry()
        }
        binding.rvMovies.apply {
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            adapter = pagedMoviesAdapter.withLoadStateAdapters(
                loadingAdapterHeader, loadingAdapterFooter
            )
        }
        pagedMoviesAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Error && pagedMoviesAdapter.itemCount < 1) {
                val error = loadState.refresh as LoadState.Error
                handleFirstLoadError(getErrorMessage(error.error as Exception))
            } else {
                binding.rvMovies.isVisible = true
                binding.groupErrorLayout.isVisible = false
            }
        }

    }

    private fun handleFirstLoadError(errorMsg: String) {
        binding.groupErrorLayout.isVisible = true
        binding.tvErrorMovies.text = errorMsg
        binding.rvMovies.isVisible = false
        binding.btnRetryPage.setOnClickListener {
            pagedMoviesAdapter.retry()
        }
    }

    companion object {
        private const val EXTRAS_SECTION_NAME = "EXTRAS_SECTION_NAME"
        private const val EXTRAS_SECTION_TYPE = "EXTRAS_SECTION_TYPE"

        @JvmStatic
        fun startActivity(context: Context?, sectionName: String, sectionType: String) {
            context?.startActivity(Intent(context, MovieSectionListActivity::class.java).apply {
                putExtra(EXTRAS_SECTION_NAME, sectionName)
                putExtra(EXTRAS_SECTION_TYPE, sectionType)
            })
        }
    }

}