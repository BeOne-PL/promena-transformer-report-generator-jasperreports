package pl.beone.promena.transformer.jasperreport

import net.sf.jasperreports.engine.JRDataSource
import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperExportManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource
import org.slf4j.LoggerFactory
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaType
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants.APPLICATION_OCTET_STREAM
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants.APPLICATION_PDF
import pl.beone.promena.transformer.contract.Transformer
import pl.beone.promena.transformer.contract.communication.CommunicationParameters
import pl.beone.promena.transformer.contract.descriptor.DataDescriptor
import pl.beone.promena.transformer.contract.descriptor.TransformedDataDescriptor
import pl.beone.promena.transformer.contract.model.Data
import pl.beone.promena.transformer.contract.model.Parameters
import pl.beone.promena.transformer.internal.model.data.FileData
import pl.beone.promena.transformer.jasperreport.parameters.ParametersValidator
import pl.beone.promena.transformer.jasperreport.parameters.getReportParameters
import pl.beone.promena.transformer.jasperreport.parameters.getReportRecords
import java.io.File
import java.net.URI

class JasperReportTransformer(private val internalCommunicationParameters: CommunicationParameters) : Transformer {

    companion object {
        private val logger = LoggerFactory.getLogger(JasperReportTransformer::class.java)

        private val parametersValidator = ParametersValidator()
    }

    override fun transform(dataDescriptors: List<DataDescriptor>,
                           targetMediaType: MediaType,
                           parameters: Parameters): List<TransformedDataDescriptor> {
        parametersValidator.validate(parameters)
        return dataDescriptors.map { generateReport(it, parameters) }
    }

    private fun generateReport(dataDescriptor: DataDescriptor, parameters: Parameters): TransformedDataDescriptor {
        val data = dataDescriptor.data

        val sourceFileData = data.convertToFileDataIfNecessary()
        val destinationFileData = FileData(createFileRegardingCommunication().toURI())

        try {
            // TODO maybe jasperPrint to file? What is more efficient?
            val jasperPrint = JasperFillManager.fillReport(JasperCompileManager.compileReport(sourceFileData.getFile().path),
                                                           parameters.getMutableReportParameters(),
                                                           parameters.getMutableReportRecords().toJRDataSource())

            JasperExportManager.exportReportToPdfStream(jasperPrint, destinationFileData.getFile().outputStream())

            return TransformedDataDescriptor(destinationFileData, dataDescriptor.metadata)
        } finally {
            sourceFileData.cleanIfNecessary()
        }
    }

    // Mutability is required by JasperReports
    private fun Parameters.getMutableReportParameters(): Map<String, Any> =
            try {
                getReportParameters().all.toMutableMap()
            } catch (e: NoSuchElementException) {
                HashMap()
            }

    // Mutability is required by JasperReports
    private fun Parameters.getMutableReportRecords(): List<Map<String, Any>> =
            getReportRecords().map { it.all.toMutableMap() }

    private fun <T> List<Map<String, T>>.toJRDataSource(): JRDataSource =
            JRMapCollectionDataSource(this)

    private fun Data.convertToFileDataIfNecessary(): FileData =
            if (this !is FileData) {
                logger.debug("One of data isn't type of <FileData>. Creating file...")
                val fileData = toFileData()
                logger.debug("Finished creating <{}> file", fileData.getFile().path)
                fileData
            } else {
                this
            }

    private fun Data.toFileData(): FileData =
            FileData(createFileRegardingCommunication().apply {
                writeBytes(getBytes())
            }.toURI())

    private fun createFileRegardingCommunication(): File =
            if (internalCommunicationParameters.getId() == "file") {
                createTempFile(directory = File(internalCommunicationParameters.get("location", URI::class.java)))
            } else {
                createTempFile()
            }

    private fun FileData.getFile(): File =
            File(getLocation())

    private fun FileData.cleanIfNecessary() {
        if (internalCommunicationParameters.getId() != "file") {
            val file = getFile()
            logger.debug("Internal communication is different than <file>. Deleting <{}> file...", file.path)
            file.delete()
            logger.debug("Finished deleting <{}> file", file.path)
        }
    }

    override fun canTransform(dataDescriptors: List<DataDescriptor>,
                              targetMediaType: MediaType,
                              parameters: Parameters): Boolean =
            dataDescriptors.allTextXml() && targetMediaType.isApplicationPdf()

    private fun List<DataDescriptor>.allTextXml(): Boolean =
            all { it.mediaType == APPLICATION_OCTET_STREAM }

    private fun MediaType.isApplicationPdf(): Boolean =
            this == APPLICATION_PDF

}
