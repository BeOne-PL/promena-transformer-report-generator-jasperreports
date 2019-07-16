package pl.beone.promena.alfresco.module.transformer.jasperreport.contract

import org.alfresco.service.cmr.repository.NodeRef
import pl.beone.promena.transformer.jasperreport.applicationmodel.parameters.JasperReportParameters
import pl.beone.promena.transformer.jasperreport.applicationmodel.parameters.JasperReportRecord
import reactor.core.publisher.Mono
import java.time.Duration

interface JasperReportAlfrescoPromenaService {

    fun generatePdf(nodeRef: NodeRef,
                    parameters: JasperReportParameters,
                    records: List<JasperReportRecord>,
                    waitMax: Duration? = null): List<NodeRef>

    fun generatePdfAsync(nodeRef: NodeRef,
                         parameters: JasperReportParameters,
                         records: List<JasperReportRecord>): Mono<List<NodeRef>>

}