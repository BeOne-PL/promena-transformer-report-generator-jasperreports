package pl.beone.promena.transformer.reportgenerator.jasperreports.example

import pl.beone.promena.transformer.contract.transformation.Transformation
import pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.jasperReportsReportGeneratorParameters
import pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.jasperReportsReportGeneratorTransformation
import java.io.Serializable

fun promena(): Transformation {
    // Data: simple-all-parameters-and-fields.jrxml | MediaType: text/xml

    return jasperReportsReportGeneratorTransformation(jasperReportsReportGeneratorParameters(createRecords(), createParameters()))
}

private fun createRecords(): List<Map<String, Serializable>> =
    listOf(
        emptyMap<String, Serializable>() + ("string" to "value") + ("boolean" to true),
        emptyMap<String, Serializable>() + ("string" to "value2")
    )

private fun createParameters(): Map<String, Serializable> =
    emptyMap<String, Serializable>() + ("string" to "value3")