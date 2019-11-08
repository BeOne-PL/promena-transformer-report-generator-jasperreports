package pl.beone.promena.transformer.reportgenerator.jasperreports.configuration

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.beone.promena.transformer.contract.communication.CommunicationParameters
import pl.beone.promena.transformer.contract.communication.CommunicationWritableDataCreator
import pl.beone.promena.transformer.reportgenerator.jasperreports.JasperReportsReportGeneratorTransformer
import pl.beone.promena.transformer.reportgenerator.jasperreports.JasperReportsReportGeneratorTransformerDefaultParameters

@Configuration
class JasperReportsReportGeneratorTransformerContext {

    @Bean
    fun jasperReportsReportGeneratorTransformer(
        defaultParameters: JasperReportsReportGeneratorTransformerDefaultParameters,
        @Qualifier("internalCommunicationParameters") communicationParameters: CommunicationParameters,
        @Qualifier("internalCommunicationWritableDataCreator") communicationWritableDataCreator: CommunicationWritableDataCreator
    ) =
        JasperReportsReportGeneratorTransformer(
            defaultParameters,
            communicationParameters,
            communicationWritableDataCreator
        )
}