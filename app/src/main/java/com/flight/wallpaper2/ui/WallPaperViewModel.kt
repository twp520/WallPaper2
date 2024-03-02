package com.flight.wallpaper2.ui

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flight.wallpaper2.bean.Category
import com.flight.wallpaper2.bean.Wallpaper
import com.flight.wallpaper2.data.WallPaperRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WallPaperViewModel : ViewModel() {

    private val _mainTitleState = MutableStateFlow<Category?>(null)
    val mainTitleState = _mainTitleState.asStateFlow()

    private val _categoryState = MutableStateFlow(listOf<Category>())
    val categoryState = _categoryState.asStateFlow()

    private val _wallpaperList = MutableStateFlow(listOf<Wallpaper>())
    val wallpaperState = _wallpaperList.asStateFlow()

    private val _downloadList = MutableStateFlow(listOf<Wallpaper>())
    val downloadState = _downloadList.asStateFlow()

    private val _favoriteList = MutableStateFlow(listOf<Wallpaper>())
    val favoriteState = _favoriteList.asStateFlow()

    fun loadCategory() {
        viewModelScope.launch {
            val category = WallPaperRepo.getCategory()
            if (category.isSuccess()) {
                _categoryState.update {
                    category.res?.category ?: mutableListOf()
                }
            }
        }
    }

    fun loadWallpaper(category: String) {
        viewModelScope.launch {
            val result = WallPaperRepo.getWallpaper(category, wallpaperState.value.size)
            Log.d("WallPaperViewModel", "loadWallpaper: ${result}")
            if (result.isSuccess()) {
                _wallpaperList.update {
                    result.res?.vertical ?: listOf()
                }
            }
        }
    }

    fun loadDownloadList(context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val download = context.externalCacheDir?.listFiles()?.map {
                    Wallpaper(it.name, it.absolutePath, it.absolutePath)
                } ?: mutableListOf()
                _downloadList.update {
                    download
                }
            }
        }
    }

    fun loadFavorite(context: Context) {
        val favorite = context.getSharedPreferences("favorite", Context.MODE_PRIVATE)
            .all.map {
                Wallpaper(it.key, it.value.toString(), it.value.toString())
            }
        _favoriteList.update {
            favorite
        }
    }

    fun updateMainTitle(title: Category?) {
        _mainTitleState.update { title }
    }


    fun favorite(context: Context, wallpaper: Wallpaper) {
        val preferences = context.getSharedPreferences("favorite", Context.MODE_PRIVATE)
        preferences.edit {
            if (isFavorite(context, wallpaper)) {
                remove(wallpaper.id)
            } else {
                putString(wallpaper.id, wallpaper.wp)
            }
        }
    }

    fun isFavorite(context: Context, wallpaper: Wallpaper): Boolean {
        return context.getSharedPreferences("favorite", Context.MODE_PRIVATE).contains(wallpaper.id)
    }
}