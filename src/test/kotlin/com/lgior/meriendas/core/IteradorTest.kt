package com.lgior.meriendas.core

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
}

class Iterador<T>(private val valores: List<T>) {
    private var counter = 0
    fun next(): T {
        val index = counter.mod(valores.size)
        return valores[index].also { counter++ }
    }

}