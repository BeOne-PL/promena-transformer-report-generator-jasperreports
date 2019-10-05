package pl.beone.promena.transformer.reportgenerator.jasperreports

import pl.beone.promena.communication.file.model.contract.FileCommunicationParameters
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaType
import pl.beone.promena.transformer.contract.Transformer
import pl.beone.promena.transformer.contract.communication.CommunicationParameters
import pl.beone.promena.transformer.contract.data.DataDescriptor
import pl.beone.promena.transformer.contract.data.TransformedDataDescriptor
import pl.beone.promena.transformer.contract.data.toTransformedDataDescriptor
import pl.beone.promena.transformer.contract.model.Parameters
import pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.JasperReportsReportGeneratorSupport
import pl.beone.promena.transformer.reportgenerator.jasperreports.transformer.AbstractTransformer
import pl.beone.promena.transformer.reportgenerator.jasperreports.transformer.FileTransformer
import pl.beone.promena.transformer.reportgenerator.jasperreports.transformer.MemoryTransformer

class JasperReportsReportGeneratorTransformer(
    private val defaultParameters: JasperReportsReportGeneratorTransformerDefaultParameters,
    private val internalCommunicationParameters: CommunicationParameters
) : Transformer {

    override fun transform(dataDescriptor: DataDescriptor, targetMediaType: MediaType, parameters: Parameters): TransformedDataDescriptor =
        dataDescriptor.descriptors
            .map { determineTransformer().transform(it, parameters) }
            .toTransformedDataDescriptor()

    private fun determineTransformer(): AbstractTransformer =
        when (internalCommunicationParameters.getId()) {
            FileCommunicationParameters.ID ->
                FileTransformer(defaultParameters, (internalCommunicationParameters as FileCommunicationParameters).getDirectory())
            else ->
                MemoryTransformer(defaultParameters)
        }

    override fun isSupported(dataDescriptor: DataDescriptor, targetMediaType: MediaType, parameters: Parameters) {
        JasperReportsReportGeneratorSupport.isSupported(dataDescriptor, targetMediaType, parameters)
    }
}