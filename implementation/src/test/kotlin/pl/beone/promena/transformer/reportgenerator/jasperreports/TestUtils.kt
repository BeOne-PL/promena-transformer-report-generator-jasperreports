package pl.beone.promena.transformer.reportgenerator.jasperreports

import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.matchers.instanceOf
import io.kotlintest.matchers.withClue
import io.kotlintest.shouldBe
import pl.beone.promena.communication.file.model.internal.fileCommunicationParameters
import pl.beone.promena.communication.memory.model.internal.memoryCommunicationParameters
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants.APPLICATION_PDF
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants.TEXT_XML
import pl.beone.promena.transformer.contract.communication.CommunicationParameters
import pl.beone.promena.transformer.contract.data.singleDataDescriptor
import pl.beone.promena.transformer.contract.model.Data
import pl.beone.promena.transformer.contract.model.Parameters
import pl.beone.promena.transformer.internal.model.data.FileData
import pl.beone.promena.transformer.internal.model.data.MemoryData
import pl.beone.promena.transformer.internal.model.data.toFileData
import pl.beone.promena.transformer.internal.model.data.toMemoryData
import pl.beone.promena.transformer.internal.model.metadata.emptyMetadata
import pl.beone.promena.transformer.reportgenerator.jasperreports.util.getResourceAsBytes
import kotlin.reflect.KClass

private val simpleAllParametersAndFieldsBytes = getResourceAsBytes("/template/simple-all-parameters-and-fields.jrxml")

internal fun memoryTest(
    parameters: Parameters,
    assertParametersLine: String,
    assertRecordLines: List<String>,
    defaultParameters: JasperReportsReportGeneratorTransformerDefaultParameters = JasperReportsReportGeneratorTransformerDefaultParameters()
) {
    test(
        simpleAllParametersAndFieldsBytes.toMemoryData(),
        MemoryData::class,
        memoryCommunicationParameters(),
        parameters,
        assertParametersLine,
        assertRecordLines,
        defaultParameters
    )
}

internal fun fileTest(
    parameters: Parameters,
    assertParametersLine: String,
    assertRecordLines: List<String>,
    defaultParameters: JasperReportsReportGeneratorTransformerDefaultParameters = JasperReportsReportGeneratorTransformerDefaultParameters()
) {
    val directory = createTempDir()

    test(
        simpleAllParametersAndFieldsBytes.inputStream().toFileData(directory),
        FileData::class,
        fileCommunicationParameters(directory),
        parameters,
        assertParametersLine,
        assertRecordLines,
        defaultParameters
    )
}

private fun test(
    data: Data,
    dataClass: KClass<*>,
    communicationParameters: CommunicationParameters,
    parameters: Parameters,
    assertParametersLine: String,
    assertRecordLines: List<String>,
    defaultParameters: JasperReportsReportGeneratorTransformerDefaultParameters
) {
    JasperReportsReportGeneratorTransformer(defaultParameters, communicationParameters)
        .transform(singleDataDescriptor(data, TEXT_XML, emptyMetadata()), APPLICATION_PDF, parameters).let { transformedDataDescriptor ->
            withClue("Transformed data should contain only <1> element") { transformedDataDescriptor.descriptors shouldHaveSize 1 }

            transformedDataDescriptor.descriptors[0].let {
                withClue("Transformed data should be instance of <$dataClass>") { it.data shouldBe instanceOf(dataClass) }

                val lines = it.data.getLines()
                withClue("Data should contain (parameters + SEPARATOR + record) lines") { lines shouldHaveSize 2 + assertRecordLines.size }

                withClue("Parameters line should contain <$assertParametersLine>") { lines[0] shouldBe assertParametersLine }
                withClue("Records lines should contain <$assertRecordLines>") { lines.subList(2, lines.size) shouldBe assertRecordLines }

                it.metadata shouldBe emptyMetadata()
            }
        }
}

private fun Data.getLines(): List<String> =
    PdfTextExtractor.getTextFromPage(PdfReader(getBytes()), 1)
        .split("\n")
        .drop(1)