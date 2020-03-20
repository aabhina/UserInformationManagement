package com.telna.userInfoSystem.service;

import com.telna.userInfoSystem.model.User;
import com.telna.userInfoSystem.model.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserIDMgmtService implements IUserIDMgmtService {

    public String generateUserID(User user, List<UserDetails> userDetailsListInMemory) {
        String generatedUserId = "";

        if(!validateName(user.getName())) {
            return "VALIDATION ERROR : Please enter only characters/letters in the name";
        }

        if(!validateEmail(user.getEmail())) {
            return "VALIDATION ERROR : Please enter a valid email ID";
        }

        if(isDuplicateEmail(user.getEmail(), userDetailsListInMemory)) {
            return "VALIDATION ERROR : Duplicate email ID. This email already exists. " +
                    "Please enter a valid email ID.";
        }

        if(!validatePhoneNumber(user.getPhoneNumber())) {
            return "VALIDATION ERROR : Please enter a valid phone number with only numbers & dashes.";
        }

        generatedUserId = generateRandomUserID();

        return generatedUserId;
    }

    @Override
    public void updateUsageInfo() {

    }

    private String generateRandomUserID() {
        //return a 10 digit alpha-numeric string.
        return UUID.randomUUID().toString().substring(0,10);
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
        //return true;
    }

    private boolean validateEmail(String email) {
        return email.contains("@") && email.contains(".")
        && (email.lastIndexOf("@") < email.lastIndexOf("."));
        //return true;
    }

    //Check if the name contains ONLY letters.
    private boolean validateName(String name) {
        return ((name != null)
                && (!name.equals(""))
                && (name.matches("^[a-zA-Z]*$")));
    }

}