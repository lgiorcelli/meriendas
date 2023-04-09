package com.lgior.meriendas.core

import com.lgior.meriendas.shared.Iterador
import com.lgior.meriendas.shared.Restriccion
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class IteradorTest {
    @Test
    fun `asignar elementos a medida que se los voy pidiendo`() {

        val valores = listOf(1, 2, 3)
        val iterador = Iterador(valores)

        var value = iterador.next()
        assertEquals(1, value)

        value = iterador.next()
        assertEquals(2, value)

        value = iterador.next()
        assertEquals(3, value)

        value = iterador.next()
        assertEquals(1, value)
    }

    @Test
    fun `debe poder entregar valores cumpliendo las expectativas`() {
        val valores = listOf(1, 2, 3, 4)
        val iterador = Iterador(valores)

        val soloPares = SoloPares()

        var proximo = iterador.proximoQueCumpla(soloPares)
        assertEquals(2, proximo)

        proximo = iterador.proximoQueCumpla(soloPares)
        assertEquals(4, proximo)
    }
}

class SoloPares : Restriccion<Int> {
    override fun seCumplePara(valor: Int): Boolean {
        return (valor % 2) == 0
    }

}

