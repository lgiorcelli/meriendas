package com.lgior.meriendas.dias

data class Calendario(val dias: List<Dia>) {

    fun diasTotal() : Int {
        return dias.size
    }

    fun obtenerSemana(nroDeSemana: Int): List<Dia> {
        return dias.filter { dia -> dia.numeroDeSemana == nroDeSemana }
    }
}
