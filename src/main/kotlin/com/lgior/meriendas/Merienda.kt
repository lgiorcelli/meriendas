package com.lgior.meriendas

import com.lgior.meriendas.dias.Dia
import com.lgior.meriendas.familias.Familia

data class Merienda(val responsable: Familia, val preparacion: String, val fecha: Dia) {
    override fun toString(): String {
        return "Familia: ${responsable.niñe} " +
                "preparacion: $preparacion " +
                "fecha: ${fecha.nombre()}"
    }
}