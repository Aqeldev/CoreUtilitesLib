package org.connecttag.utilities.global.security

import org.connecttag.utilities.global.constants.EncodeConstants.Encode_Type_AES
import org.connecttag.utilities.global.constants.EncodeConstants.Encode_Type_CipherTransformation
import org.connecttag.utilities.global.constants.EncodeConstants.Encode_Type_SHA_256
import org.connecttag.utilities.global.constants.EncodeConstants.Encode_Type_UTF8
import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncodeUnits {

    private val ivParameterSpec = IvParameterSpec(
        "rA5zE3G9F5X3T3H6".toByteArray(Charsets.UTF_8)
    )

    fun encrypt(value: String, secretKey: String = ""): String? {
        return try {

            val sckey = secretKey.ifEmpty { "6jH3MT7X0kD5F9G2P1r4wAiS9W3qA5zE" }

            val skeySpec =
                SecretKeySpec(sckey.toByteArray(charset(Encode_Type_UTF8)), Encode_Type_AES)
            val cipher = Cipher.getInstance(Encode_Type_CipherTransformation)
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec)
            Base64.encodeToString(cipher.doFinal(value.toByteArray(Charsets.UTF_8)), Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            null // Return null on failure
        }
    }

    fun decrypt(strToDecrypt: String, secretKey: String = ""): String? {
        return try {
            val sckey = secretKey.ifEmpty { "6jH3MT7X0kD5F9G2P1r4wAiS9W3qA5zE" }
            val skeySpec =
                SecretKeySpec(sckey.toByteArray(charset(Encode_Type_UTF8)), Encode_Type_AES)
            val cipher = Cipher.getInstance(Encode_Type_CipherTransformation)
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec)
            String(cipher.doFinal(Base64.decode(strToDecrypt, Base64.DEFAULT)), Charsets.UTF_8)
        } catch (e: Exception) {
            println("Error while decrypting: $e") // Log error message
            null // Return null on failure
        }
    }

    val iv = "rA5zE3G9F5X3T3H6"

    fun encryptData(value: String, secretKey: String = "6jH3MT7X0kD5F9G2P1r4wAiS9W3qA5zE"): String? {
        try {
            val iv =
                IvParameterSpec(iv.toByteArray(charset(Encode_Type_UTF8)))
            val skeySpec =
                SecretKeySpec(secretKey.toByteArray(charset(Encode_Type_UTF8)), Encode_Type_AES)
            val cipher =
                Cipher.getInstance(Encode_Type_CipherTransformation)
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv)
            val encrypted = cipher.doFinal(value.toByteArray())
            val x = Base64.encodeToString(encrypted, Base64.DEFAULT)
            return x
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
        return null
    }

    fun decryptData(strToDecrypt: String, secretKey: String = "6jH3MT7X0kD5F9G2P1r4wAiS9W3qA5zE"): String? {

        try {
            val cipher = Cipher.getInstance(Encode_Type_CipherTransformation)
            val Sec = SecretKeySpec(secretKey.toByteArray(), Encode_Type_AES)
            val iiv = IvParameterSpec(iv.toByteArray())
            cipher.init(Cipher.DECRYPT_MODE, Sec, iiv)
            val res = String(cipher.doFinal(Base64.decode(strToDecrypt, Base64.DEFAULT)))
            return res
        } catch (e: Exception) {
            println("Error while decrypting: $e")
        }
        return null
    }
    /*



        override fun intercept(chain: Interceptor.Chain): Response = chain
            .run { proceed(request()) }
            .let { response ->
                return@let if (response.isSuccessful) {
                    val body = response.body
                    val contentType = body.contentType()
                    val charset = contentType?.charset() ?: Charset.defaultCharset()
                    val buffer = body.source().apply {
                                    request(Long.MAX_VALUE) }.buffer
                    val bodyContent = buffer.clone().readString(charset)
                    val tst= bodyContent.let(::decrypt)
                    response.newBuilder()
                        .body(tst!!.toResponseBody(contentType))
                        .build()
                }
                else
                    response
            }
    */

    fun F_GenerateSHA256(originalString: String): String? {
        return try {
            val digest = MessageDigest.getInstance(Encode_Type_SHA_256)
            val encodedHash = digest.digest(originalString.toByteArray(StandardCharsets.UTF_8))
            F_BytesToHex(encodedHash)
        } catch (ex: java.lang.Exception) {
            originalString
        }
    }

    fun F_BytesToHex(hash: ByteArray): String {
        val hexString = StringBuffer()
        for (i in hash.indices) {
            val hex = Integer.toHexString(0xff and hash[i].toInt())
            if (hex.length == 1) {
                hexString.append('0')
            }
            hexString.append(hex)
        }
        return hexString.toString()
    }
}
