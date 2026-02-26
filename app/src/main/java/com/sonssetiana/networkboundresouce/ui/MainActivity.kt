package com.sonssetiana.networkboundresouce.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.sonssetiana.networkboundresouce.R
import com.sonssetiana.networkboundresouce.configs.Configs
import com.sonssetiana.networkboundresouce.data.model.Resource
import com.sonssetiana.networkboundresouce.databinding.ActivityMainBinding
import com.sonssetiana.networkboundresouce.ui.adapter.MovieAdapter
import com.sonssetiana.networkboundresouce.utils.GridSpacingItemDecoration
import com.sonssetiana.networkboundresouce.utils.gone
import com.sonssetiana.networkboundresouce.utils.visible
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()

    private val movieAdapter by lazy { MovieAdapter() }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        with(binding) {
            setSupportActionBar(toolbarView)
            recyclerView.apply {
                adapter = movieAdapter
                val spanCount = 3
                val spacing = resources.getDimensionPixelSize(R.dimen.grid_spacing)
                addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, true))
            }
        }
        observeMovieList()
        askNotificationPermission()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                mainViewModel.refresh()
                true
            }
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun observeMovieList() {
        lifecycleScope.launch {
            mainViewModel.movieList.collect { result ->
                when (result) {
                    is Resource.Loading -> binding.apply {
                        recyclerView.gone()
                        noDataView.gone()
                        progressView.visible()
                    }
                    is Resource.Success -> binding.apply {
                        progressView.gone()
                        if (result.data.isNotEmpty()) {
                            movieAdapter.setItems(result.data)
                            recyclerView.visible()
                        } else {
                            noDataView.text = Configs.NO_DATA
                            noDataView.visible()
                        }
                    }
                    is Resource.Error -> binding.apply {
                        progressView.gone()
                        noDataView.text = result.message
                        noDataView.visible()
                    }
                }
            }
        }
    }


}