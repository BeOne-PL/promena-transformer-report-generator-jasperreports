package pl.beone.promena.transformer.report.jasperreports

import pl.beone.lib.typeconverter.applicationmodel.exception.TypeConversionException
import pl.beone.promena.transformer.applicationmodel.exception.transformer.TransformerCouldNotTransformException
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaType
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants
import pl.beone.promena.transformer.contract.data.DataDescriptor
import pl.beone.promena.transformer.contract.model.Parameters
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.ReportJasperReportsParametersConstants
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.element.JasperReportsParameters
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.element.JasperReportsRecord

internal fun validateMediaTypes(dataDescriptor: DataDescriptor, targetMediaType: MediaType) {
    if (isNotDataDescriptorsTargetMediaTypeTextXmlOrApplicationOctetStream(dataDescriptor) || isNotTargetMediaTypeApplicationPdf(targetMediaType)) {
        throw TransformerCouldNotTransformException("Supported transformation: (application/octet-stream, text/xml) -> application/pdf; UTF-8")
    }
}

private fun isNotDataDescriptorsTargetMediaTypeTextXmlOrApplicationOctetStream(dataDescriptor: DataDescriptor): Boolean =
    dataDescriptor.descriptors.any {
        !listOf(MediaTypeConstants.TEXT_XML.mimeType, MediaTypeConstants.APPLICATION_OCTET_STREAM.mimeType).contains(it.mediaType.mimeType)
    }

private fun isNotTargetMediaTypeApplicationPdf(targetMediaType: MediaType): Boolean =
    targetMediaType != MediaTypeConstants.APPLICATION_PDF

internal fun validateRecordsInParameters(parameters: Parameters) {
    try {
        parameters.getList(ReportJasperReportsParametersConstants.RECORDS, JasperReportsRecord::class.java)
    } catch (e: NoSuchElementException) {
        throw TransformerCouldNotTransformException("Mandatory parameter: ${ReportJasperReportsParametersConstants.RECORDS}")
    } catch (e: TypeConversionException) {
        throw TransformerCouldNotTransformException("Parameter: ${ReportJasperReportsParametersConstants.RECORDS} must be type of List<JasperReportsRecord>")
    }
}

internal fun validateParametersInParameters(parameters: Parameters) {
    try {
        parameters.get(ReportJasperReportsParametersConstants.PARAMETERS, JasperReportsParameters::class.java)
    } catch (e: NoSuchElementException) {
        // deliberately omitted - optional parameter
    } catch (e: TypeConversionException) {
        throw TransformerCouldNotTransformException("Parameter: ${ReportJasperReportsParametersConstants.PARAMETERS} must be type of JasperReportsParameters")
    }
}
