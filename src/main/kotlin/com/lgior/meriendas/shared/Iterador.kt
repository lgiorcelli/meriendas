package com.lgior.meriendas.shared

class Iterador<T : Any>(private val valores: List<T>) {
    private val logEnabled: Boolean = false
    private var copy: MutableList<T> = valores.toMutableList()

    fun next(): T {
        return copy.first().also { sendToLastPosition(it) }
    }

    fun proximoQueCumpla(restriccion: Restriccion<T>): T {
        val first = copy.first { restriccion.seCumplePara(it) }
        return first.also { sendToLastPosition(it) }
    }

    private fun sendToLastPosition(it: T) {
        logPreviousStatus()
        copy.remove(it)
        copy.add(it)
        if (copy.isEmpty()) {
            println("------ Refilling iterator -----")
            copy = valores.toMutableList()
        }
        logAfterStatus()
    }

    private fun logAfterStatus() {
        if (logEnabled)
            println("Status after update = $copy")
    }

    private fun logPreviousStatus() {
        if (logEnabled)
            println("Status pre update = $copy")
    }

    fun tomarElMenorSegun(function: (T) -> Int): T {
        val proximo = copy.minBy(function)
        return proximo.also { sendToLastPosition(proximo)}
    }

    override fun toString(): String {
        return copy.toString()
    }
}