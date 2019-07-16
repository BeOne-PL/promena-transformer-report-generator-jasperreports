package pl.beone.promena.transformer.jasperreport.applicationmodel.parameters;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractJasperReportElement {

    private final Map<String, Serializable> items;

    AbstractJasperReportElement(Map<String, Serializable> items) {
        this.items = items;
    }

    public Map<String, Serializable> getAll() {
        return Collections.unmodifiableMap(items);
    }

    public abstract static class Builder<B extends Builder, T> {

        final Map<String, Serializable> items = new HashMap<>();

        public Builder<B, T> add(String key, String value) {
            items.put(key, value);

            return this;
        }

        public Builder<B, T> add(String key, Boolean value) {
            items.put(key, value);

            return this;
        }

        public Builder<B, T> add(String key, Double value) {
            items.put(key, value);

            return this;
        }

        public Builder<B, T> add(String key, Float value) {
            items.put(key, value);

            return this;
        }

        public Builder<B, T> add(String key, Integer value) {
            items.put(key, value);

            return this;
        }

        public Builder<B, T> add(String key, Long value) {
            items.put(key, value);

            return this;
        }

        public Builder<B, T> add(String key, Short value) {
            items.put(key, value);

            return this;
        }

        public Builder<B, T> add(String key, BigDecimal value) {
            items.put(key, value);

            return this;
        }

        public Builder<B, T> add(String key, Date value) {
            items.put(key, value);

            return this;
        }

        public Builder<B, T> add(String key, java.sql.Date value) {
            items.put(key, value);

            return this;
        }

        public Builder<B, T> add(String key, Time value) {
            items.put(key, value);

            return this;
        }

        public Builder<B, T> add(String key, Timestamp value) {
            items.put(key, value);

            return this;
        }

        public abstract T build();
    }

    @Override
    public String toString() {
        return "AbstractJasperReportElement{" +
                "items=" + items +
                '}';
    }
}
