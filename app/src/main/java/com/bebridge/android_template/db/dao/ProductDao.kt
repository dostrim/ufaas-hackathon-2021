package com.bebridge.android_template.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.bebridge.android_template.db.model.CategoryWithProducts
import com.bebridge.android_template.db.model.ProductAndCategory
import com.bebridge.android_template.db.model.ProductWithProductTags
import com.bebridge.android_template.db.entity.ProductEntity
import com.bebridge.android_template.db.entity.ProductCategoryEntity
import com.bebridge.android_template.db.model.ProductWithCategoryAndTags
import com.bebridge.android_template.db.entity.ProductProductTagEntity
import com.bebridge.android_template.db.entity.ProductTagEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ProductDao {

  @Query("SELECT * FROM product")
  fun selectProducts(): Single<List<ProductEntity>>

  @Transaction
  @Query("SELECT * FROM product_category")
  fun selectCategoryWithProductsList(): Single<List<CategoryWithProducts>>

  @Query("SELECT * FROM product, product_category WHERE product.categoryId = product_category.categoryId")
  fun selectProductAndCategoryList(): Single<List<ProductAndCategory>>

  @Transaction
  @Query("SELECT * FROM product")
  fun selectProductWithTagsList(): Single<List<ProductWithProductTags>>

  @Transaction
  @Query("SELECT * FROM product, product_category WHERE product.categoryId = product_category.categoryId")
  fun selectProductWithCategoryAndTagsList(): Single<List<ProductWithCategoryAndTags>>

  @Query("SELECT * FROM product WHERE productId = :id")
  fun selectById(id: Long): ProductEntity

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(vararg products: ProductEntity)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(products: List<ProductEntity>)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertCategory(productCategoryEntity: ProductCategoryEntity): Completable

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAllCategories(productCategoryEntity: List<ProductCategoryEntity>)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAllTags(productTagEntities: List<ProductTagEntity>)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertCategoryAndProduct(productCategoryEntity: ProductCategoryEntity, product: ProductEntity)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertProductFull(
    product: ProductEntity,
    productCategoryEntity: ProductCategoryEntity,
    productTagEntities: List<ProductTagEntity>,
    productProductTagEntity: List<ProductProductTagEntity>
  )

  @Delete
  fun delete(product: ProductEntity)

}