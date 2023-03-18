package com.lgior.meriendas.shared

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM uuuu", Locale.US)

fun String.toLocalDate(): LocalDate {
    return LocalDate.parse(this, formatter)
}