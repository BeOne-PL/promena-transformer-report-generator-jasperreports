package pl.beone.promena.transformer.report.jasperreports.applicationmodel.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class JasperReportsRecordBuilder {

    private final Map<String, Serializable> elements = new HashMap<>();

    public JasperReportsRecordBuilder add(String key, Serializable value) {
        elements.put(key, value);

        return this;
    }

    public JasperReportsRecord build() {
        return JasperReportsRecord.of(elements);
    }
}
