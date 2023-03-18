package com.lgior.meriendas.dias

import com.lgior.meriendas.shared.formatter
import com.lgior.meriendas.shared.toLocalDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.LocalDate

class ServicioDeDiasDefaultTest {

    private lateinit var servicio: ServicioDeDiasDefault

    @BeforeEach
    fun setUp() {
        servicio = ServicioDeDiasDefault(
            "10 Mar 2023".toLocalDate(),
            "/Users/workia/sources/mio/meriendas/src/main/resources/feriados.txt"
        )
    }

    @Test
    fun `retornar todos los dias de semana entre 2 fechas y los dias desde el inicio de clases`() {
        val desde = LocalDate.parse("10 Mar 2023", formatter)
        val hasta = LocalDate.parse("31 Mar 2023", formatter)

        val dias = servicio.obtenerDiasHabilesEntre(desde, hasta)

        assertEquals(15, dias.size)
        assertEquals(0, dias[0].diasDesdeInicioDeClases)
        assertEquals(1, dias[1].diasDesdeInicioDeClases)
        assertEquals(2, dias[2].diasDesdeInicioDeClases)
    }

    @Test
    fun `contar correctamente los dias para meses diferentes`() {
        val desde = "10 Mar 2023".toLocalDate()
        val hasta = "10 May 2023".toLocalDate()
        val dias = servicio.obtenerDiasHabilesEntre(desde, hasta)

        assertEquals(43, dias.size)

        val inicio = dias[0]
        assertEquals(10, inicio.numeroDeSemana)
        assertEquals(DayOfWeek.FRIDAY, inicio.dayOfWeek)
        assertEquals(desde, inicio.date)

        val unaSemanaHabilDespues = dias[4]
        assertEquals("16 Mar 2023".toLocalDate(), unaSemanaHabilDespues.date)
        assertEquals(DayOfWeek.THURSDAY, unaSemanaHabilDespues.dayOfWeek)
        assertEquals(11, unaSemanaHabilDespues.numeroDeSemana)

    }

    @Test
    fun `marcar como feriados los dias asi definidos`() {
        servicio = ServicioDeDiasDefault(
            "10 Mar 2023".toLocalDate(),
            "/Users/workia/sources/mio/meriendas/src/main/resources/feriados.txt"
        )
    }
}