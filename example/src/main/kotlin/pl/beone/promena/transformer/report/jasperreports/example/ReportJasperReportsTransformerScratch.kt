package pl.beone.promena.transformer.report.jasperreports.example

import org.alfresco.service.cmr.repository.NodeRef
import org.alfresco.service.cmr.repository.StoreRef
import pl.beone.promena.alfresco.module.client.base.contract.AlfrescoPromenaTransformer
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.element.*
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.reportJasperReportsParameters
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.reportJasperReportsTransformation

class ReportJasperReportsTransformerScratch {

    fun generateReport(alfrescoPromenaTransformer: AlfrescoPromenaTransformer) {
            alfrescoPromenaTransformer.transformAsync(
                reportJasperReportsTransformation(reportJasperReportsParameters(createRecords(), createParameters())),
                listOf(NodeRef(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE, "xxx"))
            ).subscribe(
                ::println, // SUCCESS
                ::println, // ERROR
                { } // COMPLETE
            )
    }

    private fun createRecords(): List<JasperReportsRecord> =
        (emptyJasperReportsRecord() + ("string" to "value") + ("boolean" to true)) +
                (emptyJasperReportsRecord() + ("string" to "value2"))

    private fun createParameters(): JasperReportsParameters =
        emptyJasperReportsParameters() + ("string" to "value3")
}