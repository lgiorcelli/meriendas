package com.lgior.meriendas.dias

import com.lgior.meriendas.shared.toLocalDate
import java.io.File
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class ServicioDeDiasDefault(private val inicioDeClases: LocalDate, archivoDeFeriados: String) : ServicioDeDias {
    private val semanaHabil: Set<DayOfWeek> =
        setOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)
    private val feriados: Set<LocalDate>

    init {
        feriados = leerFeriados(archivoDeFeriados)
    }

    private fun leerFeriados(archivoDeFeriados: String): Set<LocalDate> {
        val lines = readCsv(archivoDeFeriados)
        return lines.map { it.first().toLocalDate() }.toSet()
    }

    override fun obtenerDiasEntre(desde: LocalDate, hasta: LocalDate): List<Dia> {
        val result: List<LocalDate> = crearDiasEntre(desde, hasta)
        val diasHabiles = filtrarDiasHabiles(result)
        val diasHabilesDesdeInicio = contarDiasHabilesDesdeInicio()

        return diasHabiles.mapIndexed { index, localDate ->
            Dia(localDate.dayOfWeek, diasHabilesDesdeInicio + index, localDate)
        }
    }

    private fun filtrarDiasHabiles(dates: List<LocalDate>): List<LocalDate> {
        val sinFeriados = dates.filterNot { feriados.contains(it) }
        return sinFeriados.filter { dia: LocalDate -> semanaHabil.contains(dia.dayOfWeek) }
    }


    private fun crearDiasEntre(
        desde: LocalDate,
        hasta: LocalDate
    ): List<LocalDate> {
        val cantidadDeDiasEntreFechas = ChronoUnit.DAYS.between(desde, hasta)
        val result: MutableList<LocalDate> = mutableListOf()
        for (i in 0..cantidadDeDiasEntreFechas) {
            val date = desde.plusDays(i)
            result.add(date)
        }
        return result.toList().also {
            println("Se obtuvieron ${it.size} días entre $desde y $hasta")
        }
    }

    private fun contarDiasHabilesDesdeInicio() = 0

    private fun readCsv(fileName: String): Sequence<List<String>> {
        val file = File(fileName)
        val inputStream = file.inputStream()
        val reader = inputStream.bufferedReader()
        return reader.lineSequence()
            .filter { it.isNotBlank() }
            .map { it.split(";") }
    }
    /*
        private fun crearDiasEntre(
        desde: LocalDate,
        hasta: LocalDate
    ): MutableList<Dia> {
        val periodoEntreFechas = Period.between(desde, hasta).days
        var diasDesdeInicioDeClases = Period.between(inicioDeClases, desde).days
        val result: MutableList<Dia> = mutableListOf()
        for (i in 0..periodoEntreFechas) {
            val date = desde.plusDays(i.toLong())
            val dia = Dia(date.dayOfWeek,diasDesdeInicioDeClases++)
            result.add(dia)
        }
        return result
    }
     */
}