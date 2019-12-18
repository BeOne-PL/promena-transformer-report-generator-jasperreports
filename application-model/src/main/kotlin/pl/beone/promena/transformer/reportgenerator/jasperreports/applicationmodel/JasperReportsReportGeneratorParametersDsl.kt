@file:JvmName("JasperReportsReportGeneratorParametersDsl")

package pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel

import pl.beone.promena.transformer.contract.model.Parameters
import pl.beone.promena.transformer.internal.model.parameters.MapParameters
import pl.beone.promena.transformer.internal.model.parameters.addIfNotNull
import pl.beone.promena.transformer.internal.model.parameters.emptyParameters
import pl.beone.promena.transformer.internal.model.parameters.plus
import pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.JasperReportsReportGeneratorParametersConstants.Records
import java.io.Serializable

fun jasperReportsReportGeneratorParameters(records: List<Map<String, Serializable>>, parameters: Map<String, Serializable>? = null): MapParameters =
    emptyParameters() +
            (Records.NAME to records) addIfNotNull
            (JasperReportsReportGeneratorParametersConstants.Parameters.NAME to parameters)

fun Parameters.getRecords(): List<Map<String, Serializable>> =
    get(Records.NAME, Records.CLASS)

fun Parameters.getParameters(): Map<String, Serializable> =
    get(JasperReportsReportGeneratorParametersConstants.Parameters.NAME, JasperReportsReportGeneratorParametersConstants.Parameters.CLASS)

fun Parameters.getParametersOrNull(): Map<String, Serializable>? =
    getOrNull(JasperReportsReportGeneratorParametersConstants.Parameters.NAME, JasperReportsReportGeneratorParametersConstants.Parameters.CLASS)

fun Parameters.getParametersOrDefault(default: Map<String, Serializable>): Map<String, Serializable> =
    getOrDefault(
        JasperReportsReportGeneratorParametersConstants.Parameters.NAME,
        JasperReportsReportGeneratorParametersConstants.Parameters.CLASS,
        default
    )