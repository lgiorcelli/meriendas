package com.lgior.meriendas.dias


import java.time.DayOfWeek
import java.time.LocalDate

data class Dia(
    val dayOfWeek: DayOfWeek,
    val diasDesdeInicioDeClases: Int,
    val date: LocalDate = LocalDate.now(),
    val numeroDeSemana: Int
) {

    fun nombre(): String {
        return when (dayOfWeek) {
            DayOfWeek.MONDAY -> "Lunes"
            DayOfWeek.TUESDAY -> "Martes"
            DayOfWeek.WEDNESDAY -> "Miércoles"
            DayOfWeek.THURSDAY -> "Jueves"
            DayOfWeek.FRIDAY -> "Viernes"
            DayOfWeek.SATURDAY -> "Sábado"
            DayOfWeek.SUNDAY -> "Domingo"
        }
    }

    fun esDiaDeSemana(): Boolean {
        return dayOfWeek in semanaHabil
    }

    companion object {
        private val semanaHabil =
            listOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)
    }
}