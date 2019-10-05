package pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import org.junit.jupiter.api.Test
import pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.model.emptyJasperReportsParameters
import pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.model.jasperReportsParameters
import pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.model.jasperReportsRecord

class JasperReportsReportGeneratorParametersDslTest {

    companion object {
        private val records = listOf(jasperReportsRecord(emptyMap()))
    }

    @Test
    fun `jasperReportsReportGeneratorParameters _ default parameters`() {
        jasperReportsReportGeneratorParameters(records = records).let {
            it.getRecords() shouldBe records
            shouldThrow<NoSuchElementException> {
                it.getParameters()
            }
            it.getParametersOrDefault(emptyJasperReportsParameters()) shouldBe emptyJasperReportsParameters()
        }
    }

    @Test
    fun `jasperReportsReportGeneratorParameters _ all parameters`() {
        val parameters = jasperReportsParameters(emptyMap())

        jasperReportsReportGeneratorParameters(records = records, parameters = parameters).let {
            it.getRecords() shouldBe records
            it.getParameters() shouldBe parameters
            it.getParametersOrDefault(emptyJasperReportsParameters()) shouldBe parameters
        }
    }
}