package com.cubetiqs.util

private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

fun randomStringFromLength(length: Int = 2, separator: String = ""): String {
    return (1..length)
        .map { kotlin.random.Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString(separator)
}

fun String?.randomString(): String {
    return randomStringFromLength(this?.length ?: 2)
}

fun Int?.randomString(): String {
    return randomStringFromLength(this ?: 2)
}
