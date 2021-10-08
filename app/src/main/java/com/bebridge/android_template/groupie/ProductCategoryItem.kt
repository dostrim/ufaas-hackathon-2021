package com.bebridge.android_template.groupie

import com.bebridge.android_template.R
import com.bebridge.android_template.databinding.ItemCategoryBinding
import com.bebridge.android_template.db.entity.ProductCategoryEntity
import com.xwray.groupie.databinding.BindableItem

data class ProductCategoryItem(private val productCategory: ProductCategoryEntity): BindableItem<ItemCategoryBinding>(
  productCategory.hashCode().toLong()
) {

  override fun getLayout(): Int {
    return R.layout.item_category
  }

  override fun bind(viewBinding: ItemCategoryBinding, position: Int) {
    viewBinding.textView.text = productCategory.name
  }
}