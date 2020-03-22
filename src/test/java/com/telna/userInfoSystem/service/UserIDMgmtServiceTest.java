package com.telna.userInfoSystem.service;

import com.telna.userInfoSystem.model.UsageDetails;
import com.telna.userInfoSystem.model.UsageHistoryRequest;
import com.telna.userInfoSystem.model.User;
import com.telna.userInfoSystem.model.UserDetails;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.*;
import org.springframework.util.Assert;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UserIDMgmtServiceTest {

    @Test
    void validateUserInputAndGenerateUserID_ForAllValidRequestAttributes() {
        IUserIDMgmtService userIDMgmtService = new UserIDMgmtService();

        User user = new User();
        user.setName("JohnDoe");
        user.setCountry("Canada");
        user.setEmail("johdoe@gmail.com");
        user.setPhoneNumber("999-999-1234");

        List<UserDetails> userDetailsListInMemory = new ArrayList<>();

        String response = userIDMgmtService.validateUserInputAndGenerateUserID(user, userDetailsListInMemory);

        assertNotNull(response);
        assertEquals(10, response.length());
        Assert.doesNotContain("ERROR",response);
    }

    @Test
    void validateUserInputAndGenerateUserID_ForInvalidNameOnly() {
        IUserIDMgmtService userIDMgmtService = new UserIDMgmtService();

        User user = new User();
        user.setName("JohnDoe123");
        user.setCountry("Canada");
        user.setEmail("johdoes@gmail.com");
        user.setPhoneNumber("999-999-1234");

        List<UserDetails> userDetailsListInMemory = new ArrayList<>();

        String response = userIDMgmtService.validateUserInputAndGenerateUserID(user, userDetailsListInMemory);

        String validationErrorMessage = "VALIDATION ERROR : Please enter only characters/letters in the name.";
        assertNotNull(response);
        Assert.hasText(validationErrorMessage,response);
    }

    @Test
    void validateUserInputAndGenerateUserID_ForInvalidEmailOnly() {
        IUserIDMgmtService userIDMgmtService = new UserIDMgmtService();

        User user = new User();
        user.setName("JohnDoe");
        user.setCountry("Canada");
        user.setEmail("@johdoesgmail.com");
        user.setPhoneNumber("999-999-1234");

        List<UserDetails> userDetailsListInMemory = new ArrayList<>();

        String response = userIDMgmtService.validateUserInputAndGenerateUserID(user, userDetailsListInMemory);

        String validationErrorMessage = "VALIDATION ERROR : Please enter a valid email ID. ";
        assertNotNull(response);
        Assert.hasText(validationErrorMessage,response);
    }

    @Test
    void validateUserInputAndGenerateUserID_ForInvalidPhoneNumberOnly() {
        IUserIDMgmtService userIDMgmtService = new UserIDMgmtService();

        User user = new User();
        user.setName("JohnDoe");
        user.setCountry("Canada");
        user.setEmail("johdoe@gmail.com");
        user.setPhoneNumber("999-999-12..");

        List<UserDetails> userDetailsListInMemory = new ArrayList<>();

        String response = userIDMgmtService.validateUserInputAndGenerateUserID(user, userDetailsListInMemory);

        String validationErrorMessage = "VALIDATION ERROR : Please enter a valid phone number with only numbers & dashes. ";
        assertNotNull(response);
        Assert.hasText(validationErrorMessage,response);
    }

    @Test
    void validateUserInputAndGenerateUserID_ForAllInvalidAttributesInRequest() {
        IUserIDMgmtService userIDMgmtService = new UserIDMgmtService();

        User user = new User();
        user.setName("JohnDoe123");
        user.setCountry("Canada");
        user.setEmail("@johdoegmail.com");
        user.setPhoneNumber("999-999-12..");

        List<UserDetails> userDetailsListInMemory = new ArrayList<>();

        String response = userIDMgmtService.validateUserInputAndGenerateUserID(user, userDetailsListInMemory);

        String validationErrorMessage1 = "VALIDATION ERROR : Please enter a valid phone number with only numbers & dashes. ";
        String validationErrorMessage2 = "VALIDATION ERROR : Please enter a valid email ID. ";
        String validationErrorMessage3 = "VALIDATION ERROR : Please enter only characters/letters in the name.";

        assertNotNull(response);
        Assert.hasText(validationErrorMessage1,response);
        Assert.hasText(validationErrorMessage2,response);
        Assert.hasText(validationErrorMessage3,response);
    }

    @Test
    void validateUsageInfoRequest_ForAllValidParamsInRequest() {
        IUserIDMgmtService userIDMgmtService = new UserIDMgmtService();

        UsageDetails usageDetails = new UsageDetails();
        usageDetails.setUserID("abcdefghij");
        usageDetails.setUsageType("DATA");
        usageDetails.setTimeStamp("2020/02/21");

        Map<String, List<List<String>>> usageDetailsMapInMemory =  new HashMap<>();
        usageDetailsMapInMemory.put("abcdefghij", new ArrayList<>());

        String responseFromValidateUsageInfoRequest =
                userIDMgmtService.validateUsageInfoRequest(usageDetails, usageDetailsMapInMemory);

        assertNotNull(responseFromValidateUsageInfoRequest);
        Assert.doesNotContain("ERROR",responseFromValidateUsageInfoRequest);
    }

    @Test
    void validateUsageInfoRequest_ForUserIdNotPresent() {
        IUserIDMgmtService userIDMgmtService = new UserIDMgmtService();

        UsageDetails usageDetails = new UsageDetails();
        usageDetails.setUserID("abcdefghij");
        usageDetails.setUsageType("DATA");
        usageDetails.setTimeStamp("2020/02/21");

        Map<String, List<List<String>>> usageDetailsMapInMemory =  new HashMap<>();
        //usageDetailsMapInMemory.put("abcdefghij", new ArrayList<>());

        String responseFromValidateUsageInfoRequest =
                userIDMgmtService.validateUsageInfoRequest(usageDetails, usageDetailsMapInMemory);

        String VALIDATION_ERROR_USER_ID = "VALIDATION ERROR : The passed userID does not exist. ";

        assertNotNull(responseFromValidateUsageInfoRequest);
        Assert.hasText(VALIDATION_ERROR_USER_ID,responseFromValidateUsageInfoRequest);
    }

    @Test
    void validateUsageInfoRequest_ForIncorrectUsageType() {
        IUserIDMgmtService userIDMgmtService = new UserIDMgmtService();

        UsageDetails usageDetails = new UsageDetails();
        usageDetails.setUserID("abcdefghij");
        usageDetails.setUsageType("ABC");
        usageDetails.setTimeStamp("2020/02/21");

        Map<String, List<List<String>>> usageDetailsMapInMemory =  new HashMap<>();
        usageDetailsMapInMemory.put("abcdefghij", new ArrayList<>());

        String responseFromValidateUsageInfoRequest =
                userIDMgmtService.validateUsageInfoRequest(usageDetails, usageDetailsMapInMemory);

        String VALIDATION_ERROR_USAGE_TYPE = "VALIDATION ERROR : The passed usageType does not exist. Valid usageType values are DATA, VOICE, SMS. ";

        assertNotNull(responseFromValidateUsageInfoRequest);
        Assert.hasText(VALIDATION_ERROR_USAGE_TYPE,responseFromValidateUsageInfoRequest);
    }

    @Test
    void validateUsageInfoRequest_ForFutureDateInRequest() {
        IUserIDMgmtService userIDMgmtService = new UserIDMgmtService();

        UsageDetails usageDetails = new UsageDetails();
        usageDetails.setUserID("abcdefghij");
        usageDetails.setUsageType("DATA");
        usageDetails.setTimeStamp("2020/05/21");

        Map<String, List<List<String>>> usageDetailsMapInMemory =  new HashMap<>();
        usageDetailsMapInMemory.put("abcdefghij", new ArrayList<>());

        String responseFromValidateUsageInfoRequest =
                userIDMgmtService.validateUsageInfoRequest(usageDetails, usageDetailsMapInMemory);

        String VALIDATION_ERROR_TIMESTAMP = "VALIDATION ERROR : The passed timestamp is a future date. Please enter a date less than or equal to today's date. ";

        assertNotNull(responseFromValidateUsageInfoRequest);
        Assert.hasText(VALIDATION_ERROR_TIMESTAMP,responseFromValidateUsageInfoRequest);
    }

    @Test
    void validateFetchUsageInfoRequest_ForAllValidParamsInRequest() {
        IUserIDMgmtService userIDMgmtService = new UserIDMgmtService();

        UsageHistoryRequest usageHistoryRequest = new UsageHistoryRequest();
        usageHistoryRequest.setUserID("abcdefghij");
        usageHistoryRequest.setStartDate("2020/02/12");

        Map<String, List<List<String>>> usageDetailsMapInMemory = new HashMap<>();
        usageDetailsMapInMemory.put("abcdefghij", new ArrayList<>());

        String responseFromValidateUsageInfoRequest =
                userIDMgmtService.validateFetchUsageInfoRequest(usageHistoryRequest, usageDetailsMapInMemory);

        assertNotNull(responseFromValidateUsageInfoRequest);
        Assert.doesNotContain("ERROR",responseFromValidateUsageInfoRequest);
    }

    @Test
    void validateFetchUsageInfoRequest_ForInvalidUserID() {
        IUserIDMgmtService userIDMgmtService = new UserIDMgmtService();

        UsageHistoryRequest usageHistoryRequest = new UsageHistoryRequest();
        usageHistoryRequest.setUserID("abcdefghij");
        usageHistoryRequest.setStartDate("2020/02/12");

        Map<String, List<List<String>>> usageDetailsMapInMemory = new HashMap<>();
        //usageDetailsMapInMemory.put("abcdefghij", new ArrayList<>());

        String responseFromValidateUsageInfoRequest =
                userIDMgmtService.validateFetchUsageInfoRequest(usageHistoryRequest, usageDetailsMapInMemory);

        String VALIDATION_ERROR_USER_ID_DOES_NOT_EXIST = "VALIDATION ERROR : The passed userID does not exist. ";

        assertNotNull(responseFromValidateUsageInfoRequest);
        Assert.hasText(VALIDATION_ERROR_USER_ID_DOES_NOT_EXIST,responseFromValidateUsageInfoRequest);
    }

    @Test
    void validateFetchUsageInfoRequest_ForFutureDateInRequest() {
        IUserIDMgmtService userIDMgmtService = new UserIDMgmtService();

        UsageHistoryRequest usageHistoryRequest = new UsageHistoryRequest();
        usageHistoryRequest.setUserID("abcdefghij");
        usageHistoryRequest.setStartDate("2020/05/12");

        Map<String, List<List<String>>> usageDetailsMapInMemory = new HashMap<>();
        usageDetailsMapInMemory.put("abcdefghij", new ArrayList<>());

        String responseFromValidateUsageInfoRequest =
                userIDMgmtService.validateFetchUsageInfoRequest(usageHistoryRequest, usageDetailsMapInMemory);

        String VALIDATION_ERROR_FUTURE_DATE = "VALIDATION ERROR : The passed startDate is a future date. Please enter a date less than or equal to today's date. ";

        assertNotNull(responseFromValidateUsageInfoRequest);
        Assert.hasText(VALIDATION_ERROR_FUTURE_DATE,responseFromValidateUsageInfoRequest);
    }

    @Test
    void updateUsageDetailsInMemory() {
        IUserIDMgmtService userIDMgmtService = new UserIDMgmtService();

        UsageDetails usageDetails =  new UsageDetails();
        usageDetails.setTimeStamp("2020/02/21");
        usageDetails.setUsageType("DATA");
        usageDetails.setUserID("abcdefghij");

        Map<String, List<List<String>>> usageDetailsMapInMemory = new HashMap<>();
        usageDetailsMapInMemory.put("abcdefghij", new ArrayList<>());

        userIDMgmtService.updateUsageDetailsInMemory(usageDetails, usageDetailsMapInMemory);

        assertNotNull(usageDetailsMapInMemory);
        assertEquals(1, usageDetailsMapInMemory.size());
    }

    @Test
    void filterUsageList() {
        IUserIDMgmtService userIDMgmtService = new UserIDMgmtService();

        List<List<String>> usageHistoryList = new ArrayList<>();
        List<List<String>> usageHistoryListFiltered = new ArrayList<>();
        String usageType = "";
        Date datePassed = new Date();

        userIDMgmtService.filterUsageList(usageHistoryList,
                usageHistoryListFiltered, usageType, datePassed);

        assertNotNull(usageHistoryListFiltered);
    }
}