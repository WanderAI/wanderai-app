package com.thariqzs.wanderai.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale

fun formatDateRange(dateStart: String, dateEnd: String): String {
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val dateStartObj = dateFormatter.parse(dateStart)
    val dateEndObj = dateFormatter.parse(dateEnd)

    val startDay = SimpleDateFormat("d", Locale.getDefault()).format(dateStartObj)
    val startMonth = SimpleDateFormat("MMM", Locale("id", "ID")).format(dateStartObj)
    val endDay = SimpleDateFormat("d", Locale.getDefault()).format(dateEndObj)
    val endMonth = SimpleDateFormat("MMM", Locale("id", "ID")).format(dateEndObj)

    return "$startDay $startMonth 2023 - $endDay $endMonth 2023"
}

fun formatAmountRange(amountMin: Int, amountMax: Int): String {
    val formatter = DecimalFormat("###,###,###")
    val formattedMin = formatter.format(amountMin)
    val formattedMax = formatter.format(amountMax)
    return "Rp$formattedMin - Rp$formattedMax"
}