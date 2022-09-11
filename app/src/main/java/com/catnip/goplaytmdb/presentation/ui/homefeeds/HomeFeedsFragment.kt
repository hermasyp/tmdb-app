package com.catnip.goplaytmdb.presentation.ui.homefeeds

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.catnip.goplaytmdb.core.base.BaseFragment
import com.catnip.goplaytmdb.databinding.FragmentHomeBinding
import com.catnip.goplaytmdb.domain.viewparam.MovieViewParam
import com.catnip.goplaytmdb.presentation.model.HomeUiModel
import com.catnip.goplaytmdb.presentation.ui.homefeeds.adapter.HomeFeedsAdapter
import com.catnip.goplaytmdb.presentation.ui.homefeeds.adapter.HomeHeaderViewHolder
import com.catnip.goplaytmdb.presentation.ui.homefeeds.adapter.HomeSectionViewHolder
import com.catnip.goplaytmdb.presentation.ui.movieinfo.MovieInfoBottomSheetDialog
import com.catnip.goplaytmdb.presentation.ui.movielist.MovieSectionListActivity
import com.catnip.goplaytmdb.utils.CommonUtils
import com.catnip.goplaytmdb.utils.ext.subscribe
import org.koin.android.ext.android.inject

class HomeFeedsFragment :
    BaseFragment<FragmentHomeBinding, HomeFeedsViewModel>(FragmentHomeBinding::inflate) {

    override val viewModel: HomeFeedsViewModel by inject()

    private val homeAdapter: HomeFeedsAdapter by lazy {
        HomeFeedsAdapter(headerClickListener = object :
            HomeHeaderViewHolder.HomeHeaderClickListener {
            override fun onShareClicked(movieViewParam: MovieViewParam) {
                CommonUtils.shareFilm(requireContext(), movieViewParam)
            }

            override fun onInfoClicked(movieViewParam: MovieViewParam) {
                openMovieInfo(movieViewParam)
            }

        }, homeSectionClickListener = object : HomeSectionViewHolder.HomeSectionClickListener {
            override fun onPosterClicked(movieViewParam: MovieViewParam) {
                openMovieInfo(movieViewParam)
            }

            override fun onShowMoreClicked(section: HomeUiModel.MovieSectionUIModel) {
                MovieSectionListActivity.startActivity(
                    requireContext(),
                    getString(section.sectionNameRes),
                    section.sectionType
                )
            }

        }, recycledViewPool)
    }

    private val recycledViewPool: RecyclerView.RecycledViewPool by lazy {
        RecyclerView.RecycledViewPool()
    }


    override fun initView() {
        binding.rvHome.apply {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(requireContext())
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
                result.payload?.let { data -> homeAdapter.addOrReplaceItem(data) }
            }, doOnLoading = { result ->
                result.payload?.let { data -> homeAdapter.addOrReplaceItem(data) }
            })
        }
    }

    private fun handleFirstLoadError() {

    }

    private fun openMovieInfo(movieViewParam: MovieViewParam) {
        MovieInfoBottomSheetDialog(movieViewParam).show(childFragmentManager, null)
    }

    private fun initData() {
        viewModel.fetchHomeData()
    }

}