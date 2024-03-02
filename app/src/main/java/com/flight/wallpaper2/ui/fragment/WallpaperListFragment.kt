package com.flight.wallpaper2.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.QuickAdapterHelper
import com.chad.library.adapter.base.loadState.LoadState
import com.chad.library.adapter.base.loadState.trailing.TrailingLoadStateAdapter
import com.flight.wallpaper2.databinding.FragmentListBinding
import com.flight.wallpaper2.ui.PreviewActivity
import com.flight.wallpaper2.ui.WallPaperViewModel
import com.flight.wallpaper2.ui.adapter.GridItemDecoration
import com.flight.wallpaper2.ui.adapter.WallPaperListAdapter
import kotlinx.coroutines.launch


class WallpaperListFragment : BaseFragment<FragmentListBinding>() {

    private val listAdapter = WallPaperListAdapter()
    private val viewModel by activityViewModels<WallPaperViewModel>()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListBinding {
        return FragmentListBinding.inflate(inflater)
    }

    override fun setup() {

        val id = arguments?.getString("category") ?: ""
        if (id.isEmpty()) return

        viewBinding.progressView.isVisible = true
        viewBinding.list.layoutManager = GridLayoutManager(requireContext(), 2)
        viewBinding.list.addItemDecoration(GridItemDecoration(20, 2))

        val helper = QuickAdapterHelper.Builder(listAdapter)
            .setTrailingLoadStateAdapter(object : TrailingLoadStateAdapter.OnTrailingListener {
                override fun onFailRetry() {
                    viewModel.loadWallpaper(id)
                }

                override fun onLoad() {
                    viewModel.loadWallpaper(id)
                }

                override fun isAllowLoading(): Boolean {
                    return viewBinding.progressView.isVisible
                }

            }).build()

        viewBinding.list.adapter = helper.adapter

        lifecycleScope.launch {
            viewModel.wallpaperState.collect {
                listAdapter.addAll(it)
                viewBinding.progressView.isVisible = false
                helper.trailingLoadState = LoadState.NotLoading(false)
            }
        }

        listAdapter.setOnItemClickListener { _, _, position ->
            listAdapter.getItem(position)?.let {
                startActivity(Intent(
                    requireActivity(),
                    PreviewActivity::class.java
                ).apply {
                    putExtra("wallpaper", it)
                })

            }
        }

        viewModel.loadWallpaper(id)
    }
}