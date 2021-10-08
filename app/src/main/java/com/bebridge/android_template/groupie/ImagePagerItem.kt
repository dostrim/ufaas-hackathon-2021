package com.bebridge.android_template.groupie

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bebridge.android_template.R
import com.bebridge.android_template.databinding.ItemImagePagerBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.xwray.groupie.databinding.BindableItem

class ImagePagerItem(val url: String, val adapter: RecyclerView.Adapter<ViewHolder>) : BindableItem<ItemImagePagerBinding>(
  url.hashCode().toLong()
) {
  override fun getLayout(): Int = R.layout.item_image_pager
  override fun bind(viewBinding: ItemImagePagerBinding, position: Int) {
    Picasso.get()
      .load(url)
      .tag(adapter)
      .resize(dpToPx(360), dpToPx(240))
      .onlyScaleDown()
      .centerCrop()
      .into(viewBinding.imageView, object : Callback {
        override fun onSuccess() {
        }

        override fun onError(e: Exception?) {
        }
      })
  }

  private fun dpToPx(dp: Int): Int {
    return (dp * Resources.getSystem().displayMetrics.density).toInt()
  }

}