package com.flight.wallpaper2.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.flight.wallpaper2.R
import com.flight.wallpaper2.databinding.FragmentListBinding
import com.flight.wallpaper2.ui.PreviewActivity
import com.flight.wallpaper2.ui.WallPaperViewModel
import com.flight.wallpaper2.ui.adapter.GridItemDecoration
import com.flight.wallpaper2.ui.adapter.WallPaperListAdapter
import kotlinx.coroutines.launch


class FavoriteFragment : BaseFragment<FragmentListBinding>() {
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListBinding {
        return FragmentListBinding.inflate(inflater)
    }

    override fun setup() {
        val viewModel by viewModels<WallPaperViewModel>()
        viewBinding.list.layoutManager = GridLayoutManager(requireContext(), 2)
        viewBinding.list.addItemDecoration(GridItemDecoration(20, 2))
        val listAdapter = WallPaperListAdapter()
        viewBinding.list.adapter = listAdapter
        viewModel.loadFavorite(requireContext())
        lifecycleScope.launch {
            viewModel.favoriteState.collect {
                listAdapter.submitList(it)
                viewBinding.emptyView.root.isVisible = it.isEmpty()
            }
        }



        listAdapter.setOnItemClickListener { _, _, position ->
            listAdapter.getItem(position)?.let {
                startActivity(
                    Intent(
                        requireActivity(),
                        PreviewActivity::class.java
                    ).apply {
                        putExtra("wallpaper", it)
                    })

            }
        }
    }
}