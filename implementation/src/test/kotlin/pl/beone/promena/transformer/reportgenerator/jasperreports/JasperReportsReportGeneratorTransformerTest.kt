package pl.beone.promena.transformer.reportgenerator.jasperreports

import io.kotlintest.shouldThrow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.beone.lib.junit.jupiter.external.DockerExtension
import pl.beone.promena.transformer.internal.model.parameters.addTimeout
import pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.jasperReportsReportGeneratorParameters
import pl.beone.promena.transformer.reportgenerator.jasperreports.util.test
import java.io.Serializable
import java.math.BigDecimal
import java.sql.Time
import java.sql.Timestamp
import java.time.Duration
import java.util.*
import java.util.concurrent.TimeoutException

@ExtendWith(DockerExtension::class)
class JasperReportsReportGeneratorTransformerTest {

    companion object {
        private const val assertEmptyElement =
            "null null null null null null null null null null null null"
        private const val assertElement =
            "value true 1.1 2.2 3 4 5 6 1/1/70, 12:36 AM 1/1/70, 12:20 AM 1/3/70, 11:12 AM 1/2/70, 10:12 AM"
        private const val assertElement2 =
            "fieldValue false 11.1 12.2 13 14 15 16 12/17/93, 12:00 PM 5/3/91, 10:00 AM 10/10/91, 11:00 AM 1/1/00, 12:00 AM"

        private val emptyParameters = emptyMap<String, Serializable>()
        private val parameters = emptyMap<String, Serializable>() +
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

        private val emptyRecord = emptyMap<String, Serializable>()
        private val record = emptyMap<String, Serializable>() +
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
        private val record2 = emptyMap<String, Serializable>() +
                ("string" to "fieldValue") +
                ("boolean" to false) +
                ("double" to 11.1) +
                ("float" to 12.2f) +
                ("integer" to 13) +
                ("long" to 14L) +
                ("short" to 15.toShort()) +
                ("bigDecimal" to BigDecimal("16")) +
                ("date" to Date(756129600000)) +
                ("sqlDate" to java.sql.Date(-5638082400000)) +
                ("sqlTime" to Time(687092400000)) +
                ("sqlTimestamp" to Timestamp(946684800000))
    }

    @Test
    fun transform_allSet() {
        test(
            jasperReportsReportGeneratorParameters(records = listOf(record, record2), parameters = parameters),
            assertElement,
            listOf(assertElement, assertElement2)
        )
    }

    @Test
    fun transform_emptyFirstRecord() {
        test(
            jasperReportsReportGeneratorParameters(records = listOf(emptyRecord, record2), parameters = parameters),
            assertElement,
            listOf(assertEmptyElement, assertElement2)
        )
    }

    @Test
    fun transform_emptyParameters() {
        test(
            jasperReportsReportGeneratorParameters(records = listOf(record2), parameters = emptyParameters),
            assertEmptyElement,
            listOf(assertElement2)
        )
    }

    @Test
    fun transform_noParameters() {
        test(
            jasperReportsReportGeneratorParameters(records = listOf(record2)),
            assertEmptyElement,
            listOf(assertElement2)
        )
    }

    @Test
    fun transform_emptyParametersAndEmptyRecord() {
        test(
            jasperReportsReportGeneratorParameters(records = listOf(emptyRecord), parameters = emptyParameters),
            assertEmptyElement,
            listOf(assertEmptyElement)
        )
    }

    // ***

    @Test
    fun transform_zeroTimeout_shouldThrowTimeoutException() {
        shouldThrow<TimeoutException> {
            test(
                jasperReportsReportGeneratorParameters(records = emptyList()) addTimeout Duration.ZERO,
                "",
                emptyList()
            )
        }

        shouldThrow<TimeoutException> {
            test(
                jasperReportsReportGeneratorParameters(records = emptyList()),
                "",
                emptyList(),
                JasperReportsReportGeneratorTransformerDefaultParameters(timeout = Duration.ZERO)
            )
        }
    }
}