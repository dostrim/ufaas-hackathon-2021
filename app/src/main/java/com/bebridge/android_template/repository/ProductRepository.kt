package com.bebridge.android_template.repository

import com.bebridge.android_template.db.AppDatabase
import com.bebridge.android_template.api.ApiService.IApiService
import com.bebridge.android_template.api.response.PageInfo
import com.bebridge.android_template.api.toProductWithCategoryAndTags
import com.bebridge.android_template.db.model.CategoryWithProducts
import com.bebridge.android_template.db.model.ProductAndCategory
import com.bebridge.android_template.db.model.ProductWithProductTags
import com.bebridge.android_template.db.entity.ProductEntity
import com.bebridge.android_template.db.model.ProductWithCategoryAndTags
import com.bebridge.android_template.db.entity.ProductProductTagEntity
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
  private val service: IApiService,
  private val db: AppDatabase
) {


  fun downloadAndInsertProducts(page: Int = 1): Single<Pair<List<ProductWithCategoryAndTags>, PageInfo?>> {
    return service.getProducts(page, null)
      .map { Pair(it.apiProducts.toProductWithCategoryAndTags(), it.pageInfo) }
      .doOnSuccess {
        it.first.forEach { productWithCategoryAndTags ->
          db.productDao().insertProductFull(
            productWithCategoryAndTags.product,
            productWithCategoryAndTags.productCategoryEntity,
            productWithCategoryAndTags.productTagEntities,
            productWithCategoryAndTags.productTagEntities.map {
              ProductProductTagEntity(
                productWithCategoryAndTags.product.productId,
                it.productTagId
              )
            })
        }
      }
  }

  fun selectProducts(): Single<List<ProductEntity>> {
    return db.productDao().selectProducts()
  }

  fun selectCategoryWithProductsList(): Single<List<CategoryWithProducts>> {
    return db.productDao().selectCategoryWithProductsList()
  }

  fun selectProductAndCategoryList(): Single<List<ProductAndCategory>> {
    return db.productDao().selectProductAndCategoryList()
  }

  fun selectProductWithTagsList(): Single<List<ProductWithProductTags>> {
    return db.productDao().selectProductWithTagsList()
  }

  fun selectProductWithCategoryAndTagsList(): Single<List<ProductWithCategoryAndTags>> {
    return db.productDao().selectProductWithCategoryAndTagsList()
  }
}