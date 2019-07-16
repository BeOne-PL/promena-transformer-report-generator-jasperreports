package pl.beone.promena.transformer.jasperreport.applicationmodel.parameters;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

public class JasperReportRecord extends AbstractJasperReportElement {

    private JasperReportRecord(Map<String, Serializable> items) {
        super(items);
    }

    public static JasperReportRecord empty() {
        return new JasperReportRecord(Collections.emptyMap());
    }

    public static class Builder extends AbstractJasperReportElement.Builder<Builder, JasperReportRecord> {

        public JasperReportRecord build() {
            return new JasperReportRecord(items);
        }

    }

    @Override
    public String toString() {
        return "JasperReportRecord{} " + super.toString();
    }
}
