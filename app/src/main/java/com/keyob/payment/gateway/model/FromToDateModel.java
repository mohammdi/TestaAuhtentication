package com.keyob.payment.gateway.model;

import java.io.Serializable;
import java.util.Date;

public class FromToDateModel implements Serializable {

    private Date fromDate;
    private Date toDate;


    public FromToDateModel(Date fromDate, Date toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

}
