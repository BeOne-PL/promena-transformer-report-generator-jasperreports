package pl.beone.promena.transformer.report.jasperreports.applicationmodel

import pl.beone.promena.transformer.report.jasperreports.applicationmodel.model.JasperReportsParameters
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.model.JasperReportsRecord

object JasperReportsReportParametersConstants {

    object Records {
        const val NAME = "RECORDS"
        val CLASS = getClazz<List<JasperReportsRecord>>()
    }

    object Parameters {
        const val NAME = "PARAMETERS"
        val CLASS = JasperReportsParameters::class.java
    }

    private inline fun <reified T : Any> getClazz(): Class<T> =
        T::class.java
}