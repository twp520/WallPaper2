package com.flight.wallpaper2.ui

import android.app.WallpaperManager
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.flight.wallpaper2.R
import com.flight.wallpaper2.bean.Wallpaper
import com.flight.wallpaper2.databinding.ActivityPreviewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


class PreviewActivity : AppCompatActivity() {

    private val viewModel by viewModels<WallPaperViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        with(window) {
            addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        val wallpaper = intent.getParcelableExtra<Wallpaper>("wallpaper")
        wallpaper ?: return
        Glide.with(this)
            .load(wallpaper.thumb)
            .centerCrop()
            .into(viewBinding.image)

        viewBinding.buttonDownload.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle(R.string.set_option_title)
                    .setItems(R.array.set_option) { dialog, position ->
                        dialog.dismiss()
                        setWallpaper(wallpaper, position, viewBinding)
                    }
                    .show()
            }


        }

        viewBinding.buttonFavorite.setOnClickListener {
            viewModel.favorite(this, wallpaper)
            viewBinding.buttonFavorite.iconTint = if (viewModel.isFavorite(this, wallpaper))
                ColorStateList.valueOf(Color.RED) else ColorStateList.valueOf(Color.WHITE)
        }

        viewBinding.buttonFavorite.iconTint = if (viewModel.isFavorite(this, wallpaper))
            ColorStateList.valueOf(Color.RED) else ColorStateList.valueOf(Color.WHITE)

        viewBinding.buttonShare.setOnClickListener {
            share()
        }
    }

    private fun setWallpaper(
        wallpaper: Wallpaper,
        position: Int,
        viewBinding: ActivityPreviewBinding
    ) {
        lifecycleScope.launch {
            viewBinding.progress.isVisible = true
            val file = File(externalCacheDir, wallpaper.id)
            val bitmap = withContext(Dispatchers.IO) {
                if (file.exists())
                    return@withContext file
                val bitmap = Glide.with(this@PreviewActivity)
                    .asBitmap()
                    .load(wallpaper.wp)
                    .submit()
                    .get()

                bitmap.compress(
                    Bitmap.CompressFormat.JPEG, 100,
                    file.outputStream()
                )
                return@withContext file
            }
            viewBinding.progress.isVisible = false
            Toast.makeText(this@PreviewActivity, "Set Success!", Toast.LENGTH_SHORT).show()
            val flag = when (position) {
                0 -> WallpaperManager.FLAG_SYSTEM
                1 -> WallpaperManager.FLAG_LOCK
                else -> (WallpaperManager.FLAG_LOCK or WallpaperManager.FLAG_SYSTEM)
            }
            WallpaperManager.getInstance(this@PreviewActivity)
                .setStream(bitmap.inputStream(), null, false, flag)
        }
    }

    private fun share() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT,
                "https://play.google.com/store/apps/details?id=${packageName}"
            )
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
        }
        startActivity(Intent.createChooser(intent, "Share FileManager"))
    }
}