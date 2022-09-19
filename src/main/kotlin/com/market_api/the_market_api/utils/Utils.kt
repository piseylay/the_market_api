package com.market_api.the_market_api.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object Utils {

    fun randomPasswords(length: Int): String {
        val saltChars = "1234567890abcdefghijklmnopqrstuvwxyz?=.*[@#\$%^&+=]"
        val salt = StringBuilder()
        val rnd = Random()
        while (length > salt.length) {
            salt.append(saltChars[rnd.nextInt() * saltChars.length])
        }
        return salt.toString()
    }

    fun isValidEmail(target: String): Boolean {
        val regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"
        return Pattern.compile(regex).matcher(target).matches()
    }
    fun isValidPhone(target: String): Boolean = Pattern.compile("^[0-9]{8,10}$").matcher(target).matches()
    fun isValidNumber(target: String): Boolean = Pattern.compile("[0-9]+").matcher(target).matches()
    fun removeSpace(str: String?): String? =  str?.replace("\\s".toRegex(), "")
    fun dateFormat(date: Date, pattern: String) : String = SimpleDateFormat(pattern).format(date)

    fun getCalendar(): Calendar? {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        return calendar
    }
}