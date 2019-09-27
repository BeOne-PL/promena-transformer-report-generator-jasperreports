package pl.beone.promena.transformer.report.jasperreports

import pl.beone.promena.communication.file.model.contract.FileCommunicationParameters
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaType
import pl.beone.promena.transformer.contract.Transformer
import pl.beone.promena.transformer.contract.communication.CommunicationParameters
import pl.beone.promena.transformer.contract.data.DataDescriptor
import pl.beone.promena.transformer.contract.data.TransformedDataDescriptor
import pl.beone.promena.transformer.contract.data.toTransformedDataDescriptor
import pl.beone.promena.transformer.contract.model.Parameters
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.JasperReportsReportSupport
import pl.beone.promena.transformer.report.jasperreports.transformer.AbstractTransformer
import pl.beone.promena.transformer.report.jasperreports.transformer.FileTransformer
import pl.beone.promena.transformer.report.jasperreports.transformer.MemoryTransformer

class JasperReportsReportTransformer(
    private val internalCommunicationParameters: CommunicationParameters
) : Transformer {

    override fun transform(dataDescriptor: DataDescriptor, targetMediaType: MediaType, parameters: Parameters): TransformedDataDescriptor =
        dataDescriptor.descriptors
            .map { determineTransformer().transform(it, targetMediaType, parameters) }
            .toTransformedDataDescriptor()

    private fun determineTransformer(): AbstractTransformer =
        when (internalCommunicationParameters.getId()) {
            FileCommunicationParameters.ID ->
                FileTransformer((internalCommunicationParameters as FileCommunicationParameters).getDirectory())
            else ->
                MemoryTransformer()
        }

    override fun isSupported(dataDescriptor: DataDescriptor, targetMediaType: MediaType, parameters: Parameters) {
        JasperReportsReportSupport.isSupported(dataDescriptor, targetMediaType, parameters)
    }
}