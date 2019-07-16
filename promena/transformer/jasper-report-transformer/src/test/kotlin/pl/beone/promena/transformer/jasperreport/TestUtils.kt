package pl.beone.promena.transformer.jasperreport

import pl.beone.promena.transformer.internal.model.parameters.MapParameters
import pl.beone.promena.transformer.jasperreport.applicationmodel.parameters.JasperReportParametersConstants.*

internal fun createParameters(reportParameters: Any?, reportRecords: Any?): MapParameters =
        MapParameters(mapOf(Root to mapTransformerParameters(reportParameters, reportRecords)))

private fun mapTransformerParameters(reportParameters: Any?, reportRecords: Any?): MapParameters =
        MapParameters(
                (if (reportParameters != null) mapOf(Parameters to reportParameters) else emptyMap()) +
                (if (reportRecords != null) mapOf(Records to reportRecords) else emptyMap())
        )