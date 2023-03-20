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
        val calendario = servicio.obtenerCalendarioEntre(desde, hasta)

        assertEquals(62, calendario.diasTotal())

        val inicio = calendario.dias[0]
        assertEquals(10, inicio.numeroDeSemana)
        assertEquals(DayOfWeek.FRIDAY, inicio.dayOfWeek)
        assertEquals(desde, inicio.date)

        val semana = calendario.obtenerSemana(11)
        assertEquals(7, semana.size)
        assertEquals("Domingo", semana[0].nombre())
        assertEquals("12 Mar 2023".toLocalDate(), semana[0].date)

        assertEquals("Lunes", semana[1].nombre())
        assertEquals("Martes", semana[2].nombre())
        assertEquals("Miércoles", semana[3].nombre())
        assertEquals("Jueves", semana[4].nombre())
        assertEquals("Viernes", semana[5].nombre())

        assertEquals("Sábado", semana[6].nombre())
        assertEquals("18 Mar 2023".toLocalDate(), semana[6].date)

    }

    @Test
    fun `marcar como feriados los dias asi definidos`() {
        servicio = ServicioDeDiasDefault(
            "10 Mar 2023".toLocalDate(),
            "/Users/workia/sources/mio/meriendas/src/main/resources/feriados.txt"
        )
    }
}