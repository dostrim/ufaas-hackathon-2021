package com.bebridge.android_template.groupie

import com.bebridge.android_template.R
import com.bebridge.android_template.databinding.ItemCategoryShimmerBinding
import com.xwray.groupie.databinding.BindableItem

class ProductCategoryShimmerItem : BindableItem<ItemCategoryShimmerBinding>(
) {

  override fun getLayout(): Int {
    return R.layout.item_category_shimmer
  }

  override fun bind(viewBinding: ItemCategoryShimmerBinding, position: Int) {

  }

}