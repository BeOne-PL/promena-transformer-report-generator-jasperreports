package pl.beone.promena.configuration

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@ComponentScan(basePackages = [
    "pl.beone.promena.transformer.jasperreport.configuration"
])
@PropertySource("classpath:jasper-report-transformer-transformer.properties")
class JasperReportTransformerModuleContext