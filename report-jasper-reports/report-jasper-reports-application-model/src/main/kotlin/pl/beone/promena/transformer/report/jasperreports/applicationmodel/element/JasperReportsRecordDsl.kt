@file:JvmName("JasperReportsRecordDsl")

package pl.beone.promena.transformer.report.jasperreports.applicationmodel.element

import java.io.Serializable

fun emptyJasperReportsRecord(): JasperReportsRecord =
    JasperReportsRecord.of(emptyMap())

fun jasperReportsRecord(elements: Map<String, Serializable>): JasperReportsRecord =
    JasperReportsRecord.of(elements)

operator fun JasperReportsRecord.plus(entry: Pair<String, Serializable>): JasperReportsRecord =
    JasperReportsRecord.of(elements + entry)

operator fun JasperReportsRecord.plus(record: JasperReportsRecord): List<JasperReportsRecord> =
    listOf(this, record)