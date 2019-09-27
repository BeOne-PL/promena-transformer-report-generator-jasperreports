package pl.beone.promena.transformer.report.jasperreports.example

import pl.beone.promena.transformer.contract.transformation.Transformation
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.jasperReportsReportParameters
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.jasperReportsReportTransformation
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.model.*

fun promena(): Transformation {
    // Data: simple-all-parameters-and-fields.jrxml | MediaType: text/xml

    return jasperReportsReportTransformation(jasperReportsReportParameters(createRecords(), createParameters()))
}

private fun createRecords(): List<JasperReportsRecord> =
    (emptyJasperReportsRecord() + ("string" to "value") + ("boolean" to true)) +
            (emptyJasperReportsRecord() + ("string" to "value2"))

private fun createParameters(): JasperReportsParameters =
    emptyJasperReportsParameters() + ("string" to "value3")