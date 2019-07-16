package pl.beone.promena.transformer.jasperreport

import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.shouldBe
import org.junit.Test
import org.junit.runner.RunWith
import pl.beone.lib.dockertestrunner.external.DockerTestRunner
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants
import pl.beone.promena.transformer.contract.descriptor.DataDescriptor
import pl.beone.promena.transformer.contract.model.Data
import pl.beone.promena.transformer.internal.communication.MapCommunicationParameters
import pl.beone.promena.transformer.internal.model.data.FileData
import pl.beone.promena.transformer.internal.model.data.MemoryData
import pl.beone.promena.transformer.internal.model.metadata.MapMetadata
import pl.beone.promena.transformer.jasperreport.applicationmodel.parameters.JasperReportParameters
import pl.beone.promena.transformer.jasperreport.applicationmodel.parameters.JasperReportRecord
import java.io.File
import java.io.IOException
import java.math.BigDecimal
import java.net.URI
import java.sql.Time
import java.sql.Timestamp
import java.util.*

@RunWith(DockerTestRunner::class)
class JasperReportTransformerTest {

    companion object {
        private val targetMediaType = MediaTypeConstants.APPLICATION_PDF

        private val emptyReportParameters = JasperReportParameters.empty()
        private val allTypesParametersRecord = JasperReportParameters.Builder()
                .add("string", "value")
                .add("boolean", true)
                .add("double", 1.1)
                .add("float", 2.2f)
                .add("integer", 3)
                .add("long", 4L)
                .add("short", 5.toShort())
                .add("bigDecimal", BigDecimal("6"))
                .add("date", Date(2213213))
                .add("sqlDate", java.sql.Date(1213213))
                .add("sqlTime", Time(213123321))
                .add("sqlTimestamp", Timestamp(123123213))
                .build()

        private val emptyReportRecord = JasperReportRecord.empty()
        private val allTypesReportRecord = JasperReportRecord.Builder()
                .add("string", "value")
                .add("boolean", true)
                .add("double", 1.1)
                .add("float", 2.2f)
                .add("integer", 3)
                .add("long", 4L)
                .add("short", 5.toShort())
                .add("bigDecimal", BigDecimal("6"))
                .add("date", Date(2213213))
                .add("sqlDate", java.sql.Date(1213213))
                .add("sqlTime", Time(213123321))
                .add("sqlTimestamp", Timestamp(123123213))
                .build()
        private val allTypesReportRecord2 = JasperReportRecord.Builder()
                .add("string", "fieldValue")
                .add("boolean", false)
                .add("double", 11.1)
                .add("float", 12.2f)
                .add("integer", 13)
                .add("long", 14L)
                .add("short", 15.toShort())
                .add("bigDecimal", BigDecimal("16"))
                .add("date", Date(756129600000))
                .add("sqlDate", java.sql.Date(-5638082400000))
                .add("sqlTime", Time(687092400000))
                .add("sqlTimestamp", Timestamp(946684800000))
                .build()
    }

    private val reportResourceBytes = "/reports/simple-all-parameters-and-fields.jrxml".getResourceBytes()

    @Test
    fun transform_MemoryData() {
        val transformer = JasperReportTransformer(MapCommunicationParameters(mapOf("id" to "memory")))

        val dataDescriptor = reportResourceBytes
                .toMemoryData()
                .toJasperDataDescriptor()

        transformer.transform(listOf(dataDescriptor),
                              targetMediaType,
                              createParameters(allTypesParametersRecord, listOf(allTypesReportRecord, allTypesReportRecord2)))
                .let { transformedDataDescriptors ->
                    transformedDataDescriptors shouldHaveSize 1

                    transformedDataDescriptors[0].let { transformedDataDescriptor ->
                        transformedDataDescriptor.data.getText() shouldHaveSize 4
                        transformedDataDescriptor.data.getText()[0] shouldBe "value true 1.1 2.2 3 4 5 6 1/1/70, 12:36 AM 1/1/70, 12:20 AM 1/3/70, 11:12 AM 1/2/70, 10:12 AM"
                        transformedDataDescriptor.data.getText()[1] shouldBe "SEPARATOR"
                        transformedDataDescriptor.data.getText()[2] shouldBe "value true 1.1 2.2 3 4 5 6 1/1/70, 12:36 AM 1/1/70, 12:20 AM 1/3/70, 11:12 AM 1/2/70, 10:12 AM"
                        transformedDataDescriptor.data.getText()[3] shouldBe "fieldValue false 11.1 12.2 13 14 15 16 12/17/93, 12:00 PM 5/3/91, 10:00 AM 10/10/91, 11:00 AM 1/1/00, 12:00 AM"
                        transformedDataDescriptor.metadata shouldBe MapMetadata.empty()
                    }
                }
    }

    @Test
    fun transform_MemoryData_emptyFirstRecord() {
        val transformer = JasperReportTransformer(MapCommunicationParameters(mapOf("id" to "memory")))

        val dataDescriptor = reportResourceBytes
                .toMemoryData()
                .toJasperDataDescriptor()

        transformer.transform(listOf(dataDescriptor),
                              targetMediaType,
                              createParameters(allTypesParametersRecord, listOf(emptyReportRecord, allTypesReportRecord2)))
                .let { transformedDataDescriptors ->
                    transformedDataDescriptors shouldHaveSize 1

                    transformedDataDescriptors[0].let { transformedDataDescriptor ->
                        transformedDataDescriptor.data.getText() shouldHaveSize 4
                        transformedDataDescriptor.data.getText()[0] shouldBe "value true 1.1 2.2 3 4 5 6 1/1/70, 12:36 AM 1/1/70, 12:20 AM 1/3/70, 11:12 AM 1/2/70, 10:12 AM"
                        transformedDataDescriptor.data.getText()[1] shouldBe "SEPARATOR"
                        transformedDataDescriptor.data.getText()[2] shouldBe "null null null null null null null null null null null null"
                        transformedDataDescriptor.data.getText()[3] shouldBe "fieldValue false 11.1 12.2 13 14 15 16 12/17/93, 12:00 PM 5/3/91, 10:00 AM 10/10/91, 11:00 AM 1/1/00, 12:00 AM"
                        transformedDataDescriptor.metadata shouldBe MapMetadata.empty()
                    }
                }
    }

    @Test
    fun transform_MemoryData_emptyParameters() {
        val transformer = JasperReportTransformer(MapCommunicationParameters(mapOf("id" to "memory")))

        val dataDescriptor = reportResourceBytes
                .toMemoryData()
                .toJasperDataDescriptor()

        transformer.transform(listOf(dataDescriptor), targetMediaType, createParameters(emptyReportParameters, listOf(allTypesReportRecord2)))
                .let { transformedDataDescriptors ->
                    transformedDataDescriptors shouldHaveSize 1

                    transformedDataDescriptors[0].let { transformedDataDescriptor ->
                        transformedDataDescriptor.data.getText() shouldHaveSize 3
                        transformedDataDescriptor.data.getText()[0] shouldBe "null null null null null null null null null null null null"
                        transformedDataDescriptor.data.getText()[1] shouldBe "SEPARATOR"
                        transformedDataDescriptor.data.getText()[2] shouldBe "fieldValue false 11.1 12.2 13 14 15 16 12/17/93, 12:00 PM 5/3/91, 10:00 AM 10/10/91, 11:00 AM 1/1/00, 12:00 AM"
                        transformedDataDescriptor.metadata shouldBe MapMetadata.empty()
                    }
                }
    }

    @Test
    fun transform_MemoryData_noParameters() {
        val transformer = JasperReportTransformer(MapCommunicationParameters(mapOf("id" to "memory")))

        val dataDescriptor = reportResourceBytes
                .toMemoryData()
                .toJasperDataDescriptor()

        transformer.transform(listOf(dataDescriptor), targetMediaType, createParameters(null, listOf(allTypesReportRecord2)))
                .let { transformedDataDescriptors ->
                    transformedDataDescriptors shouldHaveSize 1

                    transformedDataDescriptors[0].let { transformedDataDescriptor ->
                        transformedDataDescriptor.data.getText() shouldHaveSize 3
                        transformedDataDescriptor.data.getText()[0] shouldBe "null null null null null null null null null null null null"
                        transformedDataDescriptor.data.getText()[1] shouldBe "SEPARATOR"
                        transformedDataDescriptor.data.getText()[2] shouldBe "fieldValue false 11.1 12.2 13 14 15 16 12/17/93, 12:00 PM 5/3/91, 10:00 AM 10/10/91, 11:00 AM 1/1/00, 12:00 AM"
                        transformedDataDescriptor.metadata shouldBe MapMetadata.empty()
                    }
                }
    }

    @Test
    fun transform_FileData_emptyParametersAndEmptyRecords() {
        val transformer = JasperReportTransformer(MapCommunicationParameters(mapOf("id" to "file",
                                                                                   "location" to URI("file:/tmp"))))

        val dataDescriptor = reportResourceBytes
                .toFileData("/tmp")
                .toJasperDataDescriptor()

        transformer.transform(listOf(dataDescriptor), targetMediaType, createParameters(emptyReportParameters, listOf(emptyReportRecord)))
                .let { transformedDataDescriptors ->
                    transformedDataDescriptors shouldHaveSize 1

                    transformedDataDescriptors[0].let { transformedDataDescriptor ->
                        transformedDataDescriptor.data.getText() shouldHaveSize 3
                        transformedDataDescriptor.data.getText()[0] shouldBe "null null null null null null null null null null null null"
                        transformedDataDescriptor.data.getText()[1] shouldBe "SEPARATOR"
                        transformedDataDescriptor.data.getText()[2] shouldBe "null null null null null null null null null null null null"
                        transformedDataDescriptor.metadata shouldBe MapMetadata.empty()
                    }
                }
    }

    private fun String.getResourceBytes(): ByteArray =
            object {}::class.java.getResourceAsStream(this)
                    ?.readAllBytes() ?: throw IOException("Couldn't find <$this> resource file")

    private fun ByteArray.toMemoryData(): MemoryData =
            MemoryData(this)

    private fun ByteArray.toFileData(location: String): FileData =
            FileData(createTempFile(directory = File(location)).apply {
                writeBytes(this@toFileData)
            }.toURI())

    private fun Data.toJasperDataDescriptor(): DataDescriptor =
            DataDescriptor(this, MediaTypeConstants.TEXT_XML, MapMetadata.empty())

    private fun Data.getText(): List<String> =
            PdfTextExtractor.getTextFromPage(PdfReader(getBytes()), 1)
                    .split("\n")
                    .drop(1)

}
