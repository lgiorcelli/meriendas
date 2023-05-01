package com.lgior.meriendas.dias

/*
* Esta clase tiene la responsabilidad de organizar los dias del calendario, registrar los feriados
* */
data class Calendario(val dias: List<Dia>) {

    fun diasTotal() : Int {
        return dias.size
    }

    fun obtenerSemana(nroDeSemana: Int): List<Dia> {
        return dias.filter { dia -> dia.numeroDeSemana == nroDeSemana }
    }
}
