package com.ideaware.brightsoccer.utils

import android.annotation.SuppressLint
import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private const val API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

@SuppressLint("SimpleDateFormat")
fun String.fixtureDateFormat(): String {
    if (this.isNotEmpty()) {
        try {
            val date: Date = SimpleDateFormat(API_DATE_FORMAT, Locale.getDefault()).parse(this)
            //Local TimeZone
            return SimpleDateFormat("MMM dd '. at' yyyy").format(date)
        } catch (ex: ParseException) {
            Log.e("ParseException", ex.localizedMessage)
        } catch (ex: Exception) {
            Log.e("Exception", ex.localizedMessage)
        }
    }
    return this
}

fun String.getDate(): Date {
    return SimpleDateFormat(API_DATE_FORMAT, Locale.getDefault()).parse(this)
}

@SuppressLint("SimpleDateFormat")
fun String.dayAndDayName(): String {
    val date: Date = SimpleDateFormat(API_DATE_FORMAT, Locale.getDefault()).parse(this)
    //Local TimeZone
    return SimpleDateFormat("dd \n EEE").format(date)
}

fun String.getCalendar(): Calendar {
    val date: Date = SimpleDateFormat(API_DATE_FORMAT, Locale.getDefault()).parse(this)
    val calendar = Calendar.getInstance()
    calendar.time = date
    return calendar
}

@SuppressLint("SimpleDateFormat")
fun String.getMonthAndYear(): String {
    if (this.isNotEmpty()) {
        try {
            val date: Date = SimpleDateFormat(API_DATE_FORMAT, Locale.getDefault()).parse(this)
            //Local TimeZone
            return SimpleDateFormat("MMMM yyyy").format(date)
        } catch (ex: ParseException) {
            Log.e("ParseException", ex.localizedMessage)
        } catch (ex: Exception) {
            Log.e("Exception", ex.localizedMessage)
        }
    }
    return ""
}