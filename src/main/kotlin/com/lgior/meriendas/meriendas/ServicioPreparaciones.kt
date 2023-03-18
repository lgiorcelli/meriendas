package com.lgior.meriendas.meriendas

import com.lgior.meriendas.dias.Dia


interface ServicioPreparaciones {
    fun preparacionPara(dia: Dia): String
}