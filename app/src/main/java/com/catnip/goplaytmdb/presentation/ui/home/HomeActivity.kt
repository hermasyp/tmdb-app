package com.catnip.goplaytmdb.presentation.ui.home

import androidx.fragment.app.Fragment
import com.catnip.goplaytmdb.R
import com.catnip.goplaytmdb.core.base.BaseActivity
import com.catnip.goplaytmdb.databinding.ActivityHomeBinding
import com.catnip.goplaytmdb.presentation.ui.homefeeds.HomeFeedsFragment
import com.catnip.goplaytmdb.presentation.ui.mylist.MyListFragment
import org.koin.android.ext.android.inject

class HomeActivity : BaseActivity<ActivityHomeBinding,HomeViewModel>(ActivityHomeBinding::inflate) {
    override val viewModel: HomeViewModel by inject()


    private val homeFeedsFragment = HomeFeedsFragment()
    private val myListFragment = MyListFragment()
    private var activeFragment: Fragment = homeFeedsFragment

    override fun initView() {
        setupFragment()
    }
    private fun setupFragment() {
        // delete all fragment in fragment manager first
        for (fragment in supportFragmentManager.fragments) {
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
        // add fragment to fragment manager
        supportFragmentManager.beginTransaction().apply {
            add(binding.container.id, homeFeedsFragment)
            add(binding.container.id, myListFragment)
            hide(myListFragment)
        }.commit()
        // set click menu for changing fragment
        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    showFragment(homeFeedsFragment)
                    true
                }
                else -> {
                    showFragment(myListFragment)
                    true
                }
            }
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .hide(activeFragment)
            .show(fragment)
            .commit()
        activeFragment = fragment
    }
}