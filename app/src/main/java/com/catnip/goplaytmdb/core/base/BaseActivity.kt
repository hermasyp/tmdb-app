package com.catnip.goplaytmdb.core.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.catnip.goplaytmdb.R
import com.catnip.goplaytmdb.utils.ext.getErrorMessage
import com.google.android.material.snackbar.Snackbar

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
abstract class BaseActivity<B : ViewBinding, VM : ViewModel>(
    val bindingFactory: (LayoutInflater) -> B
) : AppCompatActivity() {

    protected lateinit var binding: B
    protected abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
        initView()
        observeData()
    }

    abstract fun initView()

    open fun observeData() {

    }

    open fun showError(isErrorEnabled: Boolean, exception: Exception) {
        if (isErrorEnabled) {
            Toast.makeText(this,getErrorMessage(exception), Toast.LENGTH_SHORT).show()
        }
    }

    fun enableHomeAsBack() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun showOnDevelopmentToast(){
        Toast.makeText(this, "Still on Development", Toast.LENGTH_SHORT).show()
    }
    fun showSnackbarNoInternet(container : View){
        val snackbar = Snackbar.make(
            container,
            getString(R.string.text_no_internet),
            Snackbar.LENGTH_INDEFINITE
        ).setTextColor(Color.WHITE)
        snackbar.setAction(getString(R.string.text_snackbar_dismiss)) {
            snackbar.dismiss()
        }.setActionTextColor(Color.WHITE).show()
    }
}