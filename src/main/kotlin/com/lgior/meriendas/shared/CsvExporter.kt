package com.lgior.meriendas.shared

import com.lgior.meriendas.Merienda

object CsvExporter {
    fun export(meriendas: List<Merienda>) {
        val header = buildHeader()
        val lines = meriendas.map { buildLine(it) }
        writeFile(header + lines + footer())
    }

    private fun buildHeader(): List<String> {
        return listOf("Responsable;Preparación;Fecha")
    }

    private fun footer() : List<String> {
        return listOf(
        ";Las preparaciones deben ser vegetarianas (sin carne, jamón o pescado);",
        ";Se agradece mucho incorporar una opción vegana para Sathya o avisar con tiempo a Cecilia 11-6532-4391.;",
        ";Ademas de la merienda, llevar 2 kilos de fruta de estacion lavada.;",
        ";Si la merienda es caliente por favor avisar al momento de entregarla.;",
        ";Si alguna familia por alguna razon no puede llevar la merienda por favor avisar con tiempo asi los cubrimos de alguna manera.;",
        )
    }

    private fun writeFile(lines: List<String>) {
        lines.forEach{
            println(it)
        }
    }

    private fun buildLine(merienda: Merienda): String {
        return "Familia de ${merienda.responsable.niñe}; ${merienda.preparacion}; ${merienda.fecha.nombre()} ${formatter.format(merienda.fecha.date)}"

    }

}