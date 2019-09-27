package pl.beone.promena.transformer.report.jasperreports

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.beone.lib.junit5.extension.docker.external.DockerExtension
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.jasperReportsReportParameters
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.model.emptyJasperReportsParameters
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.model.emptyJasperReportsRecord
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.model.plus
import java.math.BigDecimal
import java.sql.Time
import java.sql.Timestamp
import java.util.*

@ExtendWith(DockerExtension::class)
class JasperReportsReportTransformerCommunicationTest {

    companion object {
        private const val assertElement =
            "value true 1.1 2.2 3 4 5 6 1/1/70, 12:36 AM 1/1/70, 12:20 AM 1/3/70, 11:12 AM 1/2/70, 10:12 AM"

        private val parameters = emptyJasperReportsParameters() +
                ("string" to "value") +
                ("boolean" to true) +
                ("double" to 1.1) +
                ("float" to 2.2f) +
                ("integer" to 3) +
                ("long" to 4L) +
                ("short" to 5.toShort()) +
                ("bigDecimal" to BigDecimal("6")) +
                ("date" to Date(2213213)) +
                ("sqlDate" to java.sql.Date(1213213)) +
                ("sqlTime" to Time(213123321)) +
                ("sqlTimestamp" to Timestamp(123123213))

        private val record = emptyJasperReportsRecord() +
                ("string" to "value") +
                ("boolean" to true) +
                ("double" to 1.1) +
                ("float" to 2.2f) +
                ("integer" to 3) +
                ("long" to 4L) +
                ("short" to 5.toShort()) +
                ("bigDecimal" to BigDecimal("6")) +
                ("date" to Date(2213213)) +
                ("sqlDate" to java.sql.Date(1213213)) +
                ("sqlTime" to Time(213123321)) +
                ("sqlTimestamp" to Timestamp(123123213))
    }

    @Test
    fun transform_memoryTest() {
        memoryTest(
            jasperReportsReportParameters(records = listOf(record), parameters = parameters),
            assertElement,
            listOf(assertElement)
        )
    }

    @Test
    fun transform_fileTest() {
        fileTest(
            jasperReportsReportParameters(records = listOf(record), parameters = parameters),
            assertElement,
            listOf(assertElement)
        )
    }
}