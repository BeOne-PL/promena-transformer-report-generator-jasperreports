@file:JvmName("JasperReportsParametersDsl")

package pl.beone.promena.transformer.report.jasperreports.applicationmodel.element

import java.io.Serializable

fun emptyJasperReportsParameters(): JasperReportsParameters =
    JasperReportsParameters.of(emptyMap())

fun jasperReportsParameters(elements: Map<String, Serializable>): JasperReportsParameters =
    JasperReportsParameters.of(elements)

operator fun JasperReportsParameters.plus(entry: Pair<String, Serializable>): JasperReportsParameters =
    JasperReportsParameters.of(elements + entry)
