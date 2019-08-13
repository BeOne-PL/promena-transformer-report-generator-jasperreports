package pl.beone.promena.transformer.report.jasperreports.applicationmodel

import pl.beone.promena.transformer.contract.transformer.transformerId

object ReportJasperReportsConstants {

    const val TRANSFORMER_NAME = "report"

    const val TRANSFORMER_SUB_NAME = "jasper-reports"

    @JvmField
    val TRANSFORMER_ID = transformerId(TRANSFORMER_NAME, TRANSFORMER_SUB_NAME)
}