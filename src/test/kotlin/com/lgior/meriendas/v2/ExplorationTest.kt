package com.lgior.meriendas.v2

import com.lgior.meriendas.shared.FileReader
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ExplorationTest {
    @Test
    fun name() {
        val familias = FileReader.readCsv()
        val iterador = Iterador(familias)

        val fixture = Fixture.desde(LocalDate.of(2023, 7, 16))
        for (i in 1..30) {
            val next = iterador.next()
            fixture.acomodar(next)
        }

        println(fixture)
    }
}