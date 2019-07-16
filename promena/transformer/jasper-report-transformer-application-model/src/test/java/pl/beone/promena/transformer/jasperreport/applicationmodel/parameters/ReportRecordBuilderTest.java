package pl.beone.promena.transformer.jasperreport.applicationmodel.parameters;

import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;

public class ReportRecordBuilderTest {

    @Test
    public void builder() {
        assertThat(new JasperReportRecord.Builder()
                .add("string", "value")
                .add("boolean", true)
                .add("double", 1.1)
                .add("float", 2.2f)
                .add("integer", 3)
                .add("long", 4L)
                .add("short", (short) 5)
                .add("bigDecimal", new BigDecimal("6"))
                .add("date", new Date(2213213))
                .add("sqlDate", new java.sql.Date(1213213))
                .add("sqlTime", new Time(213123321))
                .add("sqlTimestamp", new Timestamp(123123213))
                .build().getAll())
                .hasSize(12)
                .isEqualTo(Map.ofEntries(
                        entry("string", "value"),
                        entry("boolean", true),
                        entry("double", 1.1),
                        entry("float", 2.2f),
                        entry("integer", 3),
                        entry("long", 4L),
                        entry("short", (short) 5),
                        entry("bigDecimal", new BigDecimal("6")),
                        entry("date", new Date(2213213)),
                        entry("sqlDate", new java.sql.Date(1213213)),
                        entry("sqlTime", new Time(213123321)),
                        entry("sqlTimestamp", new Timestamp(123123213))
                ));
    }

    @Test
    public void builder_empty() {
        assertThat(JasperReportRecord.empty().getAll())
                .hasSize(0);
    }

}