package com.lgior.meriendas.familias

interface ServicioFamilias {
    fun obtenerFamiliaAAsignar(dia: Int): Familia
    fun obtenerTodasLasFamilias() : List<Familia>
}