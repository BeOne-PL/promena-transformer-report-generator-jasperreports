package pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel

import pl.beone.lib.typeconverter.applicationmodel.exception.TypeConversionException
import pl.beone.promena.transformer.applicationmodel.exception.transformer.TransformationNotSupportedException
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaType
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants.APPLICATION_OCTET_STREAM
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants.APPLICATION_PDF
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants.TEXT_XML
import pl.beone.promena.transformer.contract.data.DataDescriptor
import pl.beone.promena.transformer.contract.model.Parameters
import pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.JasperReportsReportGeneratorParametersConstants.Records

object JasperReportsReportGeneratorSupport {

    fun isSupported(dataDescriptor: DataDescriptor, targetMediaType: MediaType, parameters: Parameters) {
        dataDescriptor.descriptors.forEach { (_, mediaType) -> MediaTypeSupport.isSupported(mediaType, targetMediaType) }
        ParametersSupport.isSupported(parameters)
    }

    object MediaTypeSupport {
        private val supportedMimeType = setOf(
            TEXT_XML.mimeType to APPLICATION_PDF,
            APPLICATION_OCTET_STREAM.mimeType to APPLICATION_PDF
        )

        fun isSupported(mediaType: MediaType, targetMediaType: MediaType) {
            if (!supportedMimeType.contains(mediaType.mimeType to targetMediaType)) {
                throw TransformationNotSupportedException.unsupportedMediaType(mediaType, targetMediaType)
            }
        }
    }

    object ParametersSupport {
        fun isSupported(parameters: Parameters) {
            parameters.validate(Records.NAME, Records.CLASS, true)
            parameters.validate(
                JasperReportsReportGeneratorParametersConstants.Parameters.NAME,
                JasperReportsReportGeneratorParametersConstants.Parameters.CLASS,
                false
            )
        }

        private fun Parameters.validate(name: String, clazz: Class<*>, mandatory: Boolean) {
            try {
                get(name, clazz)
            } catch (e: NoSuchElementException) {
                if (mandatory) {
                    throw TransformationNotSupportedException.mandatoryParameter(name)
                }
            } catch (e: TypeConversionException) {
                throw TransformationNotSupportedException.unsupportedParameterType(name, clazz)
            }
        }
    }
}