package com.lgior.meriendas

import com.lgior.meriendas.dias.Dia
import com.lgior.meriendas.dias.ServicioDeDias
import com.lgior.meriendas.familias.ServicioFamilias
import com.lgior.meriendas.meriendas.ServicioPreparaciones
import java.time.LocalDate

class CalculadorDeMerienda(
    private val servicioDeDias: ServicioDeDias,
    private val servicioDeFamilia: ServicioFamilias,
    private val servicioDePreparaciones: ServicioPreparaciones
) {

    fun calcularMerienda(desde: LocalDate, hasta: LocalDate): List<Merienda> {
        val dias = servicioDeDias.obtenerDiasEntre(desde, hasta)

        return dias.mapIndexed { index, dia ->  definirMerienda(dia, index) }
    }

    private fun definirMerienda(dia: Dia, diasDesdeInicioDeClases: Int): Merienda {
        val responsable = servicioDeFamilia.obtenerFamiliaAAsignar(diasDesdeInicioDeClases)
        //Aca salteamos los feriados, y evitamos definir familia
        val preparacion = servicioDePreparaciones.preparacionPara(dia)
        return Merienda(responsable, preparacion, dia)
    }

}