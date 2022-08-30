package com.ibnu.saltnews.utils

import java.text.SimpleDateFormat
import java.util.*

fun String.parseToDateFormat(): String {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ROOT)
    val formatter = SimpleDateFormat("dd/MM/yyyy  HH:mm", Locale.ROOT)
    return formatter.format(parser.parse(this) ?: Date())
}