package com.example

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

const val API_DATE_FORMAT = "yyyy-MM-dd"

object DateUtils {

    fun getApiDateFormatted(date: String): Date? {
        return try {
            getApiDateFormat().parse(date)
        } catch (e: ParseException) {
            null
        }
    }
    fun getApiDateStringFormatted(date: Date?): String {
        return date?.let { getApiDateFormat().format(it) } ?: kotlin.run { "" }
    }

    @Suppress("SimpleDateFormat")
    fun getApiDateFormat(): SimpleDateFormat {
        return SimpleDateFormat(API_DATE_FORMAT, Locale.getDefault())
    }
}
