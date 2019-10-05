package pl.beone.promena.transformer.reportgenerator.jasperreports

import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.beone.lib.junit.jupiter.external.DockerExtension
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaType
import pl.beone.promena.transformer.contract.data.DataDescriptor
import pl.beone.promena.transformer.contract.model.Parameters
import pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.JasperReportsReportGeneratorSupport

@ExtendWith(DockerExtension::class)
class JasperReportsReportGeneratorTransformerSupportTest {

    @BeforeEach
    fun setUp() {
        mockkObject(JasperReportsReportGeneratorSupport)
    }

    @Test
    fun isSupported() {
        val dataDescriptor = mockk<DataDescriptor>()
        val targetMediaType = mockk<MediaType>()
        val parameters = mockk<Parameters>()

        every { JasperReportsReportGeneratorSupport.isSupported(dataDescriptor, targetMediaType, parameters) } just Runs

        JasperReportsReportGeneratorTransformer(mockk(), mockk())
            .isSupported(dataDescriptor, targetMediaType, parameters)

        verify(exactly = 1) { JasperReportsReportGeneratorSupport.isSupported(dataDescriptor, targetMediaType, parameters) }
    }

    @AfterEach
    fun tearDown() {
        unmockkObject(JasperReportsReportGeneratorSupport)
    }
}