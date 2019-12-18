package pl.beone.promena.transformer.reportgenerator.jasperreports.applicationmodel

import pl.beone.lib.typeconverter.internal.getClazz
import java.io.Serializable

object JasperReportsReportGeneratorParametersConstants {

    object Records {
        const val NAME = "records"
        @JvmField
        val CLASS = getClazz<List<Map<String, Serializable>>>()
    }

    object Parameters {
        const val NAME = "parameters"
        @JvmField
        val CLASS = getClazz<Map<String, Serializable>>()
    }
}