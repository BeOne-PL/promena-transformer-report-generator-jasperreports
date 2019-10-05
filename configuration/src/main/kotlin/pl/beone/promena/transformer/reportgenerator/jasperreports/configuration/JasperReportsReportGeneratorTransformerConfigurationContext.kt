package pl.beone.promena.transformer.reportgenerator.jasperreports.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import pl.beone.promena.transformer.reportgenerator.jasperreports.JasperReportsReportGeneratorTransformerDefaultParameters
import pl.beone.promena.transformer.reportgenerator.jasperreports.configuration.extension.getNotBlankProperty
import pl.beone.promena.transformer.reportgenerator.jasperreports.configuration.extension.toDuration

@Configuration
class JasperReportsReportGeneratorTransformerConfigurationContext {

    companion object {
        private const val PROPERTY_PREFIX = "transformer.pl.beone.promena.transformer.reportgenerator.jasperreports"
    }

    @Bean
    fun jasperReportsReportGeneratorTransformerDefaultParameters(environment: Environment): JasperReportsReportGeneratorTransformerDefaultParameters =
        JasperReportsReportGeneratorTransformerDefaultParameters(
            environment.getNotBlankProperty("$PROPERTY_PREFIX.default.parameters.timeout")?.toDuration()
        )
}