package com.example.willowhealth

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.getFormattingDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(this)
}

fun Date.getFormattingTime(): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(this)
}