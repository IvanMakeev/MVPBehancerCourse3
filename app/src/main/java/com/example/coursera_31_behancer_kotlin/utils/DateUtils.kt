package com.example.coursera_31_behancer_kotlin.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun format(time: Long): String {
        val date = Date(time * 1000L)
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        return sdf.format(date)
    }

}

