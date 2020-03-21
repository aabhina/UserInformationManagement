package com.telna.userInfoSystem.service;

import com.telna.userInfoSystem.model.UsageDetails;
import com.telna.userInfoSystem.model.UsageHistory;
import com.telna.userInfoSystem.model.User;
import com.telna.userInfoSystem.model.UserDetails;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserIDMgmtService implements IUserIDMgmtService {

    public String generateUserID(User user, List<UserDetails> userDetailsListInMemory) {
        StringBuilder generatedUserId = new StringBuilder();

        if(!validateName(user.getName())) {
            generatedUserId.append("VALIDATION ERROR : Please enter only characters/letters in the name. ");
        }

        if(!validateEmail(user.getEmail())) {
            generatedUserId.append("VALIDATION ERROR : Please enter a valid email ID. ");
        }

        if(isDuplicateEmail(user.getEmail(), userDetailsListInMemory)) {
            generatedUserId.append("VALIDATION ERROR : Duplicate email ID. This email already exists. " +
                    "Please enter a valid email ID. ");
        }

        if(!validatePhoneNumber(user.getPhoneNumber())) {
            generatedUserId.append("VALIDATION ERROR : Please enter a valid phone number with only numbers & dashes. ");
        }

        if(generatedUserId.length() == 0) {
            generatedUserId = generateRandomUserID();
        }


        return generatedUserId.toString();
    }

    @Override
    public String validateUsageInfoRequest(UsageDetails usageDetails, Map<String,
            List<List<String>>> usageDetailsMapInMemory) {
        //Takes in User Id.
        //Type of usage: DATA, VOICE SMS.
        //Enter the date in the YYYY/MM/DD format in the request body.

        StringBuilder responseFromValidateUsageInfoRequest = new StringBuilder();

        String userID = usageDetails.getUserID();
        if(!usageDetailsMapInMemory.containsKey(userID)) {
            responseFromValidateUsageInfoRequest.
                    append("VALIDATION ERROR : The passed userID does not exist. ");
        }

        String usageType = usageDetails.getUsageType();
        if(!usageType.equalsIgnoreCase("DATA")
                && !usageType.equalsIgnoreCase("VOICE")
                && !usageType.equalsIgnoreCase("SMS")) {
            responseFromValidateUsageInfoRequest.
                    append("VALIDATION ERROR : The passed usageType does not exist. " +
                            "Valid usageType values are DATA, VOICE, SMS. ");
        }

        String timeStamp = usageDetails.getTimeStamp();
        Date datePassed = null;
        try {
            datePassed = new SimpleDateFormat("yyyy/MM/dd").parse(timeStamp);
            System.out.println("datePassed is : " +  datePassed);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date todaysDate = new Date();
        if(null == datePassed || datePassed.after(todaysDate)) {
            responseFromValidateUsageInfoRequest.
                    append("VALIDATION ERROR : The passed timestamp is a future date. " +
                            "Please enter a date less than or equal to today's date. ");
        }

        return responseFromValidateUsageInfoRequest.toString();
    }

    @Override
    public String validateFetchUsageInfoRequest(UsageHistory usageHistory,
                                                Map<String, List<List<String>>> usageDetailsMapInMemory) {
        //Validations :
        //If the ID does not exist, an error should be thrown.
        //The day to fetch information from — should not be future date.
        StringBuilder responseFromValidateUsageInfoRequest = new StringBuilder();

        String userID = usageHistory.getUserID();
        if(!usageDetailsMapInMemory.containsKey(userID)) {
            responseFromValidateUsageInfoRequest.
                    append("VALIDATION ERROR : The passed userID does not exist. ");
        }

        String timeStamp = usageHistory.getStartDate();
        Date datePassed = null;
        try {
            datePassed = new SimpleDateFormat("yyyy/MM/dd").parse(timeStamp);
            System.out.println("datePassed is : " +  datePassed);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date todaysDate = new Date();
        if(null == datePassed || datePassed.after(todaysDate)) {
            responseFromValidateUsageInfoRequest.
                    append("VALIDATION ERROR : The passed startDate is a future date. " +
                            "Please enter a date less than or equal to today's date. ");
        }

        return responseFromValidateUsageInfoRequest.toString();
    }

    private StringBuilder generateRandomUserID() {
        //return a 10 digit alpha-numeric string.
        return new StringBuilder(UUID.randomUUID().toString().substring(0,10));
    }

    private boolean isDuplicateEmail(String email, List<UserDetails> userDetailsListInMemory) {
        for(UserDetails userDetails : userDetailsListInMemory) {
            if(userDetails.getUser().getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }

        return false;
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        return (phoneNumber != null)
                && (!phoneNumber.equals(""))
                && phoneNumber.matches("^[0-9-]*$");
    }

    private boolean validateEmail(String email) {
        return email.contains("@") && email.contains(".")
        && (email.lastIndexOf("@") < email.lastIndexOf("."));
    }

    //Check if the name contains ONLY letters.
    private boolean validateName(String name) {
        return ((name != null)
                && (!name.equals(""))
                && (name.matches("^[a-zA-Z]*$")));
    }

}