package com.insiderser.popularmovies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.insiderser.popularmovies.databinding.ActivityMainBinding
import com.insiderser.popularmovies.util.applyStatusBarHeightToHeight
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import dev.chrisbanes.insetter.setEdgeToEdgeSystemUiFlags

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.setEdgeToEdgeSystemUiFlags()
        binding.root.applySystemWindowInsetsToPadding(left = true, right = true)
        binding.statusBarScrim.applyStatusBarHeightToHeight()
    }
}
