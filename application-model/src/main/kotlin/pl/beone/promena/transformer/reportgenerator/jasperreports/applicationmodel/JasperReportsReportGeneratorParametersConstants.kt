package pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel

import pl.beone.lib.typeconverter.internal.getClazz
import pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.model.JasperReportsParameters
import pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel.model.JasperReportsRecord

object JasperReportsReportGeneratorParametersConstants {

    object Records {
        const val NAME = "records"
        @JvmField
        val CLASS = getClazz<List<JasperReportsRecord>>()
    }

    object Parameters {
        const val NAME = "parameters"
        @JvmField
        val CLASS = JasperReportsParameters::class.java
    }
}