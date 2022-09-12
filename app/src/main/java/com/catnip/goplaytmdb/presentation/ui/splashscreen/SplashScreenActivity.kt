package com.catnip.goplaytmdb.presentation.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.catnip.goplaytmdb.R
import com.catnip.goplaytmdb.presentation.ui.home.HomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        lifecycleScope.launch(Dispatchers.IO) {
            delay(1000)
            lifecycleScope.launch(Dispatchers.Main){
                navigateToHome()
            }
        }
    }

    private fun navigateToHome() {
        startActivity(Intent(this,HomeActivity::class.java))
    }
}