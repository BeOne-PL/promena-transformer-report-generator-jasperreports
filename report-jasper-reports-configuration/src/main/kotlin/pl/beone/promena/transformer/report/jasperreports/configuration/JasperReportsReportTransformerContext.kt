package pl.beone.promena.transformer.report.jasperreports.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.beone.promena.transformer.contract.communication.CommunicationParameters
import pl.beone.promena.transformer.report.jasperreports.JasperReportsReportTransformer

@Configuration
class JasperReportsReportTransformerContext {

    @Bean
    fun jasperReportsReportTransformer(
        internalCommunicationParameters: CommunicationParameters
    ) =
        JasperReportsReportTransformer(
            internalCommunicationParameters
        )
}