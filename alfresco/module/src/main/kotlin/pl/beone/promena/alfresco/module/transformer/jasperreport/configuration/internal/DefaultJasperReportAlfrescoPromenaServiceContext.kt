package pl.beone.promena.alfresco.module.transformer.jasperreport.configuration.internal

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.beone.promena.alfresco.module.client.base.contract.AlfrescoPromenaService
import pl.beone.promena.alfresco.module.transformer.jasperreport.internal.DefaultJasperReportAlfrescoPromenaService

@Configuration
class DefaultJasperReportAlfrescoPromenaServiceContext {

    @Bean
    fun defaultJasperReportAlfrescoPromenaService(alfrescoPromenaService: AlfrescoPromenaService) =
            DefaultJasperReportAlfrescoPromenaService(alfrescoPromenaService)
}