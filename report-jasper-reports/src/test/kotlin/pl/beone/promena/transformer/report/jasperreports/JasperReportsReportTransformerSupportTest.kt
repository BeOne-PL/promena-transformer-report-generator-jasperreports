package pl.beone.promena.transformer.report.jasperreports

import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.beone.lib.junit5.extension.docker.external.DockerExtension
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaType
import pl.beone.promena.transformer.contract.data.DataDescriptor
import pl.beone.promena.transformer.contract.model.Parameters
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.JasperReportsReportSupport

@ExtendWith(DockerExtension::class)
class JasperReportsReportTransformerSupportTest {

    @BeforeEach
    fun setUp() {
        mockkObject(JasperReportsReportSupport)
    }

    @Test
    fun isSupported() {
        val dataDescriptor = mockk<DataDescriptor>()
        val targetMediaType = mockk<MediaType>()
        val parameters = mockk<Parameters>()

        every { JasperReportsReportSupport.isSupported(dataDescriptor, targetMediaType, parameters) } just Runs

        JasperReportsReportTransformer(mockk())
            .isSupported(dataDescriptor, targetMediaType, parameters)

        verify(exactly = 1) { JasperReportsReportSupport.isSupported(dataDescriptor, targetMediaType, parameters) }
    }

    @AfterEach
    fun tearDown() {
        unmockkObject(JasperReportsReportSupport)
    }
}