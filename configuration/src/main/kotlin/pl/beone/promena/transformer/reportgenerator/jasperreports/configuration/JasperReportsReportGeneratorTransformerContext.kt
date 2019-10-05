package pl.beone.promena.transformer.reportgenerator.jasperreports.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.beone.promena.transformer.contract.communication.CommunicationParameters
import pl.beone.promena.transformer.reportgenerator.jasperreports.JasperReportsReportGeneratorTransformer
import pl.beone.promena.transformer.reportgenerator.jasperreports.JasperReportsReportGeneratorTransformerDefaultParameters

@Configuration
class JasperReportsReportGeneratorTransformerContext {

    @Bean
    fun jasperReportsReportGeneratorTransformer(
        defaultParameters: JasperReportsReportGeneratorTransformerDefaultParameters,
        internalCommunicationParameters: CommunicationParameters
    ) =
        JasperReportsReportGeneratorTransformer(
            defaultParameters,
            internalCommunicationParameters
        )
}