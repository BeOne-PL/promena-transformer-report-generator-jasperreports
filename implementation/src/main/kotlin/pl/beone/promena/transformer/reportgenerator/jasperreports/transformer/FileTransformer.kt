package pl.beone.promena.transformer.reportgenerator.jasperreports.transformer

import pl.beone.promena.transformer.contract.model.data.Data
import pl.beone.promena.transformer.internal.model.data.file.fileData
import pl.beone.promena.transformer.reportgenerator.jasperreports.JasperReportsReportGeneratorTransformerDefaultParameters
import java.io.File
import java.io.OutputStream

internal class FileTransformer(
    defaultParameters: JasperReportsReportGeneratorTransformerDefaultParameters,
    directory: File
) : AbstractTransformer(defaultParameters) {

    private val file = createTempFile(directory = directory)

    override fun getOutputStream(): OutputStream =
        file.outputStream()

    override fun createData(): Data =
        fileData(file)
}