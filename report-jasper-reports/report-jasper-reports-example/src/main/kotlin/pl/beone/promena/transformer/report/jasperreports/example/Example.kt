package pl.beone.promena.transformer.report.jasperreports.example

import pl.beone.promena.transformer.contract.transformation.Transformation
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.element.*
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.reportJasperReportsParameters
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.reportJasperReportsTransformation

fun transform(): Transformation {
    // HTTP: localhost:8080
    // Repeat: 1
    // Concurrency: 1
    // Data: simple-all-parameters-and-fields.jrxml

    return reportJasperReportsTransformation(reportJasperReportsParameters(createRecords(), createParameters()))
}

private fun createRecords(): List<JasperReportsRecord> =
    (emptyJasperReportsRecord() + ("string" to "value") + ("boolean" to true)) +
            (emptyJasperReportsRecord() + ("string" to "value2"))

private fun createParameters(): JasperReportsParameters =
    emptyJasperReportsParameters() + ("string" to "value3")