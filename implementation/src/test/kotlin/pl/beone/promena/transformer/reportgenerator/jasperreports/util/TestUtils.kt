package pl.beone.promena.transformer.reportgenerator.jasperreports.util

import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.matchers.withClue
import io.kotlintest.shouldBe
import io.mockk.mockk
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants.APPLICATION_PDF
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants.TEXT_XML
import pl.beone.promena.transformer.contract.communication.CommunicationParameters
import pl.beone.promena.transformer.contract.communication.CommunicationWritableDataCreator
import pl.beone.promena.transformer.contract.data.singleDataDescriptor
import pl.beone.promena.transformer.contract.model.Parameters
import pl.beone.promena.transformer.contract.model.data.Data
import pl.beone.promena.transformer.contract.model.data.WritableData
import pl.beone.promena.transformer.internal.model.data.memory.emptyMemoryWritableData
import pl.beone.promena.transformer.internal.model.data.memory.toMemoryData
import pl.beone.promena.transformer.internal.model.metadata.emptyMetadata
import pl.beone.promena.transformer.reportgenerator.jasperreports.JasperReportsReportGeneratorTransformer
import pl.beone.promena.transformer.reportgenerator.jasperreports.JasperReportsReportGeneratorTransformerDefaultParameters

private object MemoryCommunicationWritableDataCreator : CommunicationWritableDataCreator {
    override fun create(communicationParameters: CommunicationParameters): WritableData = emptyMemoryWritableData()
}

internal fun createJasperReportsReportGeneratorTransformer(
    defaultParameters: JasperReportsReportGeneratorTransformerDefaultParameters = JasperReportsReportGeneratorTransformerDefaultParameters(),
    communicationParameters: CommunicationParameters = mockk(),
    communicationWritableDataCreator: CommunicationWritableDataCreator = MemoryCommunicationWritableDataCreator
): JasperReportsReportGeneratorTransformer =
    JasperReportsReportGeneratorTransformer(
        defaultParameters,
        communicationParameters,
        communicationWritableDataCreator
    )

private val data = getResourceAsBytes("/template/simple-all-parameters-and-fields.jrxml").toMemoryData()

internal fun test(
    parameters: Parameters,
    assertParametersLine: String,
    assertRecordLines: List<String>,
    defaultParameters: JasperReportsReportGeneratorTransformerDefaultParameters = JasperReportsReportGeneratorTransformerDefaultParameters()
) {
    with(
        createJasperReportsReportGeneratorTransformer(defaultParameters)
            .transform(singleDataDescriptor(data, TEXT_XML, emptyMetadata()), APPLICATION_PDF, parameters)
    ) {
        withClue("Transformed data should contain only <1> element") { descriptors shouldHaveSize 1 }

        with(descriptors[0]) {
            val lines = data.getLines()
            withClue("Data should contain (parameters + SEPARATOR + record) lines") { lines shouldHaveSize 2 + assertRecordLines.size }

            withClue("Parameters line should contain <$assertParametersLine>") { lines[0] shouldBe assertParametersLine }
            withClue("Records lines should contain <$assertRecordLines>") { lines.subList(2, lines.size) shouldBe assertRecordLines }

            metadata shouldBe emptyMetadata()
        }
    }
}

private fun Data.getLines(): List<String> =
    PdfTextExtractor.getTextFromPage(PdfReader(getBytes()), 1)
        .split("\n")
        .drop(1)