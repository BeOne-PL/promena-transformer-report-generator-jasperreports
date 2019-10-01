package pl.beone.promena.transformer.report.jasperreports.transformer

import net.sf.jasperreports.engine.JRDataSource
import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperExportManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaType
import pl.beone.promena.transformer.contract.data.DataDescriptor
import pl.beone.promena.transformer.contract.data.TransformedDataDescriptor
import pl.beone.promena.transformer.contract.data.singleTransformedDataDescriptor
import pl.beone.promena.transformer.contract.model.Data
import pl.beone.promena.transformer.contract.model.Parameters
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.getParametersOrDefault
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.getRecords
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.model.emptyJasperReportsParameters
import java.io.OutputStream

internal abstract class AbstractTransformer {

    protected abstract fun getOutputStream(): OutputStream

    protected abstract fun createData(): Data

    fun transform(singleDataDescriptor: DataDescriptor.Single, targetMediaType: MediaType, parameters: Parameters): TransformedDataDescriptor.Single {
        val (data, _, metadata) = singleDataDescriptor

        val jasperPrint = JasperFillManager.fillReport(
            JasperCompileManager.compileReport(data.getInputStream()),
            getMutableReportParameters(parameters),
            getMutableReportRecords(parameters).toJRDataSource()
        )

        JasperExportManager.exportReportToPdfStream(jasperPrint, getOutputStream())

        return singleTransformedDataDescriptor(createData(), metadata)
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