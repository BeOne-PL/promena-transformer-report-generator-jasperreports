package pl.beone.promena.transformer.report.jasperreports.applicationmodel.element

import java.io.Serializable

data class JasperReportsRecord private constructor(
    val elements: Map<String, Serializable>
) {

    companion object {
        @JvmStatic
        fun of(elements: Map<String, Serializable>): JasperReportsRecord =
            JasperReportsRecord(elements)
    }
}
