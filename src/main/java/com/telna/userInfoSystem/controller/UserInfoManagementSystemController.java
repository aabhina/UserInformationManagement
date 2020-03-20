package com.telna.userInfoSystem.controller;

import com.telna.userInfoSystem.model.*;
import com.telna.userInfoSystem.service.IUserIDMgmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/userInfoMgmt")
public class UserInfoManagementSystemController {

    List<UserDetails> userDetailsListInMemory = new ArrayList<>();

    @Autowired
    IUserIDMgmtService userIDMgmtService;

    @PostMapping("/generateUserID/v1")
    public String generateUserID(@RequestBody User user) {

        String generatedUserId = userIDMgmtService.generateUserID(user, userDetailsListInMemory);

        if(!generatedUserId.contains("ERROR")) {
            UserDetails userDetails = new UserDetails();
            userDetails.setUser(user);
            userDetails.setUserID(generatedUserId);
            userDetailsListInMemory.add(userDetails);
        }

        System.out.println(userDetailsListInMemory.size());

        return generatedUserId;
    }

    @PostMapping("updateUsageInfo")
    public UserInfoMgmtResponse updateUsageInfo(@RequestBody UsageDetails usageDetails) {
        //Takes in User Id, Type of usage: DATA, VOICE SMS, Time stamp
        //(Enter in the date, month, year of usage) in the request body.

        //Validations :
        //If the ID does not exist, an error should be thrown.
        //If a non-existent parameter is entered, an error should be thrown.
        //Date validation so that it is a valid date in the past.
        
        UserInfoMgmtResponse userInfoMgmtResponse = new UserInfoMgmtResponse();

        //userIDMgmtService.updateUsageInfo(userDetailsListInMemory);

        return userInfoMgmtResponse;
    }

    @GetMapping("fetchUsageInfo")
    public UsageHistory fetchUsageInfo() {
        UsageHistory usageHistory = new UsageHistory();

        return usageHistory;
    }
}
