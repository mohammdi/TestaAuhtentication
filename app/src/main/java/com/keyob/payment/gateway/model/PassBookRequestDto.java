package com.keyob.payment.gateway.model;

import java.io.Serializable;
import java.util.Date;

public class PassBookRequestDto implements Serializable {

    private int Page;
    private Long WalletId;
    private int SearchType;
    private String StartDate;
    private String EndDate;

    public int getPage() {
        return Page;
    }

    public void setPage(int page) {
        Page = page;
    }

    public Long getWalletId() {
        return WalletId;
    }

    public void setWalletId(Long walletId) {
        WalletId = walletId;
    }

    public int getSearchType() {
        return SearchType;
    }

    public void setSearchType(int searchType) {
        SearchType = searchType;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }
}
