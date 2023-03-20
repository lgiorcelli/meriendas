package com.lgior.meriendas.dias

import java.time.LocalDate

interface ServicioDeDias {
    fun obtenerDiasHabilesEntre(desde: LocalDate, hasta: LocalDate): List<Dia>
    fun obtenerCalendarioEntre(desde: LocalDate, hasta: LocalDate): Calendario
}