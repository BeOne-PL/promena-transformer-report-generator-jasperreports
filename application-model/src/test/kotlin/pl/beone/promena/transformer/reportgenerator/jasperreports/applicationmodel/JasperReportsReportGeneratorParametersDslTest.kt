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
        with(jasperReportsReportGeneratorParameters(records = records)) {
            getRecords() shouldBe records
            shouldThrow<NoSuchElementException> { getParameters() }
            getParametersOrDefault(emptyJasperReportsParameters()) shouldBe emptyJasperReportsParameters()
        }
    }

    @Test
    fun `jasperReportsReportGeneratorParameters _ all parameters`() {
        val parameters = jasperReportsParameters(emptyMap())

        with(jasperReportsReportGeneratorParameters(records = records, parameters = parameters)) {
            getRecords() shouldBe records
            getParameters() shouldBe parameters
            getParametersOrDefault(emptyJasperReportsParameters()) shouldBe parameters
        }
    }
}