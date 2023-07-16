package com.lgior.meriendas.familias

import java.io.File

class ServicioFamiliasDefault(private val familias: List<Familia>) : ServicioFamilias {
    constructor(fileName: String) : this(readCsv())



    override fun obtenerFamiliaAAsignar(diasDesdeElInicoDeClases: Int): Familia {
        val posicion = diasDesdeElInicoDeClases.mod(familias.size)
        return familias[posicion]
    }

    override fun obtenerTodasLasFamilias(): List<Familia> {
        return familias
    }

    companion object {
        fun readCsv(): List<Familia> {
            val resource = this::class.java.getResource("/familias_1.csv")
            val file = File(resource.file)
            val inputStream = file.inputStream()
            val reader = inputStream.bufferedReader()
            return reader.lineSequence()
                .filter { it.isNotBlank() }
                .map { Familia(it.split(";")[0]) }
                .toList()
        }

    }
}