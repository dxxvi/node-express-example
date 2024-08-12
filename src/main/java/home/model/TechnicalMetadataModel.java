package home.model;

import java.time.Instant;
import java.util.List;

public record TechnicalMetadataModel(
    int messageType,
    String clientId,
    String notificationMailId,
    TechnicalMetadata technicalMetadata) {
  public record TechnicalMetadata(ApplicationCatalog applicationCatalog) {
    public record ApplicationCatalog(
        String dataSourceId,
        String applicationId,
        String physicalServerName,
        String sourceApplicationId,
        String sourceApplicationIdTypeText,
        String applicationSystemTypeCode,
        String applicationSystemTypeName,
        String dataStorePlatformCode,
        String dataStorePlatformName,
        String url,
        DataStore dataStore) {
      public record DataStore(
          String dataStoreName,
          String dataStoreDescription,
          String dataStoreLocationName,
          DataSet dataSet) {
        public record DataSet(
            String datasetName,
            String sourceDataStoreName,
            String datasetDescription,
            String datasetTypeName,
            String partitionColumnName,
            String userGroupTypeText,
            String userGroupText,
            String replicaClustersText,
            Instant datasetCreatedDate,
            List<PhysicalDataElement> physicalDataElements) {
          public record PhysicalDataElement(
              String physicalDataElementName,
              String physicalDataElementDataType,
              String physicalDataElementDescription,
              String physicalDataElementFormat,
              String securityLevelName,
              String externalSecurityClassificationText,
              String piiText,
              String businessElementName,
              Long physicalDataElementLength,
              Long precisionDigitNumber,
              Long scaleDigitNumber,
              boolean nullIndicator,
              boolean encryptIndicator,
              boolean primaryKeyIndicator,
              boolean privateIndicator,
              boolean piiIndicator,
              boolean externalSecurityClassificationIndicator) {}
        }
      }
    }
  }
}
