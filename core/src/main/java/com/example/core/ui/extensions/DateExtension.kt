package com.example.core.ui.extensions

fun String.toReadableDateTime(): String {
    val inputFormat = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", java.util.Locale.getDefault())
    inputFormat.timeZone = java.util.TimeZone.getTimeZone("UTC")

    val outputFormat = java.text.SimpleDateFormat("dd.MM.yyyy - HH:mm", java.util.Locale.getDefault())
    outputFormat.timeZone = java.util.TimeZone.getDefault()

    val date = inputFormat.parse(this)
    return outputFormat.format(date!!)
}