package home.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "TMDL_API_TCNCL_MTDAT_DSET")
public class DsetEntity {

  @Id
  @Column(name = "API_MTDAT_DSET_ID", nullable = false)
  private String apiMtdatDsetId;

  @Column(name = "API_MTDAT_APPL_ID", nullable = false)
  private String apiMtdatApplId;

  @Column(name = "MSG_TYPE_NUM", nullable = false)
  private long msgTypeNum;

  @Column(name = "DAT_SRC_INGST_SYS_NM", nullable = false)
  private String datSrcIngstSysNm;

  @Column(name = "DAT_STOR_NM", nullable = false)
  private String datStorNm;

  @Column(name = "PHYS_SVR_NM", nullable = false)
  private String physSvrNm;

  @Column(name = "DAT_STOR_LOC_NM")
  private String datStorLocNm;

  @Column(name = "DAT_STOR_PLTFM_NM")
  private String datStorPltfmNm;

  @Column(name = "URL")
  private String url;

  @Column(name = "APPL_SYS_TYPE_CD")
  private String applSysTypeCd;

  @Column(name = "APPL_SYS_TYPE_NM")
  private String applSysTypeNm;

  @Column(name = "DAT_STOR_PLTFM_CD")
  private String datStorPltfmCd;

  @Column(name = "DAT_STOR_DESC")
  private String datStorDesc;

  @Column(name = "DAT_STOR_CRTE_DT")
  private LocalDateTime datStorCrteDt;

  @Column(name = "DAT_SET_NM", nullable = false)
  private String datSetNm;

  @Column(name = "DAT_SET_DESC")
  private String datSetDesc;

  @Column(name = "DAT_SET_TYPE_NM")
  private String datSetTypeNm;

  @Column(name = "DAT_SET_CRTE_DT")
  private LocalDateTime datSetCrteDt;

  @Column(name = "PRTN_COL_NM")
  private String prtnColNm;

  @Column(name = "USR_GRP_TYPE_TXT")
  private String usrGrpTypeTxt;

  @Column(name = "USR_GRP_TXT")
  private String usrGrpTxt;

  @Column(name = "REPLICA_CLUSTERS_TXT")
  private String replicaClustersTxt;

  @Column(name = "LD_IND")
  private Character ldInd;

  @Column(name = "LD_DTTM")
  private LocalDateTime ldDttm;

  @Column(name = "API_MTDAT_DSET_LFCYC_STAT_TXT")
  private String apiMtdatDsetLfcycStatTxt;

  @Column(name = "RUN_ID", nullable = false)
  private String runId;

  @Column(name = "CRTE_DTTM", insertable = false)
  private LocalDateTime crteDttm;

  @Column(name = "CRTE_BY_ID")
  private String crteById;

  @Column(name = "UPDT_DTTM")
  private LocalDateTime updtDttm;

  @Column(name = "UPDT_BY_ID")
  private String updtById;

  @Column(name = "ERR_FLG_IND")
  private Character errFlgInd;

  @Column(name = "ERR_TXT")
  private String errTxt;

  public String getApiMtdatDsetId() {
    return apiMtdatDsetId;
  }

  public DsetEntity setApiMtdatDsetId(String apiMtdatDsetId) {
    this.apiMtdatDsetId = apiMtdatDsetId;
    return this;
  }

  public String getApiMtdatApplId() {
    return apiMtdatApplId;
  }

  public DsetEntity setApiMtdatApplId(String apiMtdatApplId) {
    this.apiMtdatApplId = apiMtdatApplId;
    return this;
  }

  public long getMsgTypeNum() {
    return msgTypeNum;
  }

  public DsetEntity setMsgTypeNum(long msgTypeNum) {
    this.msgTypeNum = msgTypeNum;
    return this;
  }

  public String getDatSrcIngstSysNm() {
    return datSrcIngstSysNm;
  }

  public DsetEntity setDatSrcIngstSysNm(String datSrcIngstSysNm) {
    this.datSrcIngstSysNm = datSrcIngstSysNm;
    return this;
  }

  public String getDatStorNm() {
    return datStorNm;
  }

  public DsetEntity setDatStorNm(String datStorNm) {
    this.datStorNm = datStorNm;
    return this;
  }

  public String getPhysSvrNm() {
    return physSvrNm;
  }

  public DsetEntity setPhysSvrNm(String physSvrNm) {
    this.physSvrNm = physSvrNm;
    return this;
  }

  public String getDatStorLocNm() {
    return datStorLocNm;
  }

  public DsetEntity setDatStorLocNm(String datStorLocNm) {
    this.datStorLocNm = datStorLocNm;
    return this;
  }

  public String getDatStorPltfmNm() {
    return datStorPltfmNm;
  }

  public DsetEntity setDatStorPltfmNm(String datStorPltfmNm) {
    this.datStorPltfmNm = datStorPltfmNm;
    return this;
  }

  public String getUrl() {
    return url;
  }

  public DsetEntity setUrl(String url) {
    this.url = url;
    return this;
  }

  public String getApplSysTypeCd() {
    return applSysTypeCd;
  }

  public DsetEntity setApplSysTypeCd(String applSysTypeCd) {
    this.applSysTypeCd = applSysTypeCd;
    return this;
  }

  public String getApplSysTypeNm() {
    return applSysTypeNm;
  }

  public DsetEntity setApplSysTypeNm(String applSysTypeNm) {
    this.applSysTypeNm = applSysTypeNm;
    return this;
  }

  public String getDatStorPltfmCd() {
    return datStorPltfmCd;
  }

  public DsetEntity setDatStorPltfmCd(String datStorPltfmCd) {
    this.datStorPltfmCd = datStorPltfmCd;
    return this;
  }

  public String getDatStorDesc() {
    return datStorDesc;
  }

  public DsetEntity setDatStorDesc(String datStorDesc) {
    this.datStorDesc = datStorDesc;
    return this;
  }

  public LocalDateTime getDatStorCrteDt() {
    return datStorCrteDt;
  }

  public DsetEntity setDatStorCrteDt(LocalDateTime datStorCrteDt) {
    this.datStorCrteDt = datStorCrteDt;
    return this;
  }

  public String getDatSetNm() {
    return datSetNm;
  }

  public DsetEntity setDatSetNm(String datSetNm) {
    this.datSetNm = datSetNm;
    return this;
  }

  public String getDatSetDesc() {
    return datSetDesc;
  }

  public DsetEntity setDatSetDesc(String datSetDesc) {
    this.datSetDesc = datSetDesc;
    return this;
  }

  public String getDatSetTypeNm() {
    return datSetTypeNm;
  }

  public DsetEntity setDatSetTypeNm(String datSetTypeNm) {
    this.datSetTypeNm = datSetTypeNm;
    return this;
  }

  public LocalDateTime getDatSetCrteDt() {
    return datSetCrteDt;
  }

  public DsetEntity setDatSetCrteDt(LocalDateTime datSetCrteDt) {
    this.datSetCrteDt = datSetCrteDt;
    return this;
  }

  public String getPrtnColNm() {
    return prtnColNm;
  }

  public DsetEntity setPrtnColNm(String prtnColNm) {
    this.prtnColNm = prtnColNm;
    return this;
  }

  public String getUsrGrpTypeTxt() {
    return usrGrpTypeTxt;
  }

  public DsetEntity setUsrGrpTypeTxt(String usrGrpTypeTxt) {
    this.usrGrpTypeTxt = usrGrpTypeTxt;
    return this;
  }

  public String getUsrGrpTxt() {
    return usrGrpTxt;
  }

  public DsetEntity setUsrGrpTxt(String usrGrpTxt) {
    this.usrGrpTxt = usrGrpTxt;
    return this;
  }

  public String getReplicaClustersTxt() {
    return replicaClustersTxt;
  }

  public DsetEntity setReplicaClustersTxt(String replicaClustersTxt) {
    this.replicaClustersTxt = replicaClustersTxt;
    return this;
  }

  public Character getLdInd() {
    return ldInd;
  }

  public DsetEntity setLdInd(Character ldInd) {
    this.ldInd = ldInd;
    return this;
  }

  public LocalDateTime getLdDttm() {
    return ldDttm;
  }

  public DsetEntity setLdDttm(LocalDateTime ldDttm) {
    this.ldDttm = ldDttm;
    return this;
  }

  public String getApiMtdatDsetLfcycStatTxt() {
    return apiMtdatDsetLfcycStatTxt;
  }

  public DsetEntity setApiMtdatDsetLfcycStatTxt(String apiMtdatDsetLfcycStatTxt) {
    this.apiMtdatDsetLfcycStatTxt = apiMtdatDsetLfcycStatTxt;
    return this;
  }

  public String getRunId() {
    return runId;
  }

  public DsetEntity setRunId(String runId) {
    this.runId = runId;
    return this;
  }

  public LocalDateTime getCrteDttm() {
    return crteDttm;
  }

  public DsetEntity setCrteDttm(LocalDateTime crteDttm) {
    this.crteDttm = crteDttm;
    return this;
  }

  public String getCrteById() {
    return crteById;
  }

  public DsetEntity setCrteById(String crteById) {
    this.crteById = crteById;
    return this;
  }

  public LocalDateTime getUpdtDttm() {
    return updtDttm;
  }

  public DsetEntity setUpdtDttm(LocalDateTime updtDttm) {
    this.updtDttm = updtDttm;
    return this;
  }

  public String getUpdtById() {
    return updtById;
  }

  public DsetEntity setUpdtById(String updtById) {
    this.updtById = updtById;
    return this;
  }

  public Character getErrFlgInd() {
    return errFlgInd;
  }

  public DsetEntity setErrFlgInd(Character errFlgInd) {
    this.errFlgInd = errFlgInd;
    return this;
  }

  public String getErrTxt() {
    return errTxt;
  }

  public DsetEntity setErrTxt(String errTxt) {
    this.errTxt = errTxt;
    return this;
  }
}
