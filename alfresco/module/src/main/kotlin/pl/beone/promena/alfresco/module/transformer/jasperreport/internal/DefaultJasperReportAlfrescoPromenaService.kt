package pl.beone.promena.alfresco.module.transformer.jasperreport.internal

import org.alfresco.service.cmr.repository.NodeRef
import pl.beone.promena.alfresco.module.client.base.contract.AlfrescoPromenaService
import pl.beone.promena.alfresco.module.transformer.jasperreport.contract.JasperReportAlfrescoPromenaService
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants
import pl.beone.promena.transformer.internal.model.parameters.MapParameters
import pl.beone.promena.transformer.jasperreport.applicationmodel.parameters.JasperReportParameters
import pl.beone.promena.transformer.jasperreport.applicationmodel.parameters.JasperReportParametersConstants.*
import pl.beone.promena.transformer.jasperreport.applicationmodel.parameters.JasperReportRecord
import reactor.core.publisher.Mono
import java.time.Duration

class DefaultJasperReportAlfrescoPromenaService(private val alfrescoPromenaService: AlfrescoPromenaService) : JasperReportAlfrescoPromenaService {

    override fun generatePdf(nodeRef: NodeRef,
                             parameters: JasperReportParameters,
                             records: List<JasperReportRecord>,
                             waitMax: Duration?): List<NodeRef> =
            alfrescoPromenaService.transform("jasper-report",
                                             listOf(nodeRef),
                                             MediaTypeConstants.APPLICATION_PDF,
                                             createParameters(parameters, records))

    override fun generatePdfAsync(nodeRef: NodeRef,
                                  parameters: JasperReportParameters,
                                  records: List<JasperReportRecord>): Mono<List<NodeRef>> =
            alfrescoPromenaService.transformAsync("jasper-report",
                                                  listOf(nodeRef),
                                                  MediaTypeConstants.APPLICATION_PDF,
                                                  createParameters(parameters, records))

    private fun createParameters(parameters: JasperReportParameters, records: List<JasperReportRecord>): MapParameters =
            MapParameters(mapOf(Root to MapParameters(mapOf(
                    Parameters to parameters,
                    Records to records
            ))))
}