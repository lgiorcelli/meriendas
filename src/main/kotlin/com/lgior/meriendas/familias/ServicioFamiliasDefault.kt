package com.lgior.meriendas.familias

import java.io.File

class ServicioFamiliasDefault(fileName: String) : ServicioFamilias {
    private val familias: List<Familia>

    init {
        this.familias = readCsv(fileName)
    }

    override fun obtenerFamiliaAAsignar(diasDesdeElInicoDeClases: Int): Familia {
        val posicion = diasDesdeElInicoDeClases.mod(familias.size)
        return familias[posicion]
    }

    override fun obtenerTodasLasFamilias(): List<Familia> {
        return familias
    }

    private fun readCsv(fileName: String): List<Familia> {
        val file = File(fileName)
        val inputStream = file.inputStream()
        val reader = inputStream.bufferedReader()
        return reader.lineSequence()
            .filter { it.isNotBlank() }
            .map { Familia(it.split(";")[0]) }
            .toList()
    }
}