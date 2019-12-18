package pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import org.junit.jupiter.api.Test
import java.io.Serializable

class JasperReportsReportGeneratorParametersDslTest {

    companion object {
        private val records = listOf(emptyMap<String, Serializable>())
        private val parameters = emptyMap<String, Serializable>()
    }

    @Test
    fun `jasperReportsReportGeneratorParameters _ default parameters`() {
        with(jasperReportsReportGeneratorParameters(records = records)) {
            getRecords() shouldBe records
            shouldThrow<NoSuchElementException> { getParameters() }
            getParametersOrNull() shouldBe null
            getParametersOrDefault(parameters) shouldBe parameters
        }
    }

    @Test
    fun `jasperReportsReportGeneratorParameters _ all parameters`() {
        with(jasperReportsReportGeneratorParameters(records = records, parameters = parameters)) {
            getRecords() shouldBe records
            getParameters() shouldBe parameters
            getParametersOrNull() shouldBe parameters
            getParametersOrDefault(parameters) shouldBe parameters
        }
    }
}