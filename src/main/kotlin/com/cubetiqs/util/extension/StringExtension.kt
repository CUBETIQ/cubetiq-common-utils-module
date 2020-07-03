package com.cubetiqs.util.extension

import com.cubetiqs.util.StringUtils

fun String?.getOrDefault(fallback: String? = null): String = StringUtils.getOrDefault(this, fallback)
fun String?.getOrEmpty(): String = StringUtils.getOrEmpty(this)