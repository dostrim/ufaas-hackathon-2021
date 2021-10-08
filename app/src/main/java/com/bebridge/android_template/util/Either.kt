package com.bebridge.android_template.util

sealed class Either<out A, out B> {

  abstract val isLeft: Boolean

  abstract val isRight: Boolean
}

data class Left<out A, out B>(val value: A) : Either<A, B>() {
  override val isLeft: Boolean = true

  override val isRight: Boolean = false
}

data class Right<out A, out B>(val value: B) : Either<A, B>() {
  override val isLeft: Boolean = false

  override val isRight: Boolean = true

}

inline fun <A, B, X> Either<A, B>.fold(
  left: (A) -> X,
  right: (B) -> X
): X = when (this) {
  is Left -> left(value)
  is Right -> right(value)
}