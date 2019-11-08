package pl.beone.promena.transformer.reportgenerator.jasperreports

import pl.beone.promena.transformer.applicationmodel.mediatype.MediaType
import pl.beone.promena.transformer.contract.Transformer
import pl.beone.promena.transformer.contract.communication.CommunicationParameters
import pl.beone.promena.transformer.contract.communication.CommunicationWritableDataCreator
import pl.beone.promena.transformer.contract.data.DataDescriptor
import pl.beone.promena.transformer.contract.data.TransformedDataDescriptor
import pl.beone.promena.transformer.contract.data.toTransformedDataDescriptor
import pl.beone.promena.transformer.contract.model.Parameters
import pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.JasperReportsReportGeneratorSupport
import pl.beone.promena.transformer.reportgenerator.jasperreports.processor.Processor

class JasperReportsReportGeneratorTransformer(
    defaultParameters: JasperReportsReportGeneratorTransformerDefaultParameters,
    private val communicationParameters: CommunicationParameters,
    private val communicationWritableDataCreator: CommunicationWritableDataCreator
) : Transformer {

    private val processor = Processor(defaultParameters)

    override fun transform(dataDescriptor: DataDescriptor, targetMediaType: MediaType, parameters: Parameters): TransformedDataDescriptor =
        dataDescriptor.descriptors
            .map { processor.process(it, parameters, communicationWritableDataCreator.create(communicationParameters)) }
            .toTransformedDataDescriptor()

    override fun isSupported(dataDescriptor: DataDescriptor, targetMediaType: MediaType, parameters: Parameters) {
        JasperReportsReportGeneratorSupport.isSupported(dataDescriptor, targetMediaType, parameters)
    }
}