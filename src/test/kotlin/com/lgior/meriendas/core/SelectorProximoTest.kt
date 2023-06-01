package com.lgior.meriendas.core

import com.lgior.meriendas.familias.Familia
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SelectorProximoTest {

    val familiasEnOrden = TestMother.unaListaDeFamilias(4)
    val familiaAlvarez = familiasEnOrden[0]
    val familiaBlanco = familiasEnOrden[1]
    val familiaCastro = familiasEnOrden[2]
    val familiaDominguez = familiasEnOrden[3]

    private lateinit var historicoDePreparaciones: HistoricoDePreparaciones
    @BeforeEach
    fun setUp() {
        historicoDePreparaciones = DefaultHistoricoDePreparaciones(familiasEnOrden)
    }

    @Test
    fun `tengo que elegir familia en base al orden y a una regla de meriendas ya preparadas`() {
        setUpEscenario1()

        val preparaArroz = obtenerProximaFamilia(Preparacion.Arroz, familiasEnOrden, historicoDePreparaciones)
        Assertions.assertEquals(familiaBlanco, preparaArroz)

        val preparaCebada = obtenerProximaFamilia(Preparacion.Cebada, familiasEnOrden, historicoDePreparaciones)
        Assertions.assertEquals(familiaAlvarez, preparaCebada)

        val preparaMijo = obtenerProximaFamilia(Preparacion.Mijo, familiasEnOrden, historicoDePreparaciones)
        Assertions.assertEquals(familiaCastro, preparaMijo)

        val preparaCenteno = obtenerProximaFamilia(Preparacion.Centeno, familiasEnOrden, historicoDePreparaciones)
        Assertions.assertEquals(familiaAlvarez, preparaCenteno)

        val preparaAvena = obtenerProximaFamilia(Preparacion.Avena, familiasEnOrden, historicoDePreparaciones)
        Assertions.assertEquals(familiaAlvarez, preparaAvena)
    }

    private fun setUpEscenario1() {
        //Flia      |   Arroz   |   Cebada  | mijo  | Centeno   | Avena
        //Alvarez   |   1       |   0       |   1   |   0       |   0
        //Blanco    |   0       |   1       |   1   |   0       |   0
        //Castro    |   0       |   0       |   0   |   1       |   1
        //Dominguez |   1       |   0       |   0   |   1       |   0


        // dado q la flia Alvarez ya preparo Arroz y mijo
        historicoDePreparaciones.contarPreparacion(Preparacion.Arroz, familiaAlvarez)
        historicoDePreparaciones.contarPreparacion(Preparacion.Mijo, familiaAlvarez)

        // dado q la flia Blanco ya preparo Cebada y Mijo
        historicoDePreparaciones.contarPreparacion(Preparacion.Cebada, familiaBlanco)
        historicoDePreparaciones.contarPreparacion(Preparacion.Mijo, familiaBlanco)

        // dado q la flia Castro ya preparo Avena y Centeno
        historicoDePreparaciones.contarPreparacion(Preparacion.Avena, familiaCastro)
        historicoDePreparaciones.contarPreparacion(Preparacion.Centeno, familiaCastro)
        // dado q la flia Dominguez ya preparo Arroz y Centeno
        historicoDePreparaciones.contarPreparacion(Preparacion.Arroz, familiaDominguez)
        historicoDePreparaciones.contarPreparacion(Preparacion.Centeno, familiaDominguez)

        // En este caso cuento solamente por quien preparo menos de una preparacion, las flias restantes para una vuelta no las estoy teniendo en cuenta,
        // eso podria controlarse antes y darle un listado con las flias q aun no fueron asignadas

        // Como siguiente linea de pensamiento, podemos pensar, "dado una preparacion, cual es la flia mas adecuada para prepararla" en vez de tratar de meter
        // a una flia en un dia en particular

    }

    @Test
    fun `Elegir la proxima familia en base a lo que ya preparo (sin tener en cuenta el total)`() {
        setUpEscenario2()

        val preparaArroz = obtenerProximaFamilia(Preparacion.Arroz, familiasEnOrden, historicoDePreparaciones)
        Assertions.assertEquals(familiaCastro, preparaArroz)

        val preparaCebada = obtenerProximaFamilia(Preparacion.Cebada, familiasEnOrden, historicoDePreparaciones)
        Assertions.assertEquals(familiaAlvarez, preparaCebada)

        val preparaMijo = obtenerProximaFamilia(Preparacion.Mijo, familiasEnOrden, historicoDePreparaciones)
        Assertions.assertEquals(familiaDominguez, preparaMijo)

        val preparaCenteno = obtenerProximaFamilia(Preparacion.Centeno, familiasEnOrden, historicoDePreparaciones)
        Assertions.assertEquals(familiaAlvarez, preparaCenteno)

        val preparaAvena = obtenerProximaFamilia(Preparacion.Avena, familiasEnOrden, historicoDePreparaciones)
        Assertions.assertEquals(familiaDominguez, preparaAvena)
    }

    private fun setUpEscenario2() {
        //Flia      |   Arroz   |   Cebada  | mijo  | Centeno   | Avena |
        //Alvarez   |   2       |   1       |   4   |   0       |   1   |
        //Blanco    |   1       |   1       |   3   |   0       |   1   |
        //Castro    |   0       |   2       |   2   |   0       |   1   |
        //Dominguez |   1       |   3       |   1   |   0       |   0   |


        //ARROZ
        val arroz = Preparacion.Arroz
        historicoDePreparaciones.contarPreparacion(arroz, familiaAlvarez)
        historicoDePreparaciones.contarPreparacion(arroz, familiaAlvarez)
        historicoDePreparaciones.contarPreparacion(arroz, familiaBlanco)
        historicoDePreparaciones.contarPreparacion(arroz, familiaDominguez)

        //CEBADA
        val cebada = Preparacion.Cebada
        historicoDePreparaciones.contarPreparacion(cebada, familiaAlvarez)
        historicoDePreparaciones.contarPreparacion(cebada, familiaBlanco)
        historicoDePreparaciones.contarPreparacion(cebada, familiaCastro)
        historicoDePreparaciones.contarPreparacion(cebada, familiaCastro)
        historicoDePreparaciones.contarPreparacion(cebada, familiaDominguez)
        historicoDePreparaciones.contarPreparacion(cebada, familiaDominguez)
        historicoDePreparaciones.contarPreparacion(cebada, familiaDominguez)


        //MIJO

        val mijo = Preparacion.Mijo
        historicoDePreparaciones.contarPreparacion(mijo, familiaAlvarez)
        historicoDePreparaciones.contarPreparacion(mijo, familiaAlvarez)
        historicoDePreparaciones.contarPreparacion(mijo, familiaAlvarez)
        historicoDePreparaciones.contarPreparacion(mijo, familiaAlvarez)
        historicoDePreparaciones.contarPreparacion(mijo, familiaBlanco)
        historicoDePreparaciones.contarPreparacion(mijo, familiaBlanco)
        historicoDePreparaciones.contarPreparacion(mijo, familiaBlanco)
        historicoDePreparaciones.contarPreparacion(mijo, familiaCastro)
        historicoDePreparaciones.contarPreparacion(mijo, familiaCastro)
        historicoDePreparaciones.contarPreparacion(mijo, familiaDominguez)

        //CENTENO
        //NADA

        //AVENA
        val avena = Preparacion.Avena
        historicoDePreparaciones.contarPreparacion(avena, familiaAlvarez)
        historicoDePreparaciones.contarPreparacion(avena, familiaBlanco)
        historicoDePreparaciones.contarPreparacion(avena, familiaCastro)
    }

    private fun obtenerProximaFamilia(preparacion: Preparacion, familias: List<Familia>, xxx: HistoricoDePreparaciones): Familia {
        val menosVecesPrepararon = xxx.familiasQueMenosVecesPrepararon(familias, preparacion)
        return menosVecesPrepararon.minBy { xxx.vecesQuePreparo(preparacion, it) }
    }
}