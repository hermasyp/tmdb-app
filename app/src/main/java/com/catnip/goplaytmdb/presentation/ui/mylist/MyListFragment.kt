package com.catnip.goplaytmdb.presentation.ui.mylist

import com.catnip.goplaytmdb.core.base.BaseFragment
import com.catnip.goplaytmdb.databinding.FragmentMyListBinding
import org.koin.android.ext.android.inject

class MyListFragment : BaseFragment<FragmentMyListBinding,MyListViewModel>(FragmentMyListBinding::inflate) {
    override val viewModel: MyListViewModel by inject()

    override fun initView() {

    }

}