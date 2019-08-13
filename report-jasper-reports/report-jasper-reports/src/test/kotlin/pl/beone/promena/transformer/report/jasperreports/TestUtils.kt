package pl.beone.promena.transformer.report.jasperreports

import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants
import pl.beone.promena.transformer.contract.data.DataDescriptor
import pl.beone.promena.transformer.contract.data.singleDataDescriptor
import pl.beone.promena.transformer.contract.model.Data
import pl.beone.promena.transformer.internal.model.metadata.emptyMetadata
import java.io.IOException

fun String.getResourceBytes(): ByteArray =
    object {}::class.java.getResourceAsStream(this)
        ?.readAllBytes() ?: throw IOException("Couldn't find <$this> resource file")

fun Data.toJasperDataDescriptor(): DataDescriptor.Single =
    singleDataDescriptor(this, MediaTypeConstants.TEXT_XML, emptyMetadata())

fun Data.getText(): List<String> =
    PdfTextExtractor.getTextFromPage(PdfReader(getBytes()), 1)
        .split("\n")
        .drop(1)
