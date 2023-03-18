package com.lgior.meriendas.shared

import com.lgior.meriendas.Merienda

object CsvExporter {
    fun export(meriendas: List<Merienda>) {
        val header = buildHeader()
        val lines = meriendas.map { buildLine(it) }
        writeFile(header + lines)
    }

    private fun buildHeader(): List<String> {
        return listOf("Responsable;Preparación;Fecha")
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