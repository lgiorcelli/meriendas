package com.lgior.meriendas.dias

import java.time.DayOfWeek

object Dias {
    val LUNES = Dia(DayOfWeek.MONDAY,1)
    val MARTES = Dia(DayOfWeek.TUESDAY,1)
    val MIERCOLES = Dia(DayOfWeek.WEDNESDAY,1)
    val JUEVES = Dia(DayOfWeek.THURSDAY,1)
    val VIERNES = Dia(DayOfWeek.FRIDAY,1)

    fun semanaHabil(): List<Dia> {
        return listOf(
            LUNES,
            MARTES,
            MIERCOLES,
            JUEVES,
            VIERNES,
        )
    }
}