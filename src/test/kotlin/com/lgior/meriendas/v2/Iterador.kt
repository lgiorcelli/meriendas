package com.lgior.meriendas.v2

import com.lgior.meriendas.familias.Familia

class Iterador(private val familias: List<Familia>) {
    private var index = 0
    fun next(): Familia {
        return familias[index]
            .also { increaseIndex() }
    }

    private fun increaseIndex() {
        if (index == familias.size - 1) {
            println("----- Reset index ------")
            index = 0
        } else {
            index++
        }
    }

}