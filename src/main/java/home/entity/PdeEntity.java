package home.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "TMDL_API_TCNCL_MTDAT_PDE")
public class PdeEntity {
  @Id
  @Column(name = "API_MTDAT_PDE_ID", nullable = false)
  private String apiMtdatPdeId;

  @Column(name = "API_MTDAT_DSET_ID", nullable = false)
  private String apiMtdatDsetId;

  @Column(name = "MSG_TYPE_NUM")
  private Long msgTypeNum;

  @Column(name = "PHYS_DAT_ELEM_NM", nullable = false)
  private String physDatElemNm;

  @Column(name = "PHYS_DAT_ELEM_DAT_TYPE_NM")
  private String physDatElemDatTypeNm;

  @Column(name = "PHYS_DAT_ELEM_DESC")
  private String physDatElemDesc;

  @Column(name = "PHYS_DAT_ELEM_FMT")
  private String physDatElemFmt;

  @Column(name = "PHYS_DAT_ELEM_LEN")
  private Long physDatElemLen;

  @Column(name = "PRCSN_DGT_NUM")
  private Long prcsnDgtNum;

  @Column(name = "SCALE_DGT_NUM")
  private Long scaleDgtNum;

  @Column(name = "NULL_IND")
  private Character nullInd;

  @Column(name = "ENCRYPT_IND")
  private Character encryptInd;

  @Column(name = "PRIM_KY_IND")
  private Character primKyInd;

  @Column(name = "PRIV_IND")
  private Character privInd;

  @Column(name = "PII_IND")
  private Character piiInd;

  @Column(name = "PII_TXT")
  private String piiTxt;

  @Column(name = "EXT_SEC_CLASS_IND")
  private Character extSecClassInd;

  @Column(name = "EXT_SEC_CLASS_TXT")
  private String extSecClassTxt;

  @Column(name = "SEC_LVL_NM")
  private String secLvlNm;

  @Column(name = "BUS_ELEM_NM")
  private String busElemNm;

  @Column(name = "ST_MTDAT_IND")
  private Character stMtdatInd;

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

  public String getApiMtdatPdeId() {
    return apiMtdatPdeId;
  }

  public PdeEntity setApiMtdatPdeId(String apiMtdatPdeId) {
    this.apiMtdatPdeId = apiMtdatPdeId;
    return this;
  }

  public String getApiMtdatDsetId() {
    return apiMtdatDsetId;
  }

  public PdeEntity setApiMtdatDsetId(String apiMtdatDsetId) {
    this.apiMtdatDsetId = apiMtdatDsetId;
    return this;
  }

  public Long getMsgTypeNum() {
    return msgTypeNum;
  }

  public PdeEntity setMsgTypeNum(Long msgTypeNum) {
    this.msgTypeNum = msgTypeNum;
    return this;
  }

  public String getPhysDatElemNm() {
    return physDatElemNm;
  }

  public PdeEntity setPhysDatElemNm(String physDatElemNm) {
    this.physDatElemNm = physDatElemNm;
    return this;
  }

  public String getPhysDatElemDatTypeNm() {
    return physDatElemDatTypeNm;
  }

  public PdeEntity setPhysDatElemDatTypeNm(String physDatElemDatTypeNm) {
    this.physDatElemDatTypeNm = physDatElemDatTypeNm;
    return this;
  }

  public String getPhysDatElemDesc() {
    return physDatElemDesc;
  }

  public PdeEntity setPhysDatElemDesc(String physDatElemDesc) {
    this.physDatElemDesc = physDatElemDesc;
    return this;
  }

  public String getPhysDatElemFmt() {
    return physDatElemFmt;
  }

  public PdeEntity setPhysDatElemFmt(String physDatElemFmt) {
    this.physDatElemFmt = physDatElemFmt;
    return this;
  }

  public Long getPhysDatElemLen() {
    return physDatElemLen;
  }

  public PdeEntity setPhysDatElemLen(Long physDatElemLen) {
    this.physDatElemLen = physDatElemLen;
    return this;
  }

  public Long getPrcsnDgtNum() {
    return prcsnDgtNum;
  }

  public PdeEntity setPrcsnDgtNum(Long prcsnDgtNum) {
    this.prcsnDgtNum = prcsnDgtNum;
    return this;
  }

  public Long getScaleDgtNum() {
    return scaleDgtNum;
  }

  public PdeEntity setScaleDgtNum(Long scaleDgtNum) {
    this.scaleDgtNum = scaleDgtNum;
    return this;
  }

  public Character getNullInd() {
    return nullInd;
  }

  public PdeEntity setNullInd(Character nullInd) {
    this.nullInd = nullInd;
    return this;
  }

  public Character getEncryptInd() {
    return encryptInd;
  }

  public PdeEntity setEncryptInd(Character encryptInd) {
    this.encryptInd = encryptInd;
    return this;
  }

  public Character getPrimKyInd() {
    return primKyInd;
  }

  public PdeEntity setPrimKyInd(Character primKyInd) {
    this.primKyInd = primKyInd;
    return this;
  }

  public Character getPrivInd() {
    return privInd;
  }

  public PdeEntity setPrivInd(Character privInd) {
    this.privInd = privInd;
    return this;
  }

  public Character getPiiInd() {
    return piiInd;
  }

  public PdeEntity setPiiInd(Character piiInd) {
    this.piiInd = piiInd;
    return this;
  }

  public String getPiiTxt() {
    return piiTxt;
  }

  public PdeEntity setPiiTxt(String piiTxt) {
    this.piiTxt = piiTxt;
    return this;
  }

  public Character getExtSecClassInd() {
    return extSecClassInd;
  }

  public PdeEntity setExtSecClassInd(Character extSecClassInd) {
    this.extSecClassInd = extSecClassInd;
    return this;
  }

  public String getExtSecClassTxt() {
    return extSecClassTxt;
  }

  public PdeEntity setExtSecClassTxt(String extSecClassTxt) {
    this.extSecClassTxt = extSecClassTxt;
    return this;
  }

  public String getSecLvlNm() {
    return secLvlNm;
  }

  public PdeEntity setSecLvlNm(String secLvlNm) {
    this.secLvlNm = secLvlNm;
    return this;
  }

  public String getBusElemNm() {
    return busElemNm;
  }

  public PdeEntity setBusElemNm(String busElemNm) {
    this.busElemNm = busElemNm;
    return this;
  }

  public Character getStMtdatInd() {
    return stMtdatInd;
  }

  public PdeEntity setStMtdatInd(Character stMtdatInd) {
    this.stMtdatInd = stMtdatInd;
    return this;
  }

  public Character getLdInd() {
    return ldInd;
  }

  public PdeEntity setLdInd(Character ldInd) {
    this.ldInd = ldInd;
    return this;
  }

  public LocalDateTime getLdDttm() {
    return ldDttm;
  }

  public PdeEntity setLdDttm(LocalDateTime ldDttm) {
    this.ldDttm = ldDttm;
    return this;
  }

  public String getApiMtdatDsetLfcycStatTxt() {
    return apiMtdatDsetLfcycStatTxt;
  }

  public PdeEntity setApiMtdatDsetLfcycStatTxt(String apiMtdatDsetLfcycStatTxt) {
    this.apiMtdatDsetLfcycStatTxt = apiMtdatDsetLfcycStatTxt;
    return this;
  }

  public String getRunId() {
    return runId;
  }

  public PdeEntity setRunId(String runId) {
    this.runId = runId;
    return this;
  }

  public LocalDateTime getCrteDttm() {
    return crteDttm;
  }

  public PdeEntity setCrteDttm(LocalDateTime crteDttm) {
    this.crteDttm = crteDttm;
    return this;
  }

  public String getCrteById() {
    return crteById;
  }

  public PdeEntity setCrteById(String crteById) {
    this.crteById = crteById;
    return this;
  }

  public LocalDateTime getUpdtDttm() {
    return updtDttm;
  }

  public PdeEntity setUpdtDttm(LocalDateTime updtDttm) {
    this.updtDttm = updtDttm;
    return this;
  }

  public String getUpdtById() {
    return updtById;
  }

  public PdeEntity setUpdtById(String updtById) {
    this.updtById = updtById;
    return this;
  }

  public Character getErrFlgInd() {
    return errFlgInd;
  }

  public PdeEntity setErrFlgInd(Character errFlgInd) {
    this.errFlgInd = errFlgInd;
    return this;
  }

  public String getErrTxt() {
    return errTxt;
  }

  public PdeEntity setErrTxt(String errTxt) {
    this.errTxt = errTxt;
    return this;
  }
}
