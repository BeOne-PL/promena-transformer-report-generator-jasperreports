package pl.beone.promena.transformer.jasperreport.parameters

import pl.beone.lib.typeconverter.internal.getClazz
import pl.beone.promena.transformer.contract.model.Parameters
import pl.beone.promena.transformer.jasperreport.applicationmodel.parameters.JasperReportParameters
import pl.beone.promena.transformer.jasperreport.applicationmodel.parameters.JasperReportParametersConstants
import pl.beone.promena.transformer.jasperreport.applicationmodel.parameters.JasperReportParametersConstants.Records
import pl.beone.promena.transformer.jasperreport.applicationmodel.parameters.JasperReportParametersConstants.Root
import pl.beone.promena.transformer.jasperreport.applicationmodel.parameters.JasperReportRecord

internal fun Parameters.getRoot(): Parameters =
        getParameters(Root)

internal fun Parameters.getReportParameters(): JasperReportParameters =
        getRoot().get(JasperReportParametersConstants.Parameters, getClazz())

internal fun Parameters.getReportRecords(): List<JasperReportRecord> =
        getRoot().get(Records, getClazz())