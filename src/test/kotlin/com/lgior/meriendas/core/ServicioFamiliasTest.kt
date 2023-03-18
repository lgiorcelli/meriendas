package com.lgior.meriendas.core

import com.lgior.meriendas.familias.ServicioFamiliasDefault
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ServicioFamiliasTest {

    @Test
    fun `leer las familias desde un csv`() {
        val fileName = "/Users/workia/sources/mio/meriendas/src/test/resources/familias_1.csv"
        val servicio = ServicioFamiliasDefault(fileName)

        val familias = servicio.obtenerTodasLasFamilias()

        assertEquals(18, familias.size)
        assertEquals(18, familias.toSet().size)
    }
}

