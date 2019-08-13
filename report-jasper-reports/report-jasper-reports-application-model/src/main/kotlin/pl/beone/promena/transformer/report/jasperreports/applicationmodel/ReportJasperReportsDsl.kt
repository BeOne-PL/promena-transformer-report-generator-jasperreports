@file:JvmName("ReportJasperReportsDsl")

package pl.beone.promena.transformer.report.jasperreports.applicationmodel

import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants
import pl.beone.promena.transformer.contract.model.Parameters
import pl.beone.promena.transformer.contract.transformation.Transformation
import pl.beone.promena.transformer.contract.transformation.singleTransformation

fun reportTransformation(parameters: Parameters): Transformation.Single =
    singleTransformation(ReportJasperReportsConstants.TRANSFORMER_NAME, MediaTypeConstants.APPLICATION_PDF, parameters)

fun reportJasperReportsTransformation(parameters: Parameters): Transformation.Single =
    singleTransformation(ReportJasperReportsConstants.TRANSFORMER_ID, MediaTypeConstants.APPLICATION_PDF, parameters)