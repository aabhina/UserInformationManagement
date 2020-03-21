package com.telna.userInfoSystem.model;

import java.util.List;

public class GenerateUserIdResponse {
    String userID;
    String errorList;
    String successMessage;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getErrorList() {
        return errorList;
    }

    public void setErrorList(String errorList) {
        this.errorList = errorList;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
}
