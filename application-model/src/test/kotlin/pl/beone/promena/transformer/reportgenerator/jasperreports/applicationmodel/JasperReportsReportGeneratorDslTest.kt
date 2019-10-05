package pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel

import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants.APPLICATION_PDF
import pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.JasperReportsReportGeneratorConstants.TRANSFORMER_ID

class JasperReportsReportGeneratorDslTest {

    @Test
    fun jasperReportsReportGeneratorTransformation() {
        jasperReportsReportGeneratorTransformation(jasperReportsReportGeneratorParameters(records = emptyList())).let {
            it.transformerId shouldBe TRANSFORMER_ID
            it.targetMediaType shouldBe APPLICATION_PDF
            it.parameters.getAll().size shouldBe 1
        }
    }
}