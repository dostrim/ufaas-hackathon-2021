package com.bebridge.android_template.groupie

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bebridge.android_template.R
import com.bebridge.android_template.databinding.ItemProductBinding
import com.bebridge.android_template.db.entity.ProductEntity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.xwray.groupie.databinding.BindableItem

data class ProductItem(val product: ProductEntity, val adapter: RecyclerView.Adapter<ViewHolder>) : BindableItem<ItemProductBinding>(
  product.hashCode().toLong()
) {

  override fun getLayout(): Int {
    return R.layout.item_product
  }

  override fun bind(viewBinding: ItemProductBinding, position: Int) {
    viewBinding.textView.text = product.name

    Picasso.get()
      .load(product.photo1)
      .tag(adapter)
      .resize(dpToPx(180), dpToPx(120))
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