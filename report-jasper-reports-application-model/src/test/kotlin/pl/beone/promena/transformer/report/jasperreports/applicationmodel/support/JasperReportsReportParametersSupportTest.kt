package pl.beone.promena.transformer.report.jasperreports.applicationmodel.support

import io.kotlintest.shouldNotThrow
import org.junit.jupiter.api.Test
import pl.beone.promena.transformer.applicationmodel.exception.transformer.TransformationNotSupportedException
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.JasperReportsReportSupport.ParametersSupport.isSupported
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.jasperReportsReportParameters
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.model.jasperReportsParameters
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.model.jasperReportsRecord

class JasperReportsReportParametersSupportTest {

    companion object {
        private val records = listOf(jasperReportsRecord(emptyMap()))
        private val parameters = jasperReportsParameters(emptyMap())
    }

    @Test
    fun `isSupported _ default parameters`() {
        shouldNotThrow<TransformationNotSupportedException> {
            isSupported(jasperReportsReportParameters(records = records))
        }
    }

    @Test
    fun `isSupported _ all parameters`() {
        shouldNotThrow<TransformationNotSupportedException> {
            isSupported(jasperReportsReportParameters(records = records, parameters = parameters))
        }
    }
}