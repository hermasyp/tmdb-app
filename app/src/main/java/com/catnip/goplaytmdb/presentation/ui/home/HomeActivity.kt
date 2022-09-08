package com.catnip.goplaytmdb.presentation.ui.home

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.catnip.goplaytmdb.core.base.BaseActivity
import com.catnip.goplaytmdb.databinding.ActivityHomeBinding
import com.catnip.goplaytmdb.domain.viewparam.MovieViewParam
import com.catnip.goplaytmdb.presentation.model.HomeUiModel
import com.catnip.goplaytmdb.presentation.ui.home.adapter.HomeAdapter
import com.catnip.goplaytmdb.presentation.ui.home.adapter.HomeHeaderViewHolder
import com.catnip.goplaytmdb.presentation.ui.home.adapter.HomeSectionViewHolder
import com.catnip.goplaytmdb.utils.ext.subscribe
import org.koin.android.ext.android.inject

class HomeActivity :
    BaseActivity<ActivityHomeBinding, HomeViewModel>(ActivityHomeBinding::inflate) {

    override val viewModel: HomeViewModel by inject()

    val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(headerClickListener = object : HomeHeaderViewHolder.HomeHeaderClickListener {
            override fun onMyListClicked(movieViewParam: MovieViewParam) {
                showOnDevelopmentToast()
            }

            override fun onPlayMovieClicked(movieViewParam: MovieViewParam) {
                showOnDevelopmentToast()
            }

            override fun onInfoClicked(movieViewParam: MovieViewParam) {
                showOnDevelopmentToast()
            }

        }, homeSectionClickListener = object : HomeSectionViewHolder.HomeSectionClickListener {
            override fun onPosterClicked(movieViewParam: MovieViewParam) {
                showOnDevelopmentToast()
            }

            override fun onShowMoreClicked(section: HomeUiModel.MovieSectionUIModel) {
                showOnDevelopmentToast()
            }

        }, recycledViewPool)
    }

    private val recycledViewPool: RecyclerView.RecycledViewPool by lazy {
        RecyclerView.RecycledViewPool()
    }


    override fun initView() {
        binding.rvHome.apply {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(this@HomeActivity)
            setRecycledViewPool(recycledViewPool)
        }
        initData()
    }

    override fun observeData() {
        super.observeData()
        viewModel.homeLiveData.observe(this) {
            it.subscribe(doOnSuccess = { result ->
                result.payload?.let { data -> homeAdapter.addOrReplaceItem(data) }
            }, doOnError = { result ->
                Log.d(HomeActivity::class.simpleName, "observeData: ${result.exception}")
                result.payload?.let { data -> homeAdapter.addOrReplaceItem(data) }
            }, doOnLoading = { result ->
                Log.d(HomeActivity::class.simpleName, "Loading")
                result.payload?.let { data -> homeAdapter.addOrReplaceItem(data) }
            })
        }
    }

    private fun initData() {
        viewModel.fetchHomeData()
    }

}