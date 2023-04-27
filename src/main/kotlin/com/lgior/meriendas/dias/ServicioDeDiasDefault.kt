package com.lgior.meriendas.dias

import com.lgior.meriendas.shared.toLocalDate
import java.io.File
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * Este servicio empieza a dejar de tener sentido y empiezo a creer q necesito un calendario dibujado
 * al estilo del de feriados (https://www.argentina.gob.ar/interior/feriados-nacionales-2023)
 * con la info que tienen hoy los dias, pero haber filtrado feriados y fines de semana me obliga a
 * "adivinar" cuantos dias hay y saltearme feriados
 */
class ServicioDeDiasDefault(private val inicioDeClases: LocalDate, archivoDeFeriados: String) : ServicioDeDias {
    private val inicioDelAño: LocalDate = inicioDeClases

    private val semanaHabil: Set<DayOfWeek> =
        setOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)
    private val feriados: Set<LocalDate>

    init {
        feriados = leerFeriados(archivoDeFeriados)
    }

    private fun leerFeriados(archivoDeFeriados: String): Set<LocalDate> {
        val feriados = readCsv(archivoDeFeriados)
        return feriados.map { it.first().toLocalDate() }.toSet()
    }

    override fun obtenerDiasHabilesEntre(desde: LocalDate, hasta: LocalDate): List<Dia> {
        val result: List<LocalDate> = crearDiasEntre(desde, hasta)
        val diasHabiles = filtrarDiasHabiles(result)
        val diasHabilesDesdeInicio = contarDiasHabilesDesdeInicio()

        return diasHabiles.mapIndexed { index, fecha ->
            Dia(
                fecha.dayOfWeek,
                diasHabilesDesdeInicio + index,
                fecha,
                numeroDeSemana(fecha)
            )
        }
    }

    override fun obtenerCalendarioEntre(desde: LocalDate, hasta: LocalDate): Calendario {
        val result: List<LocalDate> = crearDiasEntre(desde, hasta)
        val dias = result.mapIndexed { index, fecha ->
            Dia(
                fecha.dayOfWeek,
                index,
                fecha,
                numeroDeSemana(fecha)
            )
        }
        return Calendario(dias)
    }

    private fun numeroDeSemana(fecha: LocalDate): Int {
        val nroDeSemana = ChronoUnit.WEEKS.between(inicioDelAño, fecha)
        return nroDeSemana.toInt() + 1
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

}