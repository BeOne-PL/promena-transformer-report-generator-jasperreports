package pl.beone.promena.transformer.report.jasperreports.applicationmodel.element;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class JasperReportsParametersBuilder {

    private final Map<String, Serializable> elements = new HashMap<>();

    public JasperReportsParametersBuilder add(String key, Serializable value) {
        elements.put(key, value);

        return this;
    }

    public JasperReportsParameters build() {
        return JasperReportsParameters.of(elements);
    }
}
