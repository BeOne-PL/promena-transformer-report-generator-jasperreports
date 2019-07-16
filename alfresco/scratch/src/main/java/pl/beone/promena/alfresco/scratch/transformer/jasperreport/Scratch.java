package pl.beone.promena.alfresco.scratch.transformer.jasperreport;

import org.alfresco.service.cmr.repository.NodeRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import pl.beone.promena.alfresco.module.transformer.jasperreport.contract.JasperReportAlfrescoPromenaService;
import pl.beone.promena.transformer.jasperreport.applicationmodel.parameters.JasperReportParameters;
import pl.beone.promena.transformer.jasperreport.applicationmodel.parameters.JasperReportRecord;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Scratch {

    private static Logger logger = LoggerFactory.getLogger(Scratch.class);

    @Autowired
    private JasperReportAlfrescoPromenaService jasperReportAlfrescoPromenaService;

    // classpath:simple-all-parameters-and-fields.jrxml
    public void execute() {
        jasperReportAlfrescoPromenaService.generatePdfAsync(new NodeRef("workspace://SpacesStore/518bb601-742c-425e-9ce2-c8cba3749d50"),
                createParameters(),
                createRecords())
                .subscribe(
                        it -> logger.error("Transformed to nodes: {}", it),
                        it -> logger.error("Couldn't transform", it)
                );
    }

    private JasperReportParameters createParameters() {
        return new JasperReportParameters.Builder()
                .add("string", "parameterString")
                .add("boolean", true)
                .build();
    }

    private List<JasperReportRecord> createRecords() {
        return Arrays.asList(
                new JasperReportRecord.Builder()
                        .add("string", "recordString")
                        .add("boolean", false)
                        .build(),
                JasperReportRecord.empty(),
                new JasperReportRecord.Builder()
                        .add("string", "record2String")
                        .add("date", new Date())
                        .build()
        );
    }
}
