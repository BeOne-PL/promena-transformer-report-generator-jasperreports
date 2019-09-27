package pl.beone.promena.transformer.report.jasperreports

import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.matchers.withClue
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.beone.lib.junit5.extension.docker.external.DockerExtension
import pl.beone.promena.communication.file.model.internal.fileCommunicationParameters
import pl.beone.promena.communication.memory.model.internal.memoryCommunicationParameters
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants.APPLICATION_PDF
import pl.beone.promena.transformer.internal.model.data.toFileData
import pl.beone.promena.transformer.internal.model.data.toMemoryData
import pl.beone.promena.transformer.internal.model.metadata.emptyMetadata
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.jasperReportsReportParameters
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.model.emptyJasperReportsParameters
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.model.emptyJasperReportsRecord
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.model.plus
import java.math.BigDecimal
import java.sql.Time
import java.sql.Timestamp
import java.util.*

@ExtendWith(DockerExtension::class)
class JasperReportsReportTransformerTest {

    companion object {
        private val reportBytes = "/template/simple-all-parameters-and-fields.jrxml".getResourceBytes()

        private val targetMediaType = APPLICATION_PDF

        private val emptyParameters = emptyJasperReportsParameters()
        private val allTypesParameters = emptyJasperReportsParameters() +
                ("string" to "value") +
                ("boolean" to true) +
                ("double" to 1.1) +
                ("float" to 2.2f) +
                ("integer" to 3) +
                ("long" to 4L) +
                ("short" to 5.toShort()) +
                ("bigDecimal" to BigDecimal("6")) +
                ("date" to Date(2213213)) +
                ("sqlDate" to java.sql.Date(1213213)) +
                ("sqlTime" to Time(213123321)) +
                ("sqlTimestamp" to Timestamp(123123213))

        private val emptyRecord = emptyJasperReportsRecord()
        private val allTypesRecord = emptyJasperReportsRecord() +
                ("string" to "value") +
                ("boolean" to true) +
                ("double" to 1.1) +
                ("float" to 2.2f) +
                ("integer" to 3) +
                ("long" to 4L) +
                ("short" to 5.toShort()) +
                ("bigDecimal" to BigDecimal("6")) +
                ("date" to Date(2213213)) +
                ("sqlDate" to java.sql.Date(1213213)) +
                ("sqlTime" to Time(213123321)) +
                ("sqlTimestamp" to Timestamp(123123213))
        private val allTypesRecord2 = emptyJasperReportsRecord() +
                ("string" to "fieldValue") +
                ("boolean" to false) +
                ("double" to 11.1) +
                ("float" to 12.2f) +
                ("integer" to 13) +
                ("long" to 14L) +
                ("short" to 15.toShort()) +
                ("bigDecimal" to BigDecimal("16")) +
                ("date" to Date(756129600000)) +
                ("sqlDate" to java.sql.Date(-5638082400000)) +
                ("sqlTime" to Time(687092400000)) +
                ("sqlTimestamp" to Timestamp(946684800000))
    }

    @Test
    fun transform_MemoryData() {
        JasperReportsReportTransformer(memoryCommunicationParameters())
            .transform(
                reportBytes.toMemoryData().toJasperDataDescriptor(),
                targetMediaType,
                jasperReportsReportParameters(allTypesRecord + allTypesRecord2, allTypesParameters)
            ).let { transformedDataDescriptor ->
                val descriptors = transformedDataDescriptor.descriptors
                withClue("Transformed data should contain only <1> element") { descriptors shouldHaveSize 1 }

                descriptors[0].let {
                    it.data.getText() shouldHaveSize 4
                    it.data.getText()[0] shouldBe "value true 1.1 2.2 3 4 5 6 1/1/70, 12:36 AM 1/1/70, 12:20 AM 1/3/70, 11:12 AM 1/2/70, 10:12 AM"
                    it.data.getText()[1] shouldBe "SEPARATOR"
                    it.data.getText()[2] shouldBe "value true 1.1 2.2 3 4 5 6 1/1/70, 12:36 AM 1/1/70, 12:20 AM 1/3/70, 11:12 AM 1/2/70, 10:12 AM"
                    it.data.getText()[3] shouldBe "fieldValue false 11.1 12.2 13 14 15 16 12/17/93, 12:00 PM 5/3/91, 10:00 AM 10/10/91, 11:00 AM 1/1/00, 12:00 AM"
                    it.metadata shouldBe emptyMetadata()
                }
            }
    }

    @Test
    fun transform_MemoryData_emptyFirstRecord() {
        JasperReportsReportTransformer(memoryCommunicationParameters())
            .transform(
                reportBytes.toMemoryData().toJasperDataDescriptor(),
                targetMediaType,
                jasperReportsReportParameters(emptyRecord + allTypesRecord2, allTypesParameters)
            ).let { transformedDataDescriptor ->
                val descriptors = transformedDataDescriptor.descriptors
                withClue("Transformed data should contain only <1> element") { descriptors shouldHaveSize 1 }

                descriptors[0].let {
                    it.data.getText() shouldHaveSize 4
                    it.data.getText()[0] shouldBe "value true 1.1 2.2 3 4 5 6 1/1/70, 12:36 AM 1/1/70, 12:20 AM 1/3/70, 11:12 AM 1/2/70, 10:12 AM"
                    it.data.getText()[1] shouldBe "SEPARATOR"
                    it.data.getText()[2] shouldBe "null null null null null null null null null null null null"
                    it.data.getText()[3] shouldBe "fieldValue false 11.1 12.2 13 14 15 16 12/17/93, 12:00 PM 5/3/91, 10:00 AM 10/10/91, 11:00 AM 1/1/00, 12:00 AM"
                    it.metadata shouldBe emptyMetadata()
                }
            }
    }

    @Test
    fun transform_MemoryData_emptyParameters() {
        JasperReportsReportTransformer(memoryCommunicationParameters())
            .transform(
                reportBytes.toMemoryData().toJasperDataDescriptor(),
                targetMediaType,
                jasperReportsReportParameters(listOf(allTypesRecord2), emptyParameters)
            ).let { transformedDataDescriptor ->
                val descriptors = transformedDataDescriptor.descriptors
                withClue("Transformed data should contain only <1> element") { descriptors shouldHaveSize 1 }

                descriptors[0].let {
                    it.data.getText() shouldHaveSize 3
                    it.data.getText()[0] shouldBe "null null null null null null null null null null null null"
                    it.data.getText()[1] shouldBe "SEPARATOR"
                    it.data.getText()[2] shouldBe "fieldValue false 11.1 12.2 13 14 15 16 12/17/93, 12:00 PM 5/3/91, 10:00 AM 10/10/91, 11:00 AM 1/1/00, 12:00 AM"
                    it.metadata shouldBe emptyMetadata()
                }
            }
    }

    @Test
    fun transform_MemoryData_noParameters() {
        JasperReportsReportTransformer(memoryCommunicationParameters())
            .transform(
                reportBytes.toMemoryData().toJasperDataDescriptor(),
                targetMediaType,
                jasperReportsReportParameters(listOf(allTypesRecord2))
            ).let { transformedDataDescriptor ->
                val descriptors = transformedDataDescriptor.descriptors
                withClue("Transformed data should contain only <1> element") { descriptors shouldHaveSize 1 }

                descriptors[0].let {
                    it.data.getText() shouldHaveSize 3
                    it.data.getText()[0] shouldBe "null null null null null null null null null null null null"
                    it.data.getText()[1] shouldBe "SEPARATOR"
                    it.data.getText()[2] shouldBe "fieldValue false 11.1 12.2 13 14 15 16 12/17/93, 12:00 PM 5/3/91, 10:00 AM 10/10/91, 11:00 AM 1/1/00, 12:00 AM"
                    it.metadata shouldBe emptyMetadata()
                }
            }
    }

    @Test
    fun transform_FileData_emptyParametersAndEmptyRecord() {
        val directory = createTempDir()

        JasperReportsReportTransformer(fileCommunicationParameters(directory))
            .transform(
                reportBytes.inputStream().toFileData(directory).toJasperDataDescriptor(),
                targetMediaType,
                jasperReportsReportParameters(listOf(emptyRecord), emptyParameters)
            ).let { transformedDataDescriptor ->
                val descriptors = transformedDataDescriptor.descriptors
                withClue("Transformed data should contain only <1> element") { descriptors shouldHaveSize 1 }

                descriptors[0].let {
                    it.data.getText() shouldHaveSize 3
                    it.data.getText()[0] shouldBe "null null null null null null null null null null null null"
                    it.data.getText()[1] shouldBe "SEPARATOR"
                    it.data.getText()[2] shouldBe "null null null null null null null null null null null null"
                    it.metadata shouldBe emptyMetadata()
                }
            }
    }
}