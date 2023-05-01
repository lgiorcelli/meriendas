package com.lgior.meriendas.core

import com.lgior.meriendas.CalculadorDeMerienda
import com.lgior.meriendas.dias.ServicioDeDias
import com.lgior.meriendas.dias.ServicioDeDiasDefault
import com.lgior.meriendas.familias.Familia
import com.lgior.meriendas.familias.ServicioFamilias
import com.lgior.meriendas.familias.ServicioFamiliasDefault
import com.lgior.meriendas.meriendas.ServicioPreparaciones
import com.lgior.meriendas.meriendas.ServicioPreparacionesDefault
import com.lgior.meriendas.shared.Iterador
import com.lgior.meriendas.shared.toLocalDate
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import kotlin.test.Ignore

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

        Assertions.assertEquals(6, meriendas.size)
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


    @Test
    @Ignore
    fun `tengo que elegir familia en base al orden y a una regla de meriendas ya preparadas`() {
        val familiasEnOrden = TestMother.unaListaDeFamilias(4)
        val iterador = Iterador(familiasEnOrden)

        val arroz = "Arroz"
        val cebada = "Cebada"
        val mijo = "Mijo"
        val centeno = "Centeno"
        val avena = "Avena"
        // dado que toca preparar Cebada

        val xxx = Mockito.mock(XXX::class.java)
        // dado q la flia Alvarez ya preparo Arroz y mijo
        whenever(xxx.vecesQuePreparo(anyString(), any())).thenReturn(0)
        val familiaAlvarez = familiasEnOrden[0]
        whenever(xxx.vecesQuePreparo(arroz, familiaAlvarez)).thenReturn(1)
        whenever(xxx.vecesQuePreparo(mijo, familiaAlvarez)).thenReturn(1)
        // dado q la flia Blanco ya preparo Cebada y Mijo
        val familiaBlanco = familiasEnOrden[1]
        whenever(xxx.vecesQuePreparo(cebada, familiaBlanco)).thenReturn(1)
        whenever(xxx.vecesQuePreparo(mijo, familiaBlanco)).thenReturn(1)
        // dado q la flia Castro ya preparo Avena y Centeno
        val familiaCastro = familiasEnOrden[2]
        whenever(xxx.vecesQuePreparo(avena, familiaCastro)).thenReturn(1)
        whenever(xxx.vecesQuePreparo(centeno, familiaCastro)).thenReturn(1)
        // dado q la flia Dominguez ya preparo Arroz y Centeno
        val familiaDominguez = familiasEnOrden[3]
        whenever(xxx.vecesQuePreparo(arroz, familiaDominguez)).thenReturn(1)
        whenever(xxx.vecesQuePreparo(centeno, familiaDominguez)).thenReturn(1)

        // Cuando busco el proximo candidato para cocinar: Arroz
        //la flia elegida es Blanco
        var proximo = obtenerProximoAsignadoPara(familiasEnOrden) { xxx.vecesQuePreparo(arroz, it) }
        Assertions.assertEquals(familiaBlanco, proximo)




        //(sin actualizar)
        // Cuando busco el proximo candidato para cocinar: Cebada
        //la flia elegida es Alvarez
        proximo = obtenerProximoAsignadoPara(familiasEnOrden) { xxx.vecesQuePreparo(cebada, it) }

        val menor = iterador.tomarElMenorSegun { xxx.vecesQuePreparo(centeno, it) }
        Assertions.assertEquals(familiaAlvarez, menor)

        //(sin actualizar)
        // Cuando busco el proximo candidato para cocinar: Mijo
        //la flia elegida es Castro

        proximo = obtenerProximoAsignadoPara(familiasEnOrden) { xxx.vecesQuePreparo(mijo, it) }
        proximo = iterador.tomarElMenorSegun { xxx.vecesQuePreparo(mijo, it) }
        Assertions.assertEquals(familiaCastro, proximo)

        //(sin actualizar)
        // Cuando busco el proximo candidato para cocinar: Centeno
        //la flia elegida es Alvarez
        proximo = obtenerProximoAsignadoPara(familiasEnOrden) { xxx.vecesQuePreparo(centeno, it) }
        proximo = iterador.tomarElMenorSegun { xxx.vecesQuePreparo(centeno, it) }
        Assertions.assertEquals(familiaAlvarez, proximo)

        //(sin actualizar)
        // Cuando busco el proximo candidato para cocinar: Avena
        //la flia elegida es Alvarez
        proximo = obtenerProximoAsignadoPara(familiasEnOrden) { xxx.vecesQuePreparo(avena, it) }
        proximo = iterador.tomarElMenorSegun { xxx.vecesQuePreparo(centeno, it) }
        Assertions.assertEquals(familiaAlvarez, proximo)

        println("iterador = ${iterador}")


    }

    private fun obtenerProximoAsignadoPara(familias: List<Familia>, function: (Familia) -> Int): Familia {
        return familias.minBy(function)
    }


}

interface XXX {
    fun vecesQuePreparo(preparacion: String, familia: Familia): Int

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