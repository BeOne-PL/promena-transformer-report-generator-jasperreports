package pl.beone.promena.transformer.report.jasperreports.applicationmodel

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import org.junit.jupiter.api.Test
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.model.jasperReportsParameters
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.model.jasperReportsRecord

class JasperReportsReportParametersDslTest {

    companion object {
        private val records = listOf(jasperReportsRecord(emptyMap()))
    }

    @Test
    fun `jasperReportsReportParameters _ default parameters`() {
        jasperReportsReportParameters(records = records).let {
            it.getRecords() shouldBe records
            shouldThrow<NoSuchElementException> {
                it.getParameters()
            }
        }
    }

    @Test
    fun `jasperReportsReportParameters _ all parameters`() {
        val parameters = jasperReportsParameters(emptyMap())

        jasperReportsReportParameters(records = records, parameters = parameters).let {
            it.getRecords() shouldBe records
            it.getParameters() shouldBe parameters
        }
    }
}