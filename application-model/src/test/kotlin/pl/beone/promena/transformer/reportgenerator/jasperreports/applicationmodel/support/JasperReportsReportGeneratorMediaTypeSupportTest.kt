package pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.support

import io.kotlintest.shouldNotThrow
import io.kotlintest.shouldThrow
import org.junit.jupiter.api.Test
import pl.beone.promena.transformer.applicationmodel.exception.transformer.TransformationNotSupportedException
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants.APPLICATION_OCTET_STREAM
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants.APPLICATION_PDF
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants.TEXT_PLAIN
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants.TEXT_XML
import pl.beone.promena.transformer.applicationmodel.mediatype.mediaType
import pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.JasperReportsReportGeneratorSupport.MediaTypeSupport.isSupported
import kotlin.text.Charsets.ISO_8859_1

class JasperReportsReportGeneratorMediaTypeSupportTest {

    @Test
    fun isSupported() {
        shouldNotThrow<TransformationNotSupportedException> {
            isSupported(APPLICATION_OCTET_STREAM, APPLICATION_PDF)
        }
    }

    @Test
    fun isSupported2() {
        shouldNotThrow<TransformationNotSupportedException> {
            isSupported(mediaType(APPLICATION_OCTET_STREAM.mimeType, ISO_8859_1), APPLICATION_PDF)
        }
    }

    @Test
    fun isSupported3() {
        shouldNotThrow<TransformationNotSupportedException> {
            isSupported(TEXT_XML, APPLICATION_PDF)
        }
    }

    @Test
    fun isSupported4() {
        shouldNotThrow<TransformationNotSupportedException> {
            isSupported(mediaType(TEXT_XML.mimeType, ISO_8859_1), APPLICATION_PDF)
        }
    }

    @Test
    fun `isSupported _ media type is not supported _ should throw TransformationNotSupportedException`() {
        shouldThrow<TransformationNotSupportedException> {
            isSupported(TEXT_PLAIN, APPLICATION_PDF)
        }
    }

    @Test
    fun `isSupported _ target media type is not supported _ should throw TransformationNotSupportedException`() {
        shouldThrow<TransformationNotSupportedException> {
            isSupported(TEXT_XML, TEXT_PLAIN)
        }
    }
}