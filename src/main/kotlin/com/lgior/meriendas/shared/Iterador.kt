package com.lgior.meriendas.shared

class Iterador<T : Any>(private val valores: List<T>) {
    private var copy: MutableList<T> = valores.toMutableList()

    fun next(): T {
        return copy.first().also { sendToLastPosition(it) }
    }

    fun proximoQueCumpla(restriccion: Restriccion<T>): T {
        val first = copy.first { restriccion.seCumplePara(it) }
        return first.also { sendToLastPosition(it) }
    }

    private fun sendToLastPosition(it: T) {
        println("Status pre update = $copy")
        copy.remove(it)
        copy.add(it)
        if (copy.isEmpty()) {
            copy = valores.toMutableList()
        }
        println("Status after update = $copy")
    }

    fun tomarElMenorSegun(function: (T) -> Int): T {
        val proximo = copy.minBy(function)
        return proximo.also { sendToLastPosition(proximo)}
    }

    override fun toString(): String {
        return copy.toString()
    }
}