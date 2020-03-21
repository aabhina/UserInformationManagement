package com.telna.userInfoSystem.model;

import java.util.List;

public class UsageHistoryResponse {
    String userID;
    List<List<String>> usageDetailsList;
    String errorString;

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<List<String>> getUsageDetailsList() {
        return usageDetailsList;
    }

    public void setUsageDetailsList(List<List<String>> usageDetailsList) {
        this.usageDetailsList = usageDetailsList;
    }
}
