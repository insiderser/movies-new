package com.insiderser.popularmovies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import com.insiderser.popularmovies.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.Insetter
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
        Insetter.builder()
            .setOnApplyInsetsListener { view, insets, _ ->
                view.updateLayoutParams {
                    height = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
                }
            }
            .applyToView(binding.statusBarScrim)
    }
}
