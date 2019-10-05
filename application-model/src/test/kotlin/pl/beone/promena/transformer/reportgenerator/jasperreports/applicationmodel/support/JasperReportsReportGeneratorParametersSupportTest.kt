package pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.support

import io.kotlintest.shouldNotThrow
import org.junit.jupiter.api.Test
import pl.beone.promena.transformer.applicationmodel.exception.transformer.TransformationNotSupportedException
import pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.JasperReportsReportGeneratorSupport.ParametersSupport.isSupported
import pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.jasperReportsReportGeneratorParameters
import pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.model.jasperReportsParameters
import pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.model.jasperReportsRecord

class JasperReportsReportGeneratorParametersSupportTest {

    companion object {
        private val records = listOf(jasperReportsRecord(emptyMap()))
        private val parameters = jasperReportsParameters(emptyMap())
    }

    @Test
    fun `isSupported _ default parameters`() {
        shouldNotThrow<TransformationNotSupportedException> {
            isSupported(jasperReportsReportGeneratorParameters(records = records))
        }
    }

    @Test
    fun `isSupported _ all parameters`() {
        shouldNotThrow<TransformationNotSupportedException> {
            isSupported(jasperReportsReportGeneratorParameters(records = records, parameters = parameters))
        }
    }
}