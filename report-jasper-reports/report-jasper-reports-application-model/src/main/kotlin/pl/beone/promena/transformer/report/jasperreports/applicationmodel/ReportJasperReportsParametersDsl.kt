@file:JvmName("ReportJasperReportsParametersDsl")

package pl.beone.promena.transformer.report.jasperreports.applicationmodel

import pl.beone.promena.transformer.internal.model.parameters.MapParameters
import pl.beone.promena.transformer.internal.model.parameters.addIfNotNull
import pl.beone.promena.transformer.internal.model.parameters.emptyParameters
import pl.beone.promena.transformer.internal.model.parameters.plus
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.element.JasperReportsParameters
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.element.JasperReportsRecord

fun reportJasperReportsParameters(records: List<JasperReportsRecord>, parameters: JasperReportsParameters? = null): MapParameters =
    emptyParameters() +
            (ReportJasperReportsParametersConstants.RECORDS to records) addIfNotNull
            (ReportJasperReportsParametersConstants.PARAMETERS to parameters)