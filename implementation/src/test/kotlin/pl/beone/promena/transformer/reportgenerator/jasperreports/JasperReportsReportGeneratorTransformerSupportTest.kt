package pl.beone.promena.transformer.reportgenerator.jasperreports

import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.beone.lib.junit.jupiter.external.DockerExtension
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaType
import pl.beone.promena.transformer.contract.data.DataDescriptor
import pl.beone.promena.transformer.contract.model.Parameters
import pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.JasperReportsReportGeneratorSupport
import pl.beone.promena.transformer.reportgenerator.jasperreports.util.createJasperReportsReportGeneratorTransformer

@ExtendWith(DockerExtension::class)
class JasperReportsReportGeneratorTransformerSupportTest {

    @Test
    fun isSupported() {
        val dataDescriptor = mockk<DataDescriptor>()
        val targetMediaType = mockk<MediaType>()
        val parameters = mockk<Parameters>()

        mockkStatic(JasperReportsReportGeneratorSupport::class)
        every { JasperReportsReportGeneratorSupport.isSupported(dataDescriptor, targetMediaType, parameters) } just Runs

        createJasperReportsReportGeneratorTransformer()
            .isSupported(dataDescriptor, targetMediaType, parameters)

        verify(exactly = 1) { JasperReportsReportGeneratorSupport.isSupported(dataDescriptor, targetMediaType, parameters) }
    }
}