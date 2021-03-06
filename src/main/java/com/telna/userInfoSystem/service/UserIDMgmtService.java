package com.telna.userInfoSystem.service;

import com.telna.userInfoSystem.model.UsageDetails;
import com.telna.userInfoSystem.model.UsageHistoryRequest;
import com.telna.userInfoSystem.model.User;
import com.telna.userInfoSystem.model.UserDetails;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserIDMgmtService implements IUserIDMgmtService {

    public static final String VALIDATION_ERROR_NAME = "VALIDATION ERROR : Please enter only characters/letters in the name. ";
    public static final String VALIDATION_ERROR_EMAIL = "VALIDATION ERROR : Please enter a valid email ID. ";
    public static final String VALIDATION_ERROR_DUPLICATE_EMAIL = "VALIDATION ERROR : Duplicate email ID. This email already exists. Please enter a valid email ID. ";
    public static final String VALIDATION_ERROR_PHONE_NUMBER = "VALIDATION ERROR : Please enter a valid phone number with only numbers & dashes. ";
    public static final String VALIDATION_ERROR_USER_ID = "VALIDATION ERROR : The passed userID does not exist. ";
    public static final String VALIDATION_ERROR_USAGE_TYPE = "VALIDATION ERROR : The passed usageType does not exist. Valid usageType values are DATA, VOICE, SMS. ";
    public static final String VALIDATION_ERROR_TIMESTAMP = "VALIDATION ERROR : The passed timestamp is a future date. Please enter a date less than or equal to today's date. ";
    public static final String VALIDATION_ERROR_USER_ID_DOES_NOT_EXIST = "VALIDATION ERROR : The passed userID does not exist. ";
    public static final String VALIDATION_ERROR_FUTURE_DATE = "VALIDATION ERROR : The passed startDate is a future date. Please enter a date less than or equal to today's date. ";

    @Override
    public String validateUserInputAndGenerateUserID(User user, List<UserDetails> userDetailsListInMemory) {
        StringBuilder generatedUserId = new StringBuilder();

        if(!validateName(user.getName())) {
            generatedUserId.append(VALIDATION_ERROR_NAME);
        }

        if(!validateEmail(user.getEmail())) {
            generatedUserId.append(VALIDATION_ERROR_EMAIL);
        }

        if(isDuplicateEmail(user.getEmail(), userDetailsListInMemory)) {
            generatedUserId.append(VALIDATION_ERROR_DUPLICATE_EMAIL);
        }

        if(!validatePhoneNumber(user.getPhoneNumber())) {
            generatedUserId.append(VALIDATION_ERROR_PHONE_NUMBER);
        }

        if(generatedUserId.length() == 0) {
            generatedUserId = generateRandomUserID();
        }


        return generatedUserId.toString();
    }

    @Override
    public void filterUsageList(List<List<String>> usageHistoryList,
                                 List<List<String>> usageHistoryListFiltered,
                                 String usageType, Date datePassed) {
        for(List<String> usgHistory : usageHistoryList) {
            String usageTypeFromMemoryMap = usgHistory.get(0);

            String dateString = usgHistory.get(1);
            Date dateStoredInUsageMap = null;
            try {
                dateStoredInUsageMap = new SimpleDateFormat("yyyy/MM/dd").parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(!datePassed.after(dateStoredInUsageMap)
                    && (usageType.equalsIgnoreCase(usageTypeFromMemoryMap)
                    || usageType.equalsIgnoreCase("ALL"))) {
                usageHistoryListFiltered.add(usgHistory);
            }

        }
    }

    @Override
    public String validateUsageInfoRequest(UsageDetails usageDetails, Map<String,
            List<List<String>>> usageDetailsMapInMemory) {
        //Takes in User Id.
        //Type of usage: DATA, VOICE SMS.
        //The date in the YYYY/MM/DD format in the request body.

        StringBuilder responseFromValidateUsageInfoRequest = new StringBuilder();

        //Validate if userID is already present.
        String userID = usageDetails.getUserID();
        if(!usageDetailsMapInMemory.containsKey(userID)) {
            responseFromValidateUsageInfoRequest.
                    append(VALIDATION_ERROR_USER_ID);
        }

        //Validate if usageType is one of the 3 values.
        String usageType = usageDetails.getUsageType();
        if(!usageType.equalsIgnoreCase("DATA")
                && !usageType.equalsIgnoreCase("VOICE")
                && !usageType.equalsIgnoreCase("SMS")) {
            responseFromValidateUsageInfoRequest.
                    append(VALIDATION_ERROR_USAGE_TYPE);
        }

        //Validate if the date passed is not a future date.
        String timeStamp = usageDetails.getTimeStamp();
        Date datePassed = null;
        try {
            datePassed = new SimpleDateFormat("yyyy/MM/dd").parse(timeStamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date todaysDate = new Date();
        if(null == datePassed || datePassed.after(todaysDate)) {
            responseFromValidateUsageInfoRequest.
                    append(VALIDATION_ERROR_TIMESTAMP);
        }

        return responseFromValidateUsageInfoRequest.toString();
    }

    public void updateUsageDetailsInMemory(UsageDetails usageDetails,
                                            Map<String, List<List<String>>> usageDetailsMapInMemory) {
        List<String> listOfUsageDetailsAndTimestamp = new ArrayList<>();
        listOfUsageDetailsAndTimestamp.add(usageDetails.getUsageType());
        listOfUsageDetailsAndTimestamp.add(usageDetails.getTimeStamp());

        List<List<String>> listOfUsageDetailsAndTimestampAlreadyPresent = new ArrayList<>();

        if(usageDetailsMapInMemory.containsKey(usageDetails.getUserID())) {
            listOfUsageDetailsAndTimestampAlreadyPresent =
                    usageDetailsMapInMemory.get(usageDetails.getUserID());
            listOfUsageDetailsAndTimestampAlreadyPresent.add(listOfUsageDetailsAndTimestamp);

            usageDetailsMapInMemory.put(usageDetails.getUserID(), listOfUsageDetailsAndTimestampAlreadyPresent);
        }
        else {
            listOfUsageDetailsAndTimestampAlreadyPresent.add(listOfUsageDetailsAndTimestamp);
            usageDetailsMapInMemory.put(usageDetails.getUserID(), listOfUsageDetailsAndTimestampAlreadyPresent);
        }
    }

    @Override
    public String validateFetchUsageInfoRequest(UsageHistoryRequest usageHistoryRequest,
                                                Map<String, List<List<String>>> usageDetailsMapInMemory) {

        //If the ID does not exist, an error should be thrown.
        //The day to fetch information from — should not be future date.

        StringBuilder responseFromValidateUsageInfoRequest = new StringBuilder();

        //Validate if the userID exists.
        String userID = usageHistoryRequest.getUserID();
        if(!usageDetailsMapInMemory.containsKey(userID)) {
            responseFromValidateUsageInfoRequest.
                    append(VALIDATION_ERROR_USER_ID_DOES_NOT_EXIST);
        }

        //Validate if the date passed is not a future date.
        String timeStamp = usageHistoryRequest.getStartDate();
        Date datePassed = null;
        try {
            datePassed = new SimpleDateFormat("yyyy/MM/dd").parse(timeStamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date todaysDate = new Date();
        if(null == datePassed || datePassed.after(todaysDate)) {
            responseFromValidateUsageInfoRequest.
                    append(VALIDATION_ERROR_FUTURE_DATE);
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
                && (name.matches("^[a-zA-Z\\s]*$")));
    }

}