package home.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "TMDL_API_MTDAT_APPL")
public class ApplEntity {
  @Id
  @Column(name = "API_MTDAT_APPL_ID", nullable = false)
  private String apiMtdatApplId;

  @Column(name = "APPL_ID", nullable = false)
  private String applId;

  @Column(name = "API_CLNT_ID", nullable = false)
  private String apiClntId;

  @Column(name = "OBJ_STAT_TXT")
  private String objStatTxt;

  @Column(name = "OBJ_STAT_DT")
  private LocalDateTime objStatDt;

  @Column(name = "OBJ_STAT_UPDT_BY_ID")
  private String objStatUpdtById;

  @Column(name = "OBJ_STAT_UPDT_BY_ID_TYPE_NM")
  private String objStatUpdtByIdTypeNm;

  @Column(name = "OBJ_STAT_UPDT_BY_NM")
  private String objStatUpdtByNm;

  @Column(name = "OBJ_STAT_UPDT_EMAIL_ADDR_TXT")
  private String objStatUpdtEmailAddrTxt;

  @Column(name = "OBJ_STAT_CMNT_TXT")
  private String objStatCmntTxt;

  @Column(name = "LD_IND")
  private Character ldInd;

  @Column(name = "LD_DTTM")
  private LocalDateTime ldDttm;

  @Column(name = "API_MTDAT_APPL_LFCYC_STAT_TXT")
  private String apiMtdatApplLfcycStatTxt;

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

  public String getApiMtdatApplId() {
    return apiMtdatApplId;
  }

  public ApplEntity setApiMtdatApplId(String apiMtdatApplId) {
    this.apiMtdatApplId = apiMtdatApplId;
    return this;
  }

  public String getApplId() {
    return applId;
  }

  public ApplEntity setApplId(String applId) {
    this.applId = applId;
    return this;
  }

  public String getApiClntId() {
    return apiClntId;
  }

  public ApplEntity setApiClntId(String apiClntId) {
    this.apiClntId = apiClntId;
    return this;
  }

  public String getObjStatTxt() {
    return objStatTxt;
  }

  public ApplEntity setObjStatTxt(String objStatTxt) {
    this.objStatTxt = objStatTxt;
    return this;
  }

  public LocalDateTime getObjStatDt() {
    return objStatDt;
  }

  public ApplEntity setObjStatDt(LocalDateTime objStatDt) {
    this.objStatDt = objStatDt;
    return this;
  }

  public String getObjStatUpdtById() {
    return objStatUpdtById;
  }

  public ApplEntity setObjStatUpdtById(String objStatUpdtById) {
    this.objStatUpdtById = objStatUpdtById;
    return this;
  }

  public String getObjStatUpdtByIdTypeNm() {
    return objStatUpdtByIdTypeNm;
  }

  public ApplEntity setObjStatUpdtByIdTypeNm(String objStatUpdtByIdTypeNm) {
    this.objStatUpdtByIdTypeNm = objStatUpdtByIdTypeNm;
    return this;
  }

  public String getObjStatUpdtByNm() {
    return objStatUpdtByNm;
  }

  public ApplEntity setObjStatUpdtByNm(String objStatUpdtByNm) {
    this.objStatUpdtByNm = objStatUpdtByNm;
    return this;
  }

  public String getObjStatUpdtEmailAddrTxt() {
    return objStatUpdtEmailAddrTxt;
  }

  public ApplEntity setObjStatUpdtEmailAddrTxt(String objStatUpdtEmailAddrTxt) {
    this.objStatUpdtEmailAddrTxt = objStatUpdtEmailAddrTxt;
    return this;
  }

  public String getObjStatCmntTxt() {
    return objStatCmntTxt;
  }

  public ApplEntity setObjStatCmntTxt(String objStatCmntTxt) {
    this.objStatCmntTxt = objStatCmntTxt;
    return this;
  }

  public Character getLdInd() {
    return ldInd;
  }

  public ApplEntity setLdInd(Character ldInd) {
    this.ldInd = ldInd;
    return this;
  }

  public LocalDateTime getLdDttm() {
    return ldDttm;
  }

  public ApplEntity setLdDttm(LocalDateTime ldDttm) {
    this.ldDttm = ldDttm;
    return this;
  }

  public String getApiMtdatApplLfcycStatTxt() {
    return apiMtdatApplLfcycStatTxt;
  }

  public ApplEntity setApiMtdatApplLfcycStatTxt(String apiMtdatApplLfcycStatTxt) {
    this.apiMtdatApplLfcycStatTxt = apiMtdatApplLfcycStatTxt;
    return this;
  }

  public String getRunId() {
    return runId;
  }

  public ApplEntity setRunId(String runId) {
    this.runId = runId;
    return this;
  }

  public LocalDateTime getCrteDttm() {
    return crteDttm;
  }

  public ApplEntity setCrteDttm(LocalDateTime crteDttm) {
    this.crteDttm = crteDttm;
    return this;
  }

  public String getCrteById() {
    return crteById;
  }

  public ApplEntity setCrteById(String crteById) {
    this.crteById = crteById;
    return this;
  }

  public LocalDateTime getUpdtDttm() {
    return updtDttm;
  }

  public ApplEntity setUpdtDttm(LocalDateTime updtDttm) {
    this.updtDttm = updtDttm;
    return this;
  }

  public String getUpdtById() {
    return updtById;
  }

  public ApplEntity setUpdtById(String updtById) {
    this.updtById = updtById;
    return this;
  }

  public Character getErrFlgInd() {
    return errFlgInd;
  }

  public ApplEntity setErrFlgInd(Character errFlgInd) {
    this.errFlgInd = errFlgInd;
    return this;
  }

  public String getErrTxt() {
    return errTxt;
  }

  public ApplEntity setErrTxt(String errTxt) {
    this.errTxt = errTxt;
    return this;
  }
}
