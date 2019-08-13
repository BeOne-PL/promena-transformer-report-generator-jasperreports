package pl.beone.promena.transformer.report.jasperreports.example;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;
import pl.beone.promena.alfresco.module.client.base.contract.AlfrescoPromenaTransformer;
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.element.JasperReportsParameters;
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.element.JasperReportsParametersBuilder;
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.element.JasperReportsRecord;
import pl.beone.promena.transformer.report.jasperreports.applicationmodel.element.JasperReportsRecordBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static pl.beone.promena.transformer.report.jasperreports.applicationmodel.ReportJasperReportsDsl.reportJasperReportsTransformation;
import static pl.beone.promena.transformer.report.jasperreports.applicationmodel.ReportJasperReportsParametersDsl.reportJasperReportsParameters;

public class ReportJasperReportsTransformerJavaScratch {

    public void generateReport(AlfrescoPromenaTransformer alfrescoPromenaTransformer) {
        alfrescoPromenaTransformer.transformAsync(
                reportJasperReportsTransformation(reportJasperReportsParameters(createRecords(), createParameters())),
                Collections.singletonList(new NodeRef(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE, "xxx")),
                null
        ).subscribe(
                System.out::println, // SUCCESS
                System.err::println, // ERROR
                () -> {} // COMPLETE
        );
    }

    private JasperReportsParameters createParameters() {
        return new JasperReportsParametersBuilder()
                .add("string", "value3")
                .build();
    }

    private List<JasperReportsRecord> createRecords() {
        return Arrays.asList(
                new JasperReportsRecordBuilder()
                        .add("string", "value")
                        .add("boolean", true)
                        .build(),
                new JasperReportsRecordBuilder()
                        .add("string", "value2")
                        .build()
        );
    }
}
