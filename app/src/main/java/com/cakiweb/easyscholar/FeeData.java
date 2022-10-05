package com.cakiweb.easyscholar;

public class FeeData {
    String id;
    String sessionid;
    String monthyear;
    String feeamount;
    String paidstatus;
    String paiddate;
    String paidamount;
    String finecollected;

    public FeeData(String id, String sessionid, String monthyear, String feeamount, String paidstatus, String paiddate, String paidamount, String finecollected) {
        this.id = id;
        this.sessionid = sessionid;
        this.monthyear = monthyear;
        this.feeamount = feeamount;
        this.paidstatus = paidstatus;
        this.paiddate = paiddate;
        this.paidamount = paidamount;
        this.finecollected = finecollected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFinecollected() {
        return finecollected;
    }

    public String getMonthyear() {
        return monthyear;
    }

    public String getPaidamount() {
        return paidamount;
    }

    public String getPaiddate() {
        return paiddate;
    }

    public String getPaidstatus() {
        return paidstatus;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setFinecollected(String finecollected) {
        this.finecollected = finecollected;
    }

    public void setMonthyear(String monthyear) {
        this.monthyear = monthyear;
    }

    public void setPaidamount(String paidamount) {
        this.paidamount = paidamount;
    }

    public void setPaiddate(String paiddate) {
        this.paiddate = paiddate;
    }

    public void setPaidstatus(String paidstatus) {
        this.paidstatus = paidstatus;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }
    public String getFeeamount() {
        return feeamount;
    }

    public void setFeeamount(String feeamount) {
        this.feeamount = feeamount;
    }
}
