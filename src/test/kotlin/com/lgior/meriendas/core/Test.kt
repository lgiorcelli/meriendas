package com.lgior.meriendas.core

import com.lgior.meriendas.CalculadorDeMerienda
import com.lgior.meriendas.dias.ServicioDeDias
import com.lgior.meriendas.dias.ServicioDeDiasDefault
import com.lgior.meriendas.familias.Familia
import com.lgior.meriendas.familias.ServicioFamilias
import com.lgior.meriendas.familias.ServicioFamiliasDefault
import com.lgior.meriendas.meriendas.ServicioPreparaciones
import com.lgior.meriendas.meriendas.ServicioPreparacionesDefault
import com.lgior.meriendas.shared.toLocalDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.concurrent.atomic.AtomicInteger

class Test {

    /*
    - Cargar un calendario con los dias habiles, feriados y no escolares
    - Repartir las familias para cubrir todos los dias habiles del colegio
    Tengo un mes "teorico" generado con LocalDate
    Tengo un proveedor de feriados q puede ser extendido facilmente
    Un listado de las familias a participar en las comidas (tambien puede cambiar)
    Un cereal valido para cada dia de la semana
    [Puedo tener las recetas que cargamos en el excel]
    Genera un reporte q tenga: dia del mes, familia, cereal (+receta)
    [puede incluir los cumpleaños]
    [sumar dias de jornada facilmente]
    [Como controlamos que los platos esten bien distribuidos?]


    -
     */

    @Mock
    lateinit var servicioDeDias: ServicioDeDias

    lateinit var servicioFamilias: ServicioFamilias

    lateinit var servicioPreparaciones: ServicioPreparaciones

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        unServicioDeFamiliasConfigurado()
        servicioPreparaciones = ServicioPreparacionesDefault.default()
        servicioDeDias = ServicioDeDiasDefault(
            "10 Mar 2023".toLocalDate(),
            "/Users/workia/sources/mio/meriendas/src/main/resources/feriados.txt"
        )
    }

    private fun unServicioDeFamiliasConfigurado() {
        val fileName = "/Users/workia/sources/mio/meriendas/src/main/resources/familias_1.csv"
        servicioFamilias = ServicioFamiliasDefault(fileName)
    }

    @Test
    fun `procesar una semana de meriendas entre 2 fechas definidas`() {
        val calculadorDeMerienda =
            CalculadorDeMerienda(servicioDeDias, servicioFamilias, servicioPreparaciones)
        val inicio = "10 Mar 2023".toLocalDate()

        println("inicioDeClases = ${inicio.dayOfMonth}")
        println("inicio.dayOfWeek.name = ${inicio.dayOfWeek.name}")

        val unaSemanaDespues = inicio.plusWeeks(1)
        println("unaSemanaDespues = ${unaSemanaDespues}")

        val meriendas = calculadorDeMerienda.calcularMerienda(inicio, unaSemanaDespues)

        assertEquals(6, meriendas.size)
        println(meriendas.joinToString(System.lineSeparator()))
    }

    @Test
    fun `repartir las preparaciones entre todas las familias`() {
        //Familias: Alvarez, Blanco, Castro, Dominguez, Escobar, Fernandez, Gonzalez, Herrera, Ibañez, Jimenez
        //Lunes: Alvarez
        //Martes: Blanco
        //Miercoles: Castro
        //Jueves: Dominguez
        //Viernes: Escobar

        //Lunes: -> Fernandez
        //Martes -> Gonzalez
        //Miercoles -> Herrera
        //Jueves -> Ibañez
        //Viernes -> Jimenez

        //Lunes -> Alvarez <- Ya cocino un lunes, tiene q rotar

        //dado un servicio de familias
        servicioFamilias = ServicioFamiliasDefault(TestMother.unaListaDeFamilias())

        //dado que aun una familia no preparo cebada
        //dado que toca preparar cebada
        //cuando calculo la proxima merienda

        //se debe seleccionar a la familia Ceballo para la cebada
        //se debe seleccionar a la familia Repetidez para preparar mijo
    }


}

interface HistoricoDePreparaciones {
    fun contarPreparacion(preparacion: Preparacion, familia: Familia)
    fun vecesQuePreparo(preparacion: Preparacion, familia: Familia): Int
    fun familiasQueMenosVecesPrepararon(familias: List<Familia>, preparacion: Preparacion): List<Familia>

}

class DefaultHistoricoDePreparaciones(familias: List<Familia>) : HistoricoDePreparaciones {
    private val contadoresPorPreparaciones: MutableMap<Preparacion, MutableMap<Familia, AtomicInteger>> = mutableMapOf(
        Preparacion.Arroz to familias.associateWith { AtomicInteger(0) }.toMutableMap(),
        Preparacion.Cebada to familias.associateWith { AtomicInteger(0) }.toMutableMap(),
        Preparacion.Mijo to familias.associateWith { AtomicInteger(0) }.toMutableMap(),
        Preparacion.Centeno to familias.associateWith { AtomicInteger(0) }.toMutableMap(),
        Preparacion.Avena to familias.associateWith { AtomicInteger(0) }.toMutableMap(),
    )
    private val contadorDePreparacionesPorFamilia: MutableMap<Preparacion, MutableMap<Familia, Int>> = mutableMapOf()
    private val totalesPorFamilia: MutableMap<Familia, Int> = mutableMapOf()
    override fun contarPreparacion(preparacion: Preparacion, familia: Familia) {
        val contadorPorFamilia = contadorDePreparacionesPorFamilia[preparacion]
        if (contadorPorFamilia == null) {
            contadorDePreparacionesPorFamilia[preparacion] = mutableMapOf(familia to 1)
        } else {
            val veces = contadorPorFamilia[familia] ?: 0
            contadorPorFamilia[familia] = veces.inc()
        }

        incrementTotals(familia)

        contadoresPorPreparaciones[preparacion]?.get(familia)?.incrementAndGet()
    }

    private fun incrementTotals(familia: Familia) {
        val totales = totalesPorFamilia[familia] ?: 0
        totalesPorFamilia[familia] = totales.inc()
    }

    override fun vecesQuePreparo(preparacion: Preparacion, familia: Familia): Int {
        return contadorDePreparacionesPorFamilia[preparacion]?.get(familia) ?: 0
    }

    override fun familiasQueMenosVecesPrepararon(familias: List<Familia>, preparacion: Preparacion): List<Familia> {
        val contadorPorPreparacion =
            contadoresPorPreparaciones[preparacion] ?: throw RuntimeException("No hay contador para $preparacion")
        return contadorPorPreparacion.keys.sortedBy { getValueOf(contadorPorPreparacion, it) }
    }

    private fun getValueOf(
        contadorPorPreparacion: MutableMap<Familia, AtomicInteger>,
        familia: Familia
    ): Int {
        return contadorPorPreparacion[familia]?.get()
            ?: throw RuntimeException("No hay numeros registrados para $familia")
    }

}


object TestMother {
    fun unaListaDeFamilias(tamanio: Int = 10): List<Familia> {
        return listOf(
            Familia("Andrea Alvarez"),
            Familia("Beatriz Blanco"),
            Familia("Carlos Castro"),
            Familia("Damian Dominguez"),
            Familia("Estela Escobar"),
            Familia("Fabian Fernandez"),
            Familia("Gaston Gonzalez"),
            Familia("Hernan Herrera"),
            Familia("Ines Ibañez"),
            Familia("Jose Jimenez"),
        ).take(tamanio)
    }
}