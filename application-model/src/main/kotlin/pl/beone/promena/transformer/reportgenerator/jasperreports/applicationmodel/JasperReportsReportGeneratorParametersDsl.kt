@file:JvmName("JasperReportsReportGeneratorParametersDsl")

package pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel

import pl.beone.promena.transformer.contract.model.Parameters
import pl.beone.promena.transformer.internal.model.parameters.MapParameters
import pl.beone.promena.transformer.internal.model.parameters.addIfNotNull
import pl.beone.promena.transformer.internal.model.parameters.emptyParameters
import pl.beone.promena.transformer.internal.model.parameters.plus
import pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.JasperReportsReportGeneratorParametersConstants.Records
import pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.model.JasperReportsParameters
import pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.model.JasperReportsRecord

fun jasperReportsReportGeneratorParameters(records: List<JasperReportsRecord>, parameters: JasperReportsParameters? = null): MapParameters =
    emptyParameters() +
            (Records.NAME to records) addIfNotNull
            (JasperReportsReportGeneratorParametersConstants.Parameters.NAME to parameters)

fun Parameters.getRecords(): List<JasperReportsRecord> =
    get(Records.NAME, Records.CLASS)

fun Parameters.getParameters(): JasperReportsParameters =
    get(JasperReportsReportGeneratorParametersConstants.Parameters.NAME, JasperReportsReportGeneratorParametersConstants.Parameters.CLASS)

fun Parameters.getParametersOrNull(): JasperReportsParameters? =
    getOrNull(JasperReportsReportGeneratorParametersConstants.Parameters.NAME, JasperReportsReportGeneratorParametersConstants.Parameters.CLASS)

fun Parameters.getParametersOrDefault(default: JasperReportsParameters): JasperReportsParameters =
    getOrDefault(
        JasperReportsReportGeneratorParametersConstants.Parameters.NAME,
        JasperReportsReportGeneratorParametersConstants.Parameters.CLASS,
        default
    )