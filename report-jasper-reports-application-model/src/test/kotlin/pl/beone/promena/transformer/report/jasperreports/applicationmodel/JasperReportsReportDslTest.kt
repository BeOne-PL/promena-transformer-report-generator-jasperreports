package pl.beone.promena.transformer.report.jasperreports.applicationmodel

import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants.APPLICATION_PDF
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.JasperReportsReportConstants.TRANSFORMER_ID
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.model.jasperReportsRecord

class JasperReportsReportDslTest {

    @Test
    fun jasperReportsReportTransformation() {
        jasperReportsReportTransformation(jasperReportsReportParameters(records = listOf(jasperReportsRecord(emptyMap())))).let {
            it.transformerId shouldBe TRANSFORMER_ID
            it.targetMediaType shouldBe APPLICATION_PDF
            it.parameters.getAll().size shouldBe 1
        }
    }
}