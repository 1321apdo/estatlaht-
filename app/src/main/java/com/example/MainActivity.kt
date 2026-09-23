package com.example

import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.data.database.AppDatabase
import com.example.data.repository.RewardsRepository
import com.example.ui.screens.MainRewardsApp
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.RewardsViewModel
import com.example.ui.viewmodel.RewardsViewModelFactory
import com.example.util.AdMobManager
import com.google.android.gms.ads.MobileAds
import java.io.File

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    // Configure safe WebView cache directory to prevent chromium simple_version_upgrade / index errors
    try {
      val webViewDir = File(cacheDir, "webview_cache")
      if (!webViewDir.exists()) {
        webViewDir.mkdirs()
      }
    } catch (_: Exception) {
    }

    // 1. Initialize Google Mobile Ads SDK safely via AdMobManager
    try {
      AdMobManager.initialize(this)
    } catch (_: Exception) {
      // Graceful fallback in environments without full Google Play Services
    }

    // 2. Initialize Room Database and Repository
    val database = AppDatabase.getDatabase(this)
    val repository = RewardsRepository(database.taskDao())

    // 3. Instantiate ViewModel using modern ViewModelProvider.Factory
    val viewModel = ViewModelProvider(
        this, 
        RewardsViewModelFactory(repository)
    )[RewardsViewModel::class.java]

    // 4. Set Content using Compose and our themed application
    setContent {
      MyApplicationTheme {
        MainRewardsApp(viewModel = viewModel)
      }
    }
  }
}
