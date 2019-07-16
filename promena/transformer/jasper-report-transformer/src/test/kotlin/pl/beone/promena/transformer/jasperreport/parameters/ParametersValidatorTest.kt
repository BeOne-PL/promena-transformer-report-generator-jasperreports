package pl.beone.promena.transformer.jasperreport.parameters

import io.kotlintest.matchers.string.shouldContain
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotThrow
import io.kotlintest.shouldThrow
import org.junit.Test
import pl.beone.promena.transformer.internal.model.parameters.MapParameters
import pl.beone.promena.transformer.jasperreport.applicationmodel.parameters.JasperReportParameters
import pl.beone.promena.transformer.jasperreport.applicationmodel.parameters.JasperReportRecord
import pl.beone.promena.transformer.jasperreport.createParameters
import pl.beone.promena.transformer.jasperreport.exception.JasperReportValidationException

class ParametersValidatorTest {

    companion object {
        private val parametersValidator = ParametersValidator()

        private val emptyReportParameters = JasperReportParameters.empty()
        private val emptyReportRecord = JasperReportRecord.empty()
    }

    @Test
    fun validate() {
        shouldNotThrow<JasperReportValidationException> {
            parametersValidator.validate(createParameters(emptyReportParameters, listOf(emptyReportRecord)))
        }
    }

    @Test
    fun `validate _ no root`() {
        shouldThrow<JasperReportValidationException> {
            parametersValidator.validate(MapParameters.empty())
        }.message shouldContain "Parameter <jasperReportTransformer> must be set"
    }

    @Test
    fun `validate _ no parameters`() {
        shouldNotThrow<JasperReportValidationException> {
            parametersValidator.validate(createParameters(null, listOf(emptyReportRecord)))
        }
    }

    @Test
    fun `validate _ parameters isn't the correct type _ should throw JasperReportValidationException`() {
        shouldThrow<JasperReportValidationException> {
            parametersValidator.validate(createParameters(false, listOf(emptyReportRecord)))
        }.message shouldContain "Parameter <jasperReportTransformer.parameters> must be of the <ReportParameters> type"
    }

    @Test
    fun `validate _ no records _ should throw JasperReportValidationException`() {
        shouldThrow<JasperReportValidationException> {
            parametersValidator.validate(createParameters(emptyReportParameters, null))
        }.message shouldBe "Parameter <jasperReportTransformer.records> must be set"
    }

    @Test
    fun `validate _ empty list of records _ should throw JasperReportValidationException`() {
        shouldThrow<JasperReportValidationException> {
            parametersValidator.validate(createParameters(emptyReportParameters, emptyList<JasperReportParameters>()))
        }.message shouldBe "Parameter <jasperReportTransformer.records> must contain at least one record"
    }

    @Test
    fun `validate _ records isn't the correct type _ should throw JasperReportValidationException`() {
        shouldThrow<JasperReportValidationException> {
            parametersValidator.validate(createParameters(emptyReportParameters, false))
        }.message shouldContain "Parameter <jasperReportTransformer.records> must be of the <List<ReportRecord>> type"
    }
}