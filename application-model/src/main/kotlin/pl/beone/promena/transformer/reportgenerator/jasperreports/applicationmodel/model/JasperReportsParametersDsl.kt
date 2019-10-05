@file:JvmName("JasperReportsParametersDsl")

package pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.model

import java.io.Serializable

fun emptyJasperReportsParameters(): JasperReportsParameters =
    JasperReportsParameters.of(emptyMap())

fun jasperReportsParameters(elements: Map<String, Serializable>): JasperReportsParameters =
    JasperReportsParameters.of(elements)

operator fun JasperReportsParameters.plus(entry: Pair<String, Serializable>): JasperReportsParameters =
    JasperReportsParameters.of(elements + entry)
