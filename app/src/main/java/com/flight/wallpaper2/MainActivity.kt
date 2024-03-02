package com.flight.wallpaper2

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.flight.wallpaper2.bean.Category
import com.flight.wallpaper2.databinding.ActivityMainBinding
import com.flight.wallpaper2.ui.WallPaperViewModel
import com.flight.wallpaper2.ui.fragment.CategoryFragment
import com.flight.wallpaper2.ui.fragment.DownloadFragment
import com.flight.wallpaper2.ui.fragment.FavoriteFragment
import com.flight.wallpaper2.ui.fragment.WallpaperListFragment
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private val viewModel by viewModels<WallPaperViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val categoryFragment = CategoryFragment()
        val fragmentList = mutableListOf(
            DownloadFragment(),
            categoryFragment,
            FavoriteFragment()
        )
        viewBinding.mainNav.setOnItemSelectedListener {
            val index = when (it.itemId) {
                R.id.bot_nav_download -> 0
                R.id.bot_nav_discover -> 1
                R.id.bot_nav_favorite -> 2
                else -> 1
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragmentList[index])
                .commit()
            updateTitle(viewModel.mainTitleState.value, it.itemId,categoryFragment)
            true
        }
        viewBinding.mainNav.selectedItemId = R.id.bot_nav_discover

        lifecycleScope.launch {
            viewModel.mainTitleState.collect {
                updateTitle(it, viewBinding.mainNav.selectedItemId,categoryFragment)
            }
        }

        viewBinding.mainToolbar.setNavigationOnClickListener {
            viewModel.updateMainTitle(null)
        }

    }

    private fun updateTitle(
        title: Category?,
        checkedMenuId: Int,
        categoryFragment: CategoryFragment
    ) {
        val showTitle = checkedMenuId == R.id.bot_nav_discover && title != null
        viewBinding.mainToolbar.isVisible = showTitle
        viewBinding.mainTopImage.isVisible = !showTitle
        viewBinding.mainToolbar.title = title?.title ?: ""
        if (checkedMenuId == R.id.bot_nav_discover) {
            val fragment = if (title == null) {
                categoryFragment
            } else {
                WallpaperListFragment().apply {
                    arguments = bundleOf(Pair("category", title.id))
                }
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
        }
    }
}