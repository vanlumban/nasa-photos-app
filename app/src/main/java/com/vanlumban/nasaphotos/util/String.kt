package com.vanlumban.nasaphotos.util

import java.text.SimpleDateFormat
import java.util.Locale

fun String.formatDate(): String {
    return try {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val formatter = SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH)
        val date = parser.parse(this)
        date?.let { formatter.format(it) } ?: "---"
    } catch (_: Exception) {
        "---"
    }
}

fun String.cleanOrDefault(default: String = "---"): String {
    return this.trim().ifEmpty { default }
}