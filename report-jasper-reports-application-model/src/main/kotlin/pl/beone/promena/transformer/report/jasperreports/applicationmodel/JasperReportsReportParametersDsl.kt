@file:JvmName("JasperReportsReportParametersDsl")

package pl.beone.promena.transformer.report.jasperreports.applicationmodel

import pl.beone.promena.transformer.contract.model.Parameters
import pl.beone.promena.transformer.internal.model.parameters.MapParameters
import pl.beone.promena.transformer.internal.model.parameters.addIfNotNull
import pl.beone.promena.transformer.internal.model.parameters.emptyParameters
import pl.beone.promena.transformer.internal.model.parameters.plus
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.JasperReportsReportParametersConstants.Records
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.model.JasperReportsParameters
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.model.JasperReportsRecord

fun jasperReportsReportParameters(records: List<JasperReportsRecord>, parameters: JasperReportsParameters? = null): MapParameters =
    emptyParameters() +
            (Records.NAME to records) addIfNotNull
            (JasperReportsReportParametersConstants.Parameters.NAME to parameters)

fun Parameters.getRecords(): List<JasperReportsRecord> =
    get(Records.NAME, Records.CLASS)

fun Parameters.getParameters(): JasperReportsParameters =
    get(JasperReportsReportParametersConstants.Parameters.NAME, JasperReportsReportParametersConstants.Parameters.CLASS)