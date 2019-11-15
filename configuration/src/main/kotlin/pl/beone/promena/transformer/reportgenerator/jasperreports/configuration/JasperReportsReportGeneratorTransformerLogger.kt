package pl.beone.promena.transformer.reportgenerator.jasperreports.configuration

import mu.KotlinLogging
import org.springframework.context.annotation.Configuration
import pl.beone.promena.transformer.reportgenerator.jasperreports.JasperReportsReportGeneratorTransformer
import pl.beone.promena.transformer.reportgenerator.jasperreports.JasperReportsReportGeneratorTransformerDefaultParameters
import javax.annotation.PostConstruct

@Configuration
class JasperReportsReportGeneratorTransformerLogger(
    private val defaultParameters: JasperReportsReportGeneratorTransformerDefaultParameters
) {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    @PostConstruct
    private fun log() {
        logger.info {
            "Run <${JasperReportsReportGeneratorTransformer::class.java.canonicalName}> with <$defaultParameters>"
        }
    }
}