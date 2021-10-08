package com.bebridge.android_template.util

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class CryptoUtil {

  companion object {
    private const val ALGORITHM = "Blowfish"
    private const val MODE = "Blowfish/CBC/PKCS5Padding"
    private const val IV = "abcdefgh"

    fun encrypto(value: String, secretKey: String): String {
      val secretKeySpec = SecretKeySpec(secretKey.toByteArray(), ALGORITHM)
      val cipher = Cipher.getInstance(MODE)
      cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, IvParameterSpec(IV.toByteArray()))
      val cryptValue = cipher.doFinal(value.toByteArray())
      return Base64.encodeToString(cryptValue, Base64.DEFAULT)
    }

    fun decrypto(value: String, secretKey: String): String {
      val decodedValues = Base64.decode(value, Base64.DEFAULT)
      val secretKeySpec = SecretKeySpec(secretKey.toByteArray(), ALGORITHM)
      val cipher = Cipher.getInstance(MODE)
      cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, IvParameterSpec(IV.toByteArray()))
      return String(cipher.doFinal(decodedValues))
    }
  }
}
