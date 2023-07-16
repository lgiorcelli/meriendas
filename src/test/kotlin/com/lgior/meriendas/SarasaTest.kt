package com.lgior.meriendas

import org.junit.jupiter.api.Test
import java.io.File
import java.text.DecimalFormat
import java.text.NumberFormat
import kotlin.io.path.writeLines
import kotlin.random.Random

class SarasaTest {
    @Test
    fun name() {
        val suffix = "oca"
        val resource = this::class.java.getResource("/legajos_$suffix.txt")
        val file = File(resource.file)
        val inputStream = file.inputStream()
        val reader = inputStream.bufferedReader()
        val lines = reader.lineSequence()
            .filter { it.isNotBlank() }
            .flatMap {
                generarFichadasParaElDia(it, 1)
            }
            .toList()

        println("Total lineas: ${lines.size}")


        File("fichadas_$suffix.txt").toPath().writeLines(
            lines
        )

        File("fichadas_shuffled_$suffix.txt").toPath().writeLines(
            lines.shuffled()
        )
    }

    private val ID_CLOCK = 22
    private val IN_CODE = "00"
    private val OUT_CODE = "01"
    private val format: NumberFormat = DecimalFormat("00")

    //0589 04/03/2022 07:35 31 00
    private fun generarFichadasParaElDia(legajo: String, cantidadDeFichadas: Int): List<String> {
        val result = mutableListOf<String>()
        (1..31).map {
            val date = "${format.format(it)}/05/2019"
            println("Procesando fichadas para el dia $date")
            result.addAll(crearFichadasIngreso(cantidadDeFichadas, legajo, date))
            result.addAll(crearFichadaEgreso(cantidadDeFichadas, legajo, date))
        }
        return result
    }

    private fun crearFichadaEgreso(
        cantidadDeFichadas: Int,
        legajo: String,
        dateAsText: String
    ): List<String> {
        val egresos = (1..cantidadDeFichadas).map {
            val minutosEgreso = Random.nextInt(0, 50)
            val horaEgreso = listOf(0, 0, 0, 0, -1, 1).random() + 18
            val minutesOut = format.format(minutosEgreso)
            legajo.trim() + " $dateAsText $horaEgreso:$minutesOut $ID_CLOCK $OUT_CODE"
        }.toList()
        return egresos
    }

    private fun crearFichadasIngreso(
        cantidadDeFichadas: Int,
        legajo: String,
        dateAsText: String
    ): List<String> {
        val ingresos = (1..cantidadDeFichadas).map {
            val deltaMinutos = Random.nextInt(0, 50)
            val horaIngreso = listOf(0, 0, 0, 0, -1, 1).random() + 9
            val hourIn = format.format(horaIngreso)
            val minutesIn = format.format(deltaMinutos)
            legajo.trim() + " $dateAsText $hourIn:$minutesIn $ID_CLOCK $IN_CODE"
        }.toList()
        return ingresos
    }
}