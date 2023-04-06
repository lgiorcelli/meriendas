package com.lgior.meriendas

import com.lgior.meriendas.dias.ServicioDeDiasDefault
import com.lgior.meriendas.familias.ServicioFamiliasDefault
import com.lgior.meriendas.meriendas.ServicioPreparacionesDefault
import com.lgior.meriendas.shared.CsvExporter
import com.lgior.meriendas.shared.toLocalDate


fun main(args: Array<String>) {

    val servicioDeDias = ServicioDeDiasDefault(
        "13 Mar 2023".toLocalDate(),
        "/Users/workia/sources/mio/meriendas/src/main/resources/feriados.txt"
    )
    val servicioFamilias =
        ServicioFamiliasDefault("/Users/workia/sources/mio/meriendas/src/main/resources/familias_1.csv")
    val servicioPreparaciones = ServicioPreparacionesDefault.default()
    val calculadorDeMerienda = CalculadorDeMerienda(servicioDeDias, servicioFamilias, servicioPreparaciones)

    val meriendas =
        calculadorDeMerienda.calcularMerienda("13 Mar 2023".toLocalDate(), "31 May 2023".toLocalDate())

    CsvExporter.export(meriendas)
}