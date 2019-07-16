package pl.beone.promena.transformer.jasperreport.applicationmodel.parameters;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

public class JasperReportParameters extends AbstractJasperReportElement {

    private JasperReportParameters(Map<String, Serializable> items) {
        super(items);
    }

    public static JasperReportParameters empty() {
        return new JasperReportParameters(Collections.emptyMap());
    }

    public static class Builder extends AbstractJasperReportElement.Builder<Builder, JasperReportParameters> {

        public JasperReportParameters build() {
            return new JasperReportParameters(items);
        }

    }

    @Override
    public String toString() {
        return "JasperReportParameters{} " + super.toString();
    }
}