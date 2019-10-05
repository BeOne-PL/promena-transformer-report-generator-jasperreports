package pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel

import pl.beone.promena.transformer.contract.transformer.transformerId

object JasperReportsReportGeneratorConstants {

    const val TRANSFORMER_NAME = "report generator"

    const val TRANSFORMER_SUB_NAME = "JasperReports"

    @JvmField
    val TRANSFORMER_ID = transformerId(TRANSFORMER_NAME, TRANSFORMER_SUB_NAME)
}