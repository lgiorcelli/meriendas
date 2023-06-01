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
        val suffix = "_oca"
        val resource = this::class.java.getResource("/legajos$suffix.txt")
        val file = File(resource.file)
        val inputStream = file.inputStream()
        val reader = inputStream.bufferedReader()
        val lines = reader.lineSequence()
            .filter { it.isNotBlank() }
            .flatMap { addData(it, 5) }
            .toList()

        println("Total lineas: ${lines.size}")


        File("fichadas$suffix.txt").toPath().writeLines(
            lines
        )

        File("fichadas_shuffled$suffix.txt").toPath().writeLines(
            lines.shuffled()
        )
    }
    //0589 04/03/2022 07:35 31 00
    private fun addData(legajo: String, cantidadDeFichadas: Int) : List<String> {
        val format: NumberFormat = DecimalFormat("00")
        val minutes = Random.nextInt(0, 50)
        val ingresos = (1..cantidadDeFichadas).map {
            legajo.trim() + " 23/05/2000 09:${format.format(minutes + it)} 22 00"
        }.toList()
        val egresos = (0..cantidadDeFichadas).map {
            legajo.trim() + " 23/05/2000 18:${format.format(minutes + it)} 22 01"
        }.toList()
        return (ingresos + egresos)
    }
}