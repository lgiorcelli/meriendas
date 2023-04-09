package com.lgior.meriendas.shared

interface Restriccion<T> {
    fun seCumplePara(valor: T): Boolean
}