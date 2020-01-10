# Promena Transformer - `report generator - JasperReports`
This transformer provides functionality to generate a report based on a template using JasperReports 6.9.0.

Visit [Promena#Transformer](https://gitlab.office.beone.pl/promena/promena#transformer) to understand the repository structure.

## Transformation [`JasperReportsReportGeneratorDsl`](./application-model/src/main/kotlin/pl/beone/promena/transformer/reportgenerator/jasperreports/applicationmodel/JasperReportsReportGeneratorDsl.kt), [`JasperReportsReportGeneratorParametersDsl`](./application-model/src/main/kotlin/pl/beone/promena/transformer/reportgenerator/jasperreports/applicationmodel/JasperReportsReportGeneratorParametersDsl.kt)
The [`DataDescriptor`](https://gitlab.office.beone.pl/promena/promena/blob/master/base/promena-transformer/contract/src/main/kotlin/pl/beone/promena/transformer/contract/data/DataDescriptor.kt) has to contain at least one descriptor with a JasperReports template. If more than one descriptor is passed, the transformation will be performed on each of them separately.

## Support [`JasperReportsReportGeneratorSupport`](./application-model/src/main/kotlin/pl/beone/promena/transformer/reportgenerator/jasperreports/applicationmodel/JasperReportsReportGeneratorSupport.kt)
### Media type [`JasperReportsReportGeneratorSupport.MediaTypeSupport`](./application-model/src/main/kotlin/pl/beone/promena/transformer/reportgenerator/jasperreports/applicationmodel/JasperReportsReportGeneratorSupport.kt)
* `text/xml` :arrow_right: `application/pdf; UTF-8`
* `application/octet-stream` :arrow_right: `application/pdf; UTF-8`

### Parameters [`JasperReportsReportGeneratorSupport.ParametersSupport`](./application-model/src/main/kotlin/pl/beone/promena/transformer/reportgenerator/jasperreports/applicationmodel/JasperReportsReportGeneratorSupport.kt)
* `records`, `List<Map<String, Serializable>>`, mandatory - elements that will be transformed into DataSource and put in the report
* `parameters`, `Map<String, Serializable>`, optional - parameters of the report

## Dependency
```xml
<dependency>
    <groupId>pl.beone.promena.transformer</groupId>
    <artifactId>report-generator-jasperreports-configuration</artifactId>
    <version>1.0.0</version>
</dependency>
```

### `promena-docker-maven-plugin`
```xml
<dependency>
    <groupId>pl.beone.promena.transformer</groupId>
    <artifactId>report-generator-jasperreports</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Properties
```properties
transformer.pl.beone.promena.transformer.reportgenerator.jasperreports.JasperReportsReportGeneratorTransformer.priority=1
transformer.pl.beone.promena.transformer.reportgenerator.jasperreports.JasperReportsReportGeneratorTransformer.actors=1

transformer.pl.beone.promena.transformer.reportgenerator.jasperreports.default.parameters.timeout=
```