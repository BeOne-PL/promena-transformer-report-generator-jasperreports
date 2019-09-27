@file:JvmName("JasperReportsReportDsl")

package pl.beone.promena.transformer.report.jasperreports.applicationmodel

import pl.beone.promena.transformer.applicationmodel.mediatype.MediaType
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants.APPLICATION_PDF
import pl.beone.promena.transformer.contract.model.Parameters
import pl.beone.promena.transformer.contract.transformation.Transformation
import pl.beone.promena.transformer.contract.transformation.singleTransformation
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.JasperReportsReportConstants.TRANSFORMER_ID
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.JasperReportsReportConstants.TRANSFORMER_NAME

fun reportTransformation(targetMediaType: MediaType, parameters: Parameters): Transformation.Single =
    singleTransformation(TRANSFORMER_NAME, targetMediaType, parameters)

fun jasperReportsReportTransformation(parameters: Parameters): Transformation.Single =
    singleTransformation(TRANSFORMER_ID, APPLICATION_PDF, parameters)