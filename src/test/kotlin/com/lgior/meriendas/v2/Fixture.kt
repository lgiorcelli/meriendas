package com.lgior.meriendas.v2

import com.lgior.meriendas.familias.Familia
import java.time.LocalDate


class Fixture(private val fechaInicial: LocalDate) {

    private val meriendas = mutableListOf<Merienda>()
    private var fechaActual: LocalDate = fechaInicial

    fun acomodar(familia: Familia) {
        fechaActual = obtenerProximaFechaAAsignar()
        val vacante = obtenerProximoMeriendaVacante(fechaActual)
        vacante.asignar(familia)
    }

    private fun obtenerProximaFechaAAsignar(): LocalDate {
        return fechaActual.plusDays(1)
    }


    private fun obtenerProximoMeriendaVacante(fecha: LocalDate): Merienda {
        val merienda = Merienda(fecha)
        meriendas.add(merienda)
        return merienda
    }

    override fun toString(): String {
        return meriendas.joinToString(System.lineSeparator())
    }

    companion object {
        fun desde(inicio: LocalDate): Fixture {
            return Fixture(inicio)
        }
    }
}

data class Merienda(val dia: LocalDate) {
    private lateinit var familia: Familia

    fun asignar(familia: Familia) {
        this.familia = familia
    }

    override fun toString(): String {
        return "${dia.dayOfWeek}  $dia - Familia: ${familia.niñe}"
    }
}