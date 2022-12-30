package com.example.testedittext.activities.report_list.server;

import com.google.gson.annotations.SerializedName;

public class ReportPojo {

    @SerializedName("reportname")
    private String reportname;
    @SerializedName("report")
    private String report;
    @SerializedName("userid")
    private String userid;
    @SerializedName("id")
    private long id;

    public ReportPojo(String reportname, String report, String userid, long id) {
        this.reportname = reportname;
        this.report = report;
        this.userid = userid;
        this.id = id;
    }

    public String getReportname() {
        return reportname;
    }

    public void setReportname(String reportname) {
        this.reportname = reportname;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
