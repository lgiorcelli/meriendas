package com.lgior.meriendas.shared

class Iterador<T>(private val valores: List<T>) {
    private var copy: MutableList<T> = valores.toMutableList()

    fun next(): T {
        return copy.first().also { ensureCopy(it) }
    }

    fun proximoQueCumpla(restriccion: Restriccion<T>): T {
        val first = copy.first { restriccion.seCumplePara(it) }
        return first.also { ensureCopy(it) }
    }

    private fun ensureCopy(it: T) {
        copy.remove(it)
        if (copy.isEmpty()) {
            copy = valores.toMutableList()
        }
    }

}