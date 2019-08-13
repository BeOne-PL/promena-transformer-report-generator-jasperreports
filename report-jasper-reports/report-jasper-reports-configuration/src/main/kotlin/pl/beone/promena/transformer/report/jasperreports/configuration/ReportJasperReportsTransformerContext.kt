package pl.beone.promena.transformer.report.jasperreports.configuration

import pl.beone.promena.transformer.report.jasperreports.ReportJasperReportsTransformer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.beone.promena.transformer.contract.communication.CommunicationParameters

@Configuration
class ReportJasperReportsTransformerContext {

    @Bean
    fun reportJasperReportsTransformer(internalCommunicationParameters: CommunicationParameters) =
        ReportJasperReportsTransformer(internalCommunicationParameters)
}