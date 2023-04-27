package com.lgior.meriendas.core

import com.lgior.meriendas.CalculadorDeMerienda
import com.lgior.meriendas.dias.ServicioDeDias
import com.lgior.meriendas.dias.ServicioDeDiasDefault
import com.lgior.meriendas.familias.ServicioFamilias
import com.lgior.meriendas.familias.ServicioFamiliasDefault
import com.lgior.meriendas.meriendas.ServicioPreparaciones
import com.lgior.meriendas.meriendas.ServicioPreparacionesDefault
import com.lgior.meriendas.shared.toLocalDate
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

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
        //Lunes: Lopez
        //Martes: Repetidez
        //Miercoles: Miranda
        //Jueves: Jimenez
        //Viernes: Vicario
        //Lunes: -> Debe elegir Repetidez
        //Martes -> Lopez
        //Miercoles -> Jimenez

        //Familias: Lopez, Repetidez, Miranda, Jimenez, Vicario
        //Lunes: Lopez
        //Martes: Repetidez
        //Miercoles: Miranda
        //Jueves: Jimenez
        //Viernes: Vicario
        //-------------------
        //Lunes: Repetidez
        //Martes: Lopez
        //Miercoles: Jimenez
        //Jueves: Miranda
        //Viernes: Lopez
        //-------------------
        //Lunes: Vicario
        //Martes: Miranda
        //Miercoles: Repetidez
        //Jueves: Vicario
        //Viernes:


        //dado un servicio de familias
        //donde la familia Repetidez ya preaparo cebada
        //dado que aun una familia no preparo cebada
        //dado que toca preparar cebada
        //cuando calculo la proxima merienda

        //se debe seleccionar a la familia Ceballo para la cebada
        //se debe seleccionar a la familia Repetidez para preparar mijo
    }
}


