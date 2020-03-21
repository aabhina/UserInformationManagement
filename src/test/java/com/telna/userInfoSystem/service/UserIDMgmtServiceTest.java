package com.telna.userInfoSystem.service;

import com.telna.userInfoSystem.model.User;
import com.telna.userInfoSystem.model.UserDetails;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.*;
import org.springframework.util.Assert;


import java.util.ArrayList;
import java.util.List;

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
    void filterUsageList() {
    }

    @Test
    void validateUsageInfoRequest() {
    }

    @Test
    void updateUsageDetailsInMemory() {
    }

    @Test
    void validateFetchUsageInfoRequest() {
    }
}