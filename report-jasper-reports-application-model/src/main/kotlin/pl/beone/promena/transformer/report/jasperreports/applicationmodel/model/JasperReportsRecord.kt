package pl.beone.promena.transformer.report.jasperreports.applicationmodel.model

import java.io.Serializable

data class JasperReportsRecord internal constructor(
    val elements: Map<String, Serializable>
) {

    companion object {
        @JvmStatic
        fun of(elements: Map<String, Serializable>): JasperReportsRecord =
            JasperReportsRecord(elements)
    }
}
