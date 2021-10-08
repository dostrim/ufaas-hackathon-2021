package com.bebridge.android_template.util

import android.content.res.Resources
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bebridge.android_template.R
import com.bebridge.android_template.db.entity.ProductEntity
import com.bebridge.android_template.db.model.CategoryWithProducts
import com.bebridge.android_template.groupie.ImagePagerItem
import com.bebridge.android_template.groupie.ProductCategoryItem
import com.bebridge.android_template.groupie.ProductCategoryShimmerItem
import com.bebridge.android_template.groupie.ProductItem
import com.bebridge.android_template.groupie.ProductShimmerItem
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.databinding.BindableItem
import jp.wasabeef.picasso.transformations.CropCircleTransformation

object BindingAdaptersForRecyclerView {

  @BindingAdapter(value = ["categoryWithProducts", "loading"])
  @JvmStatic
  fun RecyclerView.setCategoryWithProducts(categoryWithProducts: List<CategoryWithProducts>?, loading: Boolean) {

    DebugManager.instance.log(this, "categoryWithProducts=$categoryWithProducts, loading=$loading")

    val items = ArrayList<BindableItem<*>>()

    if (loading) {
      items.add(ProductCategoryShimmerItem())
      items.add(ProductShimmerItem())
      items.add(ProductShimmerItem())
      items.add(ProductShimmerItem())

    } else {
      categoryWithProducts?.forEach {
        items.add(ProductCategoryItem(it.productCategoryEntity))
        items.addAll(it.products.map { ProductItem(it, this.adapter as GroupAdapter) })
      }
    }
    (this.adapter as GroupAdapter).update(items)

  }


  @BindingAdapter(value = ["products", "loading"])
  @JvmStatic
  fun RecyclerView.setProducts(products: List<ProductEntity>?, loading: Boolean) {

    val items = ArrayList<BindableItem<*>>()

    DebugManager.instance.log(this, "setProducts=$products")

    if (loading) {
      items.add(ProductShimmerItem())
      items.add(ProductShimmerItem())
      items.add(ProductShimmerItem())

    } else {
      products?.forEach {
        items.add(ProductItem(it, this.adapter as GroupAdapter))
      }
    }
    (this.adapter as GroupAdapter).update(items)

  }

}

object BindingAdaptersForViewPage2 {

  @BindingAdapter(value = ["product"])
  @JvmStatic
  fun ViewPager2.setProduct(product: ProductEntity?) {

    val items = ArrayList<BindableItem<*>>()

    DebugManager.instance.log(this, "setProduct=$product")

    product?.photo1?.let {
      items.add(ImagePagerItem(it, this.adapter as GroupAdapter))
    }

    product?.photo2?.let {
      items.add(ImagePagerItem(it, this.adapter as GroupAdapter))
    }

    (this.adapter as GroupAdapter).update(items)

  }

}

object BindingAdapterForImageView {

  @BindingAdapter("profile_url")
  @JvmStatic
  fun ImageView.setProfileUrl(url: String?) {
    DebugManager.instance.log(this, "url=$url")
    url?.let {
      if (url.isNotEmpty()) {
        Picasso.get()
          .load(it)
          .resize(dpToPx(90), dpToPx(90))
          .onlyScaleDown()
          .centerCrop()
          .transform(CropCircleTransformation())
          .into(this, object : Callback {
            override fun onSuccess() {
              visibility = View.VISIBLE
            }

            override fun onError(e: Exception?) {
              e?.printStackTrace()
              visibility = View.VISIBLE
            }
          })
      } else {
        // if string is empty, it means the user not yet registered any profile picture
        visibility = View.VISIBLE
      }
    } ?: run {
      // when creating the screen, at first profile_url is set as null
      setImageDrawable(resources.getDrawable(R.drawable.user_default, null))
    }
  }


  fun dpToPx(dp: Int): Int {
    return (dp * Resources.getSystem().displayMetrics.density).toInt()
  }
}