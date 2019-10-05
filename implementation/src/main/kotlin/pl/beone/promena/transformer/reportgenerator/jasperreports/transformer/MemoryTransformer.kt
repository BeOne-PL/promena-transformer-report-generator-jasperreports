package pl.beone.promena.transformer.reportgenerator.jasperreports.transformer

import pl.beone.promena.transformer.contract.model.Data
import pl.beone.promena.transformer.internal.model.data.memoryData
import pl.beone.promena.transformer.reportgenerator.jasperreports.JasperReportsReportGeneratorTransformerDefaultParameters
import java.io.ByteArrayOutputStream
import java.io.OutputStream

internal class MemoryTransformer(
    defaultParameters: JasperReportsReportGeneratorTransformerDefaultParameters
) : AbstractTransformer(defaultParameters) {

    private val outputStream = ByteArrayOutputStream()

    override fun getOutputStream(): OutputStream =
        outputStream

    override fun createData(): Data =
        memoryData(outputStream.toByteArray())
}