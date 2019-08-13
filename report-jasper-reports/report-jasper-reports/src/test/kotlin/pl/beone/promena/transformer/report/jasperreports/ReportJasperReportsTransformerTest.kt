package pl.beone.promena.transformer.report.jasperreports

import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.beone.lib.junit5.extension.docker.external.DockerExtension
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants
import pl.beone.promena.transformer.internal.communication.communicationParameters
import pl.beone.promena.transformer.internal.model.data.toFileData
import pl.beone.promena.transformer.internal.model.data.toMemoryData
import pl.beone.promena.transformer.internal.model.metadata.emptyMetadata
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.element.emptyJasperReportsParameters
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.element.emptyJasperReportsRecord
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.element.plus
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.reportJasperReportsParameters
import java.math.BigDecimal
import java.sql.Time
import java.sql.Timestamp
import java.util.*

@ExtendWith(DockerExtension::class)
class ReportJasperReportsTransformerTest {

    companion object {
        private val reportBytes = "/reports/simple-all-parameters-and-fields.jrxml".getResourceBytes()

        private val targetMediaType = MediaTypeConstants.APPLICATION_PDF

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
        ReportJasperReportsTransformer(communicationParameters("memory"))
            .transform(
                reportBytes.toMemoryData().toJasperDataDescriptor(),
                targetMediaType,
                reportJasperReportsParameters(allTypesRecord + allTypesRecord2, allTypesParameters)
            )
            .let {
                it.descriptors shouldHaveSize 1

                it.descriptors[0].let { transformedDataDescriptor ->
                    transformedDataDescriptor.data.getText() shouldHaveSize 4
                    transformedDataDescriptor.data.getText()[0] shouldBe "value true 1.1 2.2 3 4 5 6 1/1/70, 12:36 AM 1/1/70, 12:20 AM 1/3/70, 11:12 AM 1/2/70, 10:12 AM"
                    transformedDataDescriptor.data.getText()[1] shouldBe "SEPARATOR"
                    transformedDataDescriptor.data.getText()[2] shouldBe "value true 1.1 2.2 3 4 5 6 1/1/70, 12:36 AM 1/1/70, 12:20 AM 1/3/70, 11:12 AM 1/2/70, 10:12 AM"
                    transformedDataDescriptor.data.getText()[3] shouldBe "fieldValue false 11.1 12.2 13 14 15 16 12/17/93, 12:00 PM 5/3/91, 10:00 AM 10/10/91, 11:00 AM 1/1/00, 12:00 AM"
                    transformedDataDescriptor.metadata shouldBe emptyMetadata()
                }
            }
    }

    @Test
    fun transform_MemoryData_emptyFirstRecord() {
        ReportJasperReportsTransformer(communicationParameters("memory"))
            .transform(
                reportBytes.toMemoryData().toJasperDataDescriptor(),
                targetMediaType,
                reportJasperReportsParameters(emptyRecord + allTypesRecord2, allTypesParameters)
            )
            .let {
                it.descriptors shouldHaveSize 1

                it.descriptors[0].let { transformedDataDescriptor ->
                    transformedDataDescriptor.data.getText() shouldHaveSize 4
                    transformedDataDescriptor.data.getText()[0] shouldBe "value true 1.1 2.2 3 4 5 6 1/1/70, 12:36 AM 1/1/70, 12:20 AM 1/3/70, 11:12 AM 1/2/70, 10:12 AM"
                    transformedDataDescriptor.data.getText()[1] shouldBe "SEPARATOR"
                    transformedDataDescriptor.data.getText()[2] shouldBe "null null null null null null null null null null null null"
                    transformedDataDescriptor.data.getText()[3] shouldBe "fieldValue false 11.1 12.2 13 14 15 16 12/17/93, 12:00 PM 5/3/91, 10:00 AM 10/10/91, 11:00 AM 1/1/00, 12:00 AM"
                    transformedDataDescriptor.metadata shouldBe emptyMetadata()
                }
            }
    }

    @Test
    fun transform_MemoryData_emptyParameters() {
        ReportJasperReportsTransformer(communicationParameters("memory"))
            .transform(
                reportBytes.toMemoryData().toJasperDataDescriptor(),
                targetMediaType,
                reportJasperReportsParameters(listOf(allTypesRecord2), emptyParameters)
            )
            .let {
                it.descriptors shouldHaveSize 1

                it.descriptors[0].let { transformedDataDescriptor ->
                    transformedDataDescriptor.data.getText() shouldHaveSize 3
                    transformedDataDescriptor.data.getText()[0] shouldBe "null null null null null null null null null null null null"
                    transformedDataDescriptor.data.getText()[1] shouldBe "SEPARATOR"
                    transformedDataDescriptor.data.getText()[2] shouldBe "fieldValue false 11.1 12.2 13 14 15 16 12/17/93, 12:00 PM 5/3/91, 10:00 AM 10/10/91, 11:00 AM 1/1/00, 12:00 AM"
                    transformedDataDescriptor.metadata shouldBe emptyMetadata()
                }
            }
    }

    @Test
    fun transform_MemoryData_noParameters() {
        ReportJasperReportsTransformer(communicationParameters("memory"))
            .transform(
                reportBytes.toMemoryData().toJasperDataDescriptor(),
                targetMediaType,
                reportJasperReportsParameters(listOf(allTypesRecord2))
            )
            .let {
                it.descriptors shouldHaveSize 1

                it.descriptors[0].let { transformedDataDescriptor ->
                    transformedDataDescriptor.data.getText() shouldHaveSize 3
                    transformedDataDescriptor.data.getText()[0] shouldBe "null null null null null null null null null null null null"
                    transformedDataDescriptor.data.getText()[1] shouldBe "SEPARATOR"
                    transformedDataDescriptor.data.getText()[2] shouldBe "fieldValue false 11.1 12.2 13 14 15 16 12/17/93, 12:00 PM 5/3/91, 10:00 AM 10/10/91, 11:00 AM 1/1/00, 12:00 AM"
                    transformedDataDescriptor.metadata shouldBe emptyMetadata()
                }
            }
    }

    @Test
    fun transform_FileData_emptyParametersAndEmptyRecord() {
        val locationFile = createTempDir()

        ReportJasperReportsTransformer(communicationParameters("file", mapOf("location" to locationFile.toURI())))
            .transform(
                reportBytes.inputStream().toFileData(locationFile).toJasperDataDescriptor(),
                targetMediaType,
                reportJasperReportsParameters(listOf(emptyRecord), emptyParameters)
            )
            .let {
                it.descriptors shouldHaveSize 1

                it.descriptors[0].let { transformedDataDescriptor ->
                    transformedDataDescriptor.data.getText() shouldHaveSize 3
                    transformedDataDescriptor.data.getText()[0] shouldBe "null null null null null null null null null null null null"
                    transformedDataDescriptor.data.getText()[1] shouldBe "SEPARATOR"
                    transformedDataDescriptor.data.getText()[2] shouldBe "null null null null null null null null null null null null"
                    transformedDataDescriptor.metadata shouldBe emptyMetadata()
                }
            }
    }

//    @Test
//    fun canTransform_targetMediaTypeIsNotTextPlain_shouldThrowTransformerCouldNotTransformException() {
//        shouldThrow<TransformerCouldNotTransformException> {
//            AppenderKotlinTransformer(mockk())
//                .canTransform(
//                    emptyDataDescriptor(),
//                    MediaTypeConstants.APPLICATION_PDF,
//                    appenderKotlinParameters(example = "test")
//                )
//        }.message shouldBe "Supported transformation: text/plain -> text/plain"
//    }
//
//    @Test
//    fun canTransform_dataDescriptorMediaTypeIsNotTextPlain_shouldThrowTransformerCouldNotTransformException() {
//        shouldThrow<TransformerCouldNotTransformException> {
//            AppenderKotlinTransformer(mockk())
//                .canTransform(
//                    singleDataDescriptor("".toMemoryData() to MediaTypeConstants.APPLICATION_PDF to emptyMetadata()),
//                    MediaTypeConstants.TEXT_PLAIN,
//                    appenderKotlinParameters(example = "test")
//                )
//        }.message shouldBe "Supported transformation: text/plain -> text/plain"
//    }
//
//    @Test
//    fun canTransform_noMandatoryParameter_shouldThrowTransformerCouldNotTransformException() {
//        shouldThrow<TransformerCouldNotTransformException> {
//            AppenderKotlinTransformer(mockk())
//                .canTransform(
//                    emptyDataDescriptor(),
//                    MediaTypeConstants.TEXT_PLAIN,
//                    emptyParameters()
//                )
//        }.message shouldBe "Mandatory parameter: ${AppenderKotlinConstants.Parameters.EXAMPLE}"
//    }
}