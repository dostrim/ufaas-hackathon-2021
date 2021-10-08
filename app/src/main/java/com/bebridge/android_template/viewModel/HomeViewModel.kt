package com.bebridge.android_template.viewModel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.bebridge.android_template.api.response.PageInfo
import com.bebridge.android_template.api.subscribeWhileHandlingRetrofitError
import com.bebridge.android_template.db.entity.ProductEntity
import com.bebridge.android_template.fragment.HomeNavigator
import com.bebridge.android_template.repository.ProductRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeViewModel @Inject constructor(private val productRepository: ProductRepository) {

  val products = ObservableField<List<ProductEntity>>()

  var navigator: HomeNavigator? = null

  val loading = ObservableBoolean(false)

  val additionalLoading = ObservableBoolean(false)

  var pageInfo: PageInfo? = null

  fun onCreateView(navigator: HomeNavigator) {
    this.navigator = navigator
    if (products.get() == null) {
      refresh()
    }
  }

  fun refresh() {
    if (!loading.get()) {
      loading.set(true)
      download(1)
    }
  }

  fun loadNext() {
    if (!loading.get() && !additionalLoading.get()) {
      pageInfo?.nextPage?.let { nextPage ->
        additionalLoading.set(true)
        download(nextPage)
      }
    }
  }


  fun onDestroyView() {
    navigator = null
  }

  private fun download(page: Int) {
    productRepository.downloadAndInsertProducts(page)
      .subscribeWhileHandlingRetrofitError({
        pageInfo = it.second

        if (page == 1) {
          products.set(it.first.map { it.product })
          loading.set(false)
          navigator?.onRefreshed()
        } else {
          (products.get() as ArrayList).addAll(it.first.map { it.product })
          products.notifyChange()
          additionalLoading.set(false)
        }


      }, {
        if (page == 1) {
          loading.set(false)
          navigator?.onRefreshed()
        } else {
          additionalLoading.set(false)
        }
        navigator?.onError(it)
      })
  }


}