package com.cubetiqs.util.test

import com.cubetiqs.logging.Log
import com.cubetiqs.util.randomString
import com.cubetiqs.util.randomStringFromLength
import org.junit.Assert
import org.junit.Test

class StringUtilsTests {
    val logger = Log.getLogger(javaClass)

    @Test
    fun testRandomString() {
        val rand = "ANC".randomString()
        logger.fatal("String random is {}", rand)
        Assert.assertNotNull("string is null", rand)

        val rand2 = randomStringFromLength(10)
        logger.error("String random is {}", rand2)
        Assert.assertTrue("is not true", rand2.length == 10)
    }
}