package com.example.willowhealth

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.getFormattingTime(pattern: String = "yyyy-MM-dd"): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(this)
}