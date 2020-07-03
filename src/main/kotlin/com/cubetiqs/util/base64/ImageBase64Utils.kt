package com.cubetiqs.util.base64

import com.cubetiqs.exception.RIOException
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.Base64
import javax.imageio.ImageIO
import javax.xml.bind.DatatypeConverter

/**
 * Image Base64 Libra helper
 *
 * @author sombochea
 * @since 1.0
 */
object ImageBase64Utils {
    private fun base64Trimmer(base64String: String): String? {
        val data: List<String> = try {
            base64String.split(",")
        } catch (ex: Exception) {
            listOf()
        }
        return if (data.size > 1) {
            data[1]
        } else {
            base64String
        }
    }

    @Throws(RIOException::class)
    @JvmStatic
    fun toImageBytes(base64String: String): ByteArray {
        var image: BufferedImage? = null
        try {
            ByteArrayInputStream(
                Base64.getMimeDecoder().decode(base64Trimmer(base64String))
            ).use { inputStream -> image = ImageIO.read(inputStream) }
        } catch (ex: IOException) {
            ex.printStackTrace()
            throw RIOException(ex)
        }
        try {
            ByteArrayOutputStream().use { outputStream ->
                ImageIO.write(image, "png", outputStream)
                return outputStream.toByteArray()
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            throw RIOException(ex)
        }
    }

    @JvmStatic
    fun toBytes(base64String: String, useTrimmer: Boolean): ByteArray {
        return if (useTrimmer) DatatypeConverter.parseBase64Binary(base64Trimmer(base64String)) else DatatypeConverter.parseBase64Binary(
            base64String
        )
    }
}
