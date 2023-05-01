package com.lgior.meriendas.core

import com.lgior.meriendas.familias.ServicioFamiliasDefault
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ServicioFamiliasTest {

    @Test
    fun `leer las familias desde un csv`() {
        val expectedFlias = 17
        val fileName = "/Users/workia/sources/mio/meriendas/src/test/resources/familias_1.csv"
        val servicio = ServicioFamiliasDefault(fileName)

        val familias = servicio.obtenerTodasLasFamilias()

        assertEquals(expectedFlias, familias.size)
        assertEquals(expectedFlias, familias.toSet().size)
    }
}

