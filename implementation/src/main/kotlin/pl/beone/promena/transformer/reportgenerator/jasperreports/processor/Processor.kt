package pl.beone.promena.transformer.reportgenerator.jasperreports.processor

import net.sf.jasperreports.engine.JRDataSource
import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperExportManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource
import pl.beone.promena.transformer.contract.data.DataDescriptor
import pl.beone.promena.transformer.contract.data.TransformedDataDescriptor
import pl.beone.promena.transformer.contract.data.singleTransformedDataDescriptor
import pl.beone.promena.transformer.contract.model.Parameters
import pl.beone.promena.transformer.contract.model.data.Data
import pl.beone.promena.transformer.contract.model.data.WritableData
import pl.beone.promena.transformer.reportgenerator.jasperreports.JasperReportsReportGeneratorTransformerDefaultParameters
import pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.getParametersOrDefault
import pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.getRecords
import pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.model.emptyJasperReportsParameters
import java.io.OutputStream
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

internal class Processor(
    private val defaultParameters: JasperReportsReportGeneratorTransformerDefaultParameters
) {

    fun process(
        singleDataDescriptor: DataDescriptor.Single,
        parameters: Parameters,
        transformedWritableData: WritableData
    ): TransformedDataDescriptor.Single {
        val (data, _, metadata) = singleDataDescriptor

        val timeout = parameters.getTimeoutOrNull() ?: defaultParameters.timeout
        if (timeout != null) {
            Executors.newSingleThreadExecutor()
                .submit { generate(data, parameters, transformedWritableData.getOutputStream()) }
                .get(timeout.toMillis(), TimeUnit.MILLISECONDS)
        } else {
            generate(data, parameters, transformedWritableData.getOutputStream())
        }

        return singleTransformedDataDescriptor(transformedWritableData, metadata)
    }

    private fun generate(data: Data, parameters: Parameters, outputStream: OutputStream) {
        val jasperPrint = JasperFillManager.fillReport(
            JasperCompileManager.compileReport(data.getInputStream()),
            getMutableReportParameters(parameters),
            getMutableReportRecords(parameters).toJRDataSource()
        )

        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream)
    }

    // Mutability is required by JasperReports
    private fun getMutableReportParameters(parameters: Parameters): Map<String, Any> =
        parameters.getParametersOrDefault(emptyJasperReportsParameters()).elements.toMutableMap()

    // Mutability is required by JasperReports
    private fun getMutableReportRecords(parameters: Parameters): List<Map<String, Any>> =
        parameters.getRecords().map { it.elements.toMutableMap() }

    private fun <T> List<Map<String, T>>.toJRDataSource(): JRDataSource =
        JRMapCollectionDataSource(this)
}