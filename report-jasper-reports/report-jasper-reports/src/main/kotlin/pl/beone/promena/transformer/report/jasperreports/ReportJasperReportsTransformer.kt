package pl.beone.promena.transformer.report.jasperreports

import net.sf.jasperreports.engine.JRDataSource
import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperExportManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaType
import pl.beone.promena.transformer.contract.Transformer
import pl.beone.promena.transformer.contract.communication.CommunicationParameters
import pl.beone.promena.transformer.contract.data.DataDescriptor
import pl.beone.promena.transformer.contract.data.TransformedDataDescriptor
import pl.beone.promena.transformer.contract.data.singleTransformedDataDescriptor
import pl.beone.promena.transformer.contract.data.toTransformedDataDescriptor
import pl.beone.promena.transformer.contract.model.Data
import pl.beone.promena.transformer.contract.model.Metadata
import pl.beone.promena.transformer.contract.model.Parameters
import pl.beone.promena.transformer.internal.model.data.toFileData
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.ReportJasperReportsParametersConstants
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.element.JasperReportsParameters
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.element.JasperReportsRecord
import java.io.File
import java.net.URI

class ReportJasperReportsTransformer(private val internalCommunicationParameters: CommunicationParameters) : Transformer {

    override fun transform(dataDescriptor: DataDescriptor, targetMediaType: MediaType, parameters: Parameters): TransformedDataDescriptor =
        dataDescriptor.descriptors.map { (data, _, metadata) ->
            generateReport(data, metadata, parameters)
        }.toTransformedDataDescriptor()

    private fun generateReport(data: Data, metadata: Metadata, parameters: Parameters): TransformedDataDescriptor.Single {
        val destinationFile = createFileConsideringCommunication()

        val jasperPrint = JasperFillManager.fillReport(
            JasperCompileManager.compileReport(data.getInputStream()),
            parameters.getMutableReportParameters(),
            parameters.getMutableReportRecords().toJRDataSource()
        )

        JasperExportManager.exportReportToPdfStream(jasperPrint, destinationFile.outputStream())

        return singleTransformedDataDescriptor(destinationFile.toFileData(), metadata)
    }

    // Mutability is required by JasperReports
    private fun Parameters.getMutableReportParameters(): Map<String, Any> =
        try {
            get(ReportJasperReportsParametersConstants.PARAMETERS, JasperReportsParameters::class.java)
                .elements.toMutableMap()
        } catch (e: NoSuchElementException) {
            HashMap()
        }

    // Mutability is required by JasperReports
    private fun Parameters.getMutableReportRecords(): List<Map<String, Any>> =
        getList(ReportJasperReportsParametersConstants.RECORDS, JasperReportsRecord::class.java)
            .map { it.elements.toMutableMap() }

    private fun <T> List<Map<String, T>>.toJRDataSource(): JRDataSource =
        JRMapCollectionDataSource(this)

    private fun createFileConsideringCommunication(): File =
        createTempFile(
            directory =
            if (internalCommunicationParameters.getId() == "file") {
                File(internalCommunicationParameters.get("location", URI::class.java))
            } else {
                createTempDir()
            }
        )

    override fun canTransform(dataDescriptor: DataDescriptor, targetMediaType: MediaType, parameters: Parameters) {
        validateMediaTypes(dataDescriptor, targetMediaType)
        // TODO the content of descriptors should be also validated. Maybe root xml element <jasperReport ...>?
        validateRecordsInParameters(parameters)
        validateParametersInParameters(parameters)
    }
}