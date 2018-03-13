package com.codecooldev.functionalities.response;

import java.util.Date;

public class HttpResponse {
    private Integer statusCode;
    private String statusInfo;
    private Date date;
    private String server;
    private String contentType;
    private long contentLen;
    private String coding;
    private String body;


    public HttpResponse() {
        this.statusCode = 200;
        this.statusInfo = "OK";
        this.date = new Date();
        this.server = "Konrad i Kamil server";
        this.contentType = "text/html";
        this.coding = "charset=iso-8859-1";
        this.body = "<html><body><h1> hello </h1></body></html>";
        this.contentLen = body.length();
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getContentLen() {
        return contentLen;
    }

    public void setContentLen(long contentLen) {
        this.contentLen = contentLen;
    }

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
        this.contentLen = body.length();
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

}
