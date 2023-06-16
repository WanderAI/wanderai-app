package com.thariqzs.wanderai.utils

import com.thariqzs.wanderai.data.api.RandomRequest
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID")) as DecimalFormat
    currencyFormat.maximumFractionDigits = 0
    val minFormatted = currencyFormat.format(amountMin)
    val maxFormatted = currencyFormat.format(amountMax)
    return "$minFormatted - $maxFormatted"
}


fun convertDateRange(dateRange: String): RandomRequest {
    val dates = dateRange.removeSurrounding("[", "]").split(", ")

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val startDate = LocalDate.parse(dates[0], formatter)
    val endDate = LocalDate.parse(dates[1], formatter)

    val dayStart = startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    val dayEnd = endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

    return RandomRequest(dayStart, dayEnd)
}

fun extractNumber(input: String): Int? {
    val numberRegex = Regex("[0-9]+")
    val matchResult = numberRegex.find(input)
    return matchResult?.value?.toIntOrNull()
}