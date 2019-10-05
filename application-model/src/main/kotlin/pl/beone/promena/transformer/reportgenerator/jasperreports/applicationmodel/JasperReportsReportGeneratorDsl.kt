@file:JvmName("JasperReportsReportGeneratorDsl")

package pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel

import pl.beone.promena.transformer.applicationmodel.mediatype.MediaType
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants.APPLICATION_PDF
import pl.beone.promena.transformer.contract.model.Parameters
import pl.beone.promena.transformer.contract.transformation.Transformation
import pl.beone.promena.transformer.contract.transformation.singleTransformation
import pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.JasperReportsReportGeneratorConstants.TRANSFORMER_ID
import pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.JasperReportsReportGeneratorConstants.TRANSFORMER_NAME

fun reportGeneratorTransformation(targetMediaType: MediaType, parameters: Parameters): Transformation.Single =
    singleTransformation(TRANSFORMER_NAME, targetMediaType, parameters)

fun jasperReportsReportGeneratorTransformation(parameters: Parameters): Transformation.Single =
    singleTransformation(TRANSFORMER_ID, APPLICATION_PDF, parameters)