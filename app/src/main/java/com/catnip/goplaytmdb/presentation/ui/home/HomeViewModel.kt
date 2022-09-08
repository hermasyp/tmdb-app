package com.catnip.goplaytmdb.presentation.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.goplaytmdb.R
import com.catnip.goplaytmdb.core.wrapper.ViewResource
import com.catnip.goplaytmdb.domain.usecases.GetHeaderDataUseCase
import com.catnip.goplaytmdb.domain.usecases.GetSectionDataUseCase
import com.catnip.goplaytmdb.presentation.model.HomeUiModel
import com.catnip.goplaytmdb.presentation.model.HomeViewType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class HomeViewModel(
    private val sectionDataUseCase: GetSectionDataUseCase,
    private val headerDataUseCase: GetHeaderDataUseCase
) : ViewModel() {

    val homeLiveData = MutableLiveData<ViewResource<Pair<String, HomeUiModel>>>()

    fun fetchHomeData() {
        viewModelScope.launch {
            merge(
                headerDataUseCase(),
                sectionDataUseCase(
                    GetSectionDataUseCase.Param(
                        HomeViewType.SECTION_NOW_PLAYING,
                        R.string.title_section_now_playing
                    )
                ), sectionDataUseCase(
                    GetSectionDataUseCase.Param(
                        HomeViewType.SECTION_UPCOMING,
                        R.string.title_section_upcoming
                    )
                ), sectionDataUseCase(
                    GetSectionDataUseCase.Param(
                        HomeViewType.SECTION_TOP_RATED,
                        R.string.title_section_top_rated
                    )
                ), sectionDataUseCase(
                    GetSectionDataUseCase.Param(
                        HomeViewType.SECTION_POPULAR,
                        R.string.title_section_popular
                    )
                )
            ).onEach { delay(100) }.collect {
                homeLiveData.postValue(it)
            }
        }
    }
}