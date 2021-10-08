package com.bebridge.android_template.viewModel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.bebridge.android_template.db.entity.ProductEntity
import com.bebridge.android_template.fragment.DetailNavigator
import com.bebridge.android_template.repository.ProductRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailViewModel @Inject constructor(private val productRepository: ProductRepository) {

  val product = ObservableField<ProductEntity>()

  var navigator: DetailNavigator? = null

  val loading = ObservableBoolean(false)

  fun onCreateView(navigator: DetailNavigator, product: ProductEntity) {
    this.navigator = navigator

    this.product.set(product)
  }

  fun onDestroyView() {
    navigator = null
  }
}