package pl.beone.promena.transformer.jasperreport.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.beone.promena.transformer.contract.communication.CommunicationParameters
import pl.beone.promena.transformer.jasperreport.JasperReportTransformer

@Configuration
class JasperReportTransformerContext {

    @Bean
    fun jasperReportTransformer(internalCommunicationParameters: CommunicationParameters) = JasperReportTransformer(internalCommunicationParameters)

}