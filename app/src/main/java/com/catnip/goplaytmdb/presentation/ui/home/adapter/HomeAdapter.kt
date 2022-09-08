package com.catnip.goplaytmdb.presentation.ui.home.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.catnip.goplaytmdb.R
import com.catnip.goplaytmdb.presentation.model.HomeUiModel
import com.catnip.goplaytmdb.presentation.model.HomeViewType
import kotlin.math.log

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class HomeAdapter(
    val headerClickListener: HomeHeaderViewHolder.HomeHeaderClickListener,
    val homeSectionClickListener: HomeSectionViewHolder.HomeSectionClickListener,
    val recycledViewPool: RecyclerView.RecycledViewPool
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = hashMapOf(
        HomeViewType.HEADER to HomeUiModel.HeaderSectionUiModel(isLoading = true),
        HomeViewType.SECTION_NOW_PLAYING to HomeUiModel.MovieSectionUIModel(
            sectionNameRes = R.string.title_section_now_playing,
            isLoading = true
        ),
        HomeViewType.SECTION_POPULAR to HomeUiModel.MovieSectionUIModel(
            sectionNameRes = R.string.title_section_popular,
            isLoading = true
        ),
        HomeViewType.SECTION_UPCOMING to HomeUiModel.MovieSectionUIModel(
            sectionNameRes = R.string.title_section_upcoming,
            isLoading = true
        ),
        HomeViewType.SECTION_TOP_RATED to HomeUiModel.MovieSectionUIModel(
            sectionNameRes = R.string.title_section_top_rated,
            isLoading = true
        )
    )

    fun addOrReplaceItem(pairItem: Pair<String, HomeUiModel>) {
        Log.d(this.javaClass.simpleName, "addOrReplaceItem: ${pairItem.first} ${pairItem.second.isLoading} ${pairItem.second.error}")
        if (items.containsKey(pairItem.first)) {
            items[pairItem.first] = pairItem.second
            notifyItemChanged(items.toList().indexOf(pairItem))
        } else {
            items[pairItem.first] = pairItem.second
            notifyItemInserted(items.toList().indexOf(pairItem))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_header_home -> HomeHeaderViewHolder.getInstance(
                parent,
                headerClickListener
            )
            else -> HomeSectionViewHolder.getInstance(
                parent,
                homeSectionClickListener,
                recycledViewPool
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items.toList()[position].second
        if (holder is HomeHeaderViewHolder) {
            holder.bindView((item as HomeUiModel.HeaderSectionUiModel))
        } else if (holder is HomeSectionViewHolder) {
            holder.bindView((item as HomeUiModel.MovieSectionUIModel))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items.toList()[position].second) {
            is HomeUiModel.HeaderSectionUiModel -> R.layout.item_header_home
            is HomeUiModel.MovieSectionUIModel -> R.layout.item_section_movie
            null -> throw IllegalStateException("Unknown view")
        }
    }

    override fun getItemCount(): Int = items.size
}