package com.telna.userInfoSystem.model;

import java.sql.Timestamp;

public class UsageDetails {
    String userID;
    //Enum usageType;
    Timestamp timeStamp;

    enum usageType
    {
        DATA, VOICE, SMS;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

}