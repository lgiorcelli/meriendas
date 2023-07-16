package com.lgior.meriendas.shared

import com.lgior.meriendas.familias.Familia
import java.io.File

object FileReader {

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
