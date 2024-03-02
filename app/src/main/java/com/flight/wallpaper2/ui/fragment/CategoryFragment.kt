package com.flight.wallpaper2.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.flight.wallpaper2.databinding.FragmentListBinding
import com.flight.wallpaper2.ui.WallPaperViewModel
import com.flight.wallpaper2.ui.adapter.CategoryAdapter
import com.flight.wallpaper2.ui.adapter.GridItemDecoration
import kotlinx.coroutines.launch


class CategoryFragment : BaseFragment<FragmentListBinding>() {

    private val viewModel by activityViewModels<WallPaperViewModel>()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListBinding {
        return FragmentListBinding.inflate(inflater)
    }

    override fun setup() {
        val adapter = CategoryAdapter()
        viewBinding.list.layoutManager = GridLayoutManager(requireContext(), 2)
        viewBinding.list.adapter = adapter
        viewBinding.list.addItemDecoration(GridItemDecoration(20, 2))

        viewBinding.progressView.isVisible = true
        viewModel.loadCategory()

        lifecycleScope.launch {
            viewModel.categoryState.collect {
                adapter.submitList(it)
                viewBinding.progressView.isVisible = false
            }
        }

        adapter.setOnItemClickListener { _, _, position ->
            adapter.getItem(position)?.let {
                viewModel.updateMainTitle(it)
            }
        }

    }
}