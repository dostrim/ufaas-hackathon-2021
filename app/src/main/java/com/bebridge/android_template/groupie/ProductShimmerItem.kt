package com.bebridge.android_template.groupie

import com.bebridge.android_template.R
import com.bebridge.android_template.databinding.ItemProductShimmerBinding
import com.xwray.groupie.databinding.BindableItem

class ProductShimmerItem : BindableItem<ItemProductShimmerBinding>(
) {

  override fun getLayout(): Int {
    return R.layout.item_product_shimmer
  }

  override fun bind(viewBinding: ItemProductShimmerBinding, position: Int) {
  }

}