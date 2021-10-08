package com.bebridge.android_template.api

import com.bebridge.android_template.api.response.ApiProduct
import com.bebridge.android_template.api.response.ApiProductTag
import com.bebridge.android_template.api.response.ApiUser
import com.bebridge.android_template.api.response.ErrorResponse
import com.bebridge.android_template.db.entity.ProductEntity
import com.bebridge.android_template.db.model.ProductWithCategoryAndTags
import com.bebridge.android_template.db.entity.ProductCategoryEntity
import com.bebridge.android_template.db.entity.ProductTagEntity
import com.bebridge.android_template.db.entity.UserEntity
import com.bebridge.android_template.util.Either
import com.bebridge.android_template.util.Left
import com.bebridge.android_template.util.Right
import com.bebridge.android_template.util.fold
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.retrofitEither(): Single<Either<ErrorResponse, T>> =
  map { Right<ErrorResponse, T>(it) as Either<ErrorResponse, T> }
    .onErrorReturn { Left(ErrorResponse.parseThrowable(it)) }

inline fun <T> Single<T>.subscribeWhileHandlingRetrofitError(
  crossinline onSuccess: (T) -> Unit,
  crossinline onError: (ErrorResponse) -> Unit
): Disposable =
  this.retrofitEither()
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribeBy({
    }, {
      it.fold({
        onError.invoke(it)
      }, {
        onSuccess.invoke(it)
      })
    })


fun ApiProduct.toProductWithCategoryAndTags(): ProductWithCategoryAndTags {
  return ProductWithCategoryAndTags(
    ProductEntity(productId, name, photo1, photo2, categoryId),
    this.productTags?.toProductTags() ?: listOf(),
    ProductCategoryEntity(
      this.categoryId,
      categoryName
    )
  )
}

fun List<ApiProduct>.toProductWithCategoryAndTags() = this.map { it.toProductWithCategoryAndTags() }


fun ApiProductTag.toProductTag(): ProductTagEntity {
  return ProductTagEntity(
    productTagId,
    name
  )
}

fun List<ApiProductTag>.toProductTags() = this.map { it.toProductTag() }

fun ApiUser.toUser(): UserEntity =
  UserEntity(
    userId,
    userName,
    email,
    birthday,
    genderCode,
    gender,
    photo,
    createdAt,
    updatedAt,
    deletedAt
  )