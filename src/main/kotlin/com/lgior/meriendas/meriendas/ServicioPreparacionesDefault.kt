package com.lgior.meriendas.meriendas

import com.lgior.meriendas.dias.Dia
import com.lgior.meriendas.dias.Dias
import java.time.DayOfWeek

class ServicioPreparacionesDefault(
    private val cerealesDesignados: Map<DayOfWeek, String>
) : ServicioPreparaciones {

    override fun preparacionPara(dia: Dia): String {
        return cerealesDesignados[dia.dayOfWeek] ?: throw RuntimeException("Falta definir el ceral para el dia $dia")
    }

    companion object {
        fun default(): ServicioPreparaciones {

            val cereales = mapOf(
                Dias.LUNES.dayOfWeek to "Arroz",
                Dias.MARTES.dayOfWeek to "Cebada",
                Dias.MIERCOLES.dayOfWeek to "Mijo",
                Dias.JUEVES.dayOfWeek to "Centeno",
                Dias.VIERNES.dayOfWeek to "Avena"
            )
            return ServicioPreparacionesDefault(cereales)
        }
    }

}