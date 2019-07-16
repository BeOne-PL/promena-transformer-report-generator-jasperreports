package pl.beone.promena.transformer.jasperreport.parameters

import pl.beone.lib.typeconverter.applicationmodel.exception.TypeConversionException
import pl.beone.promena.transformer.contract.model.Parameters
import pl.beone.promena.transformer.jasperreport.exception.JasperReportValidationException
import kotlin.NoSuchElementException

internal class ParametersValidator {

    fun validate(parameters: Parameters) {
        validateRoot(parameters)
        validateReportParameters(parameters)
        validateReportRecords(parameters)
    }

    private fun validateRoot(parameters: Parameters) {
        try {
            parameters.getRoot()
        } catch (e: NoSuchElementException) {
            throw JasperReportValidationException("Parameter <jasperReportTransformer> must be set", e)
        }
    }

    private fun validateReportParameters(parameters: Parameters) {
        try {
            parameters.getReportParameters()
        } catch (e: NoSuchElementException) {
            // deliberately omitted
            return
        } catch (e: TypeConversionException) {
            throw JasperReportValidationException("Parameter <jasperReportTransformer.parameters> must be of the <ReportParameters> type", e)
        } catch (e: Exception) {
            throw JasperReportValidationException("Unknown validation exception. Couldn't get parameter <jasperReportTransformer.parameters>", e)
        }
    }

    private fun validateReportRecords(parameters: Parameters) {
        val reportFields = try {
            parameters.getReportRecords()
        } catch (e: NoSuchElementException) {
            throw JasperReportValidationException("Parameter <jasperReportTransformer.records> must be set", e)
        } catch (e: TypeConversionException) {
            throw JasperReportValidationException("Parameter <jasperReportTransformer.records> must be of the <List<ReportRecord>> type ", e)
        } catch (e: Exception) {
            throw JasperReportValidationException("Unknown validation exception. Couldn't get parameter <jasperReportTransformer.records>", e)
        }

        if (reportFields.isEmpty()) {
            throw JasperReportValidationException("Parameter <jasperReportTransformer.records> must contain at least one record")
        }
    }
}