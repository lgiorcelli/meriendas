package com.lgior.meriendas.dias

import java.time.LocalDate

interface ServicioDeDias {
    fun obtenerDiasEntre(desde: LocalDate, hasta: LocalDate): List<Dia>
}