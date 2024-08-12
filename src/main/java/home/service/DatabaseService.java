package home.service;

import home.Application;
import home.entity.ApplEntity;
import home.entity.DsetEntity;
import home.entity.PdeEntity;
import home.model.TechnicalMetadataModel;
import home.repository.ApplRepository;
import home.repository.DsetRepository;
import home.repository.PdeRepository;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DatabaseService {
  private static final Logger log = LoggerFactory.getLogger(DatabaseService.class);
  private static final String PENDING_ACKNOWLEDGEMENT = "Pending Acknowledgement";
  private static final String DXC = "DXC-";

  private final ApplRepository applRepo;
  private final DsetRepository dsetRepo;
  private final PdeRepository pdeRepo;

  public DatabaseService(ApplRepository applRepo, DsetRepository dsetRepo, PdeRepository pdeRepo) {
    this.applRepo = applRepo;
    this.dsetRepo = dsetRepo;
    this.pdeRepo = pdeRepo;
  }

  // this method might throw exceptions
  @Transactional
  public void writeToDb(TechnicalMetadataModel model) {
    String applicationId = model.technicalMetadata().applicationCatalog().applicationId();
    String runId =
        ZonedDateTime.now().format(DateTimeFormatter.ofPattern("'TMDL-'yyyy-MM-dd'T'HH:mm:ssZ"));
    String clientId = model.clientId();
    String crteUpdtById = clientId.startsWith(DXC) ? "dssdxcp" : clientId;
    String apiMtdatApplId = Application.generateV7Uuid();
    if (!applRepo.existsByApplId(applicationId)) { // insert into APPL table
      var applEntity =
          new ApplEntity()
              .setApiMtdatApplId(apiMtdatApplId)
              .setApplId(applicationId)
              .setApiClntId(model.clientId())
              .setObjStatTxt(PENDING_ACKNOWLEDGEMENT)
              .setLdInd('N')
              .setRunId(runId)
              .setCrteById(crteUpdtById)
              .setUpdtById(crteUpdtById);
      applRepo.save(applEntity);
    } else {
      List<String> apiMtdatApplIds = applRepo.findDistinctApiMtdatApplIds(applicationId);
      if (apiMtdatApplIds.size() != 1) { // this shouldn't happen
        throw new IllegalStateException(
            "There are "
                + apiMtdatApplIds.size()
                + " API_MTDAT_APPL_ID for APPL_ID = "
                + applicationId);
      }
      apiMtdatApplId = apiMtdatApplIds.get(0);
    }

    var applicationCatalog = model.technicalMetadata().applicationCatalog();
    var dataStore = applicationCatalog.dataStore();
    var dataSet = dataStore.dataSet();

    String apiMtdatDsetId = Application.generateV7Uuid();
    var dsetEntity =
        new DsetEntity()
            .setApiMtdatDsetId(apiMtdatDsetId)
            .setApiMtdatApplId(apiMtdatApplId)
            .setMsgTypeNum(model.messageType())
            .setDatStorNm(dataStore.dataStoreName())
            .setPhysSvrNm(applicationCatalog.physicalServerName())
            .setDatStorLocNm(dataStore.dataStoreName())
            .setDatSetDesc(dataSet.datasetDescription())
            .setDatSetNm(dataSet.datasetName())
            .setDatStorPltfmNm(applicationCatalog.dataStorePlatformName())
            .setUrl(applicationCatalog.url())
            .setApplSysTypeCd(applicationCatalog.applicationSystemTypeCode()) // 10 chars
            .setApplSysTypeNm(applicationCatalog.applicationSystemTypeName())
            .setDatStorPltfmCd(applicationCatalog.dataStorePlatformCode())
            .setDatStorDesc(dataStore.dataStoreDescription())
            .setDatSetTypeNm(dataSet.datasetTypeName())
            .setDatSetCrteDt(
                LocalDateTime.ofInstant(dataSet.datasetCreatedDate(), ZoneId.systemDefault()))
            .setPrtnColNm(dataSet.partitionColumnName())
            .setUsrGrpTypeTxt(dataSet.userGroupTypeText())
            .setUsrGrpTxt(dataSet.userGroupText())
            .setReplicaClustersTxt(dataSet.replicaClustersText())
            .setLdInd('N')
            .setRunId(runId)
            .setCrteById(crteUpdtById)
            .setDatSrcIngstSysNm("...") // TODO need this
            .setUpdtById(crteUpdtById);
    dsetRepo.save(dsetEntity);

    // TODO need to find the value of apiMtdatDsetId from the select statement
    for (var physicalDataElement : dataSet.physicalDataElements()) {
      String apiMtdatPdeId = Application.generateV7Uuid();
      var pdeEntity =
          new PdeEntity()
              .setApiMtdatPdeId(apiMtdatPdeId)
              .setApiMtdatDsetId(apiMtdatDsetId)
              .setPhysDatElemNm(physicalDataElement.physicalDataElementName())
              .setPhysDatElemDatTypeNm(physicalDataElement.physicalDataElementDataType())
              .setPhysDatElemDesc(physicalDataElement.physicalDataElementDescription())
              .setPhysDatElemFmt(physicalDataElement.physicalDataElementFormat())
              .setPhysDatElemLen(physicalDataElement.physicalDataElementLength())
              .setPrcsnDgtNum(physicalDataElement.precisionDigitNumber())
              .setScaleDgtNum(physicalDataElement.scaleDigitNumber())
              .setNullInd(physicalDataElement.nullIndicator() ? 'Y' : 'N')
              .setEncryptInd(physicalDataElement.encryptIndicator() ? 'Y' : 'N')
              .setPrimKyInd(physicalDataElement.primaryKeyIndicator() ? 'Y' : 'N')
              .setPrivInd(physicalDataElement.privateIndicator() ? 'Y' : 'N')
              .setPiiInd(physicalDataElement.piiIndicator() ? 'Y' : 'N')
              .setPiiTxt(physicalDataElement.piiText())
              .setExtSecClassInd(
                  physicalDataElement.externalSecurityClassificationIndicator() ? 'Y' : 'N')
              .setExtSecClassTxt(physicalDataElement.externalSecurityClassificationText())
              .setSecLvlNm(physicalDataElement.securityLevelName())
              .setBusElemNm(physicalDataElement.businessElementName())
              .setLdInd('N')
              .setRunId(runId)
              .setCrteById(crteUpdtById)
              .setUpdtById(crteUpdtById);
      pdeRepo.save(pdeEntity);
    }
  }
}
