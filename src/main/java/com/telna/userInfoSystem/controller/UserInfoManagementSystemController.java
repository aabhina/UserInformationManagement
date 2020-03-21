package com.telna.userInfoSystem.controller;

import com.telna.userInfoSystem.model.*;
import com.telna.userInfoSystem.service.IUserIDMgmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/userInfoMgmt")
public class UserInfoManagementSystemController {

    //This should ideally be handled by a DB, but for simplicity, we are using an in-memory list here.
    List<UserDetails> userDetailsListInMemory = new ArrayList<>();

    //List<UsageDetails> usageDetailsListInMemory = new ArrayList<>();
    Map<String, List<List<String>>> usageDetailsMapInMemory =  new HashMap<>();

    @Autowired
    IUserIDMgmtService userIDMgmtService;

    @PostMapping("/generateUserID")
    public ResponseEntity<String> generateUserID(@RequestBody User user) {

        String responseFromUserIdMgmtService = userIDMgmtService.generateUserID(user, userDetailsListInMemory);

        if (responseFromUserIdMgmtService.contains("ERROR")) {
            return new ResponseEntity<>(
                    responseFromUserIdMgmtService,
                    HttpStatus.BAD_REQUEST);
        }

        else {
            UserDetails userDetails = new UserDetails();
            userDetails.setUser(user);
            userDetails.setUserID(responseFromUserIdMgmtService);
            userDetailsListInMemory.add(userDetails);

            usageDetailsMapInMemory.put(responseFromUserIdMgmtService, new ArrayList<>());
        }

        System.out.println("Current size of userDetailsListInMemory is : " + userDetailsListInMemory.size());

        return new ResponseEntity<>(
                "The generated userID is : " + responseFromUserIdMgmtService,
                HttpStatus.OK);
    }

    @PostMapping("updateUsageInfo")
    public ResponseEntity<UpdateUsageInfoResponse> updateUsageInfo(@RequestBody UsageDetails usageDetails) {

        UpdateUsageInfoResponse updateUsageInfoResponse = new UpdateUsageInfoResponse();

        String responseFromValidateUsageInfoService =  userIDMgmtService.
                validateUsageInfoRequest(usageDetails, usageDetailsMapInMemory);

        if (responseFromValidateUsageInfoService.contains("ERROR")) {
            updateUsageInfoResponse.setErrorList(responseFromValidateUsageInfoService);

            return new ResponseEntity<UpdateUsageInfoResponse>(
                    updateUsageInfoResponse,
                    HttpStatus.BAD_REQUEST);
        }
        else {
            userIDMgmtService.updateUsageDetailsInMemory(usageDetails, usageDetailsMapInMemory);
            updateUsageInfoResponse.setSuccessMessage("The usage details were successfully updated.");
        }

        System.out.println("usageDetailsMapInMemory is : " + usageDetailsMapInMemory);
        System.out.println("Current size of usageDetailsMapInMemory is : " + usageDetailsMapInMemory.size());

        return new ResponseEntity<UpdateUsageInfoResponse>(
                updateUsageInfoResponse,
                HttpStatus.OK);
    }

    @GetMapping("fetchUsageInfo")
    public ResponseEntity<UsageHistoryResponse> fetchUsageInfo(@RequestBody UsageHistoryRequest usageHistoryRequest) {
        //This will retrieve ALL information about the user’s usage history based
        //on a time range.
        //Takes in User Id, Start Date, Type of Data (DATA, VOICE, SMS, ALL) in the request body.

        UsageHistoryResponse usageHistoryResponse = new UsageHistoryResponse();

        String responseFromFetchUsageInfoService =  userIDMgmtService.
                validateFetchUsageInfoRequest(usageHistoryRequest, usageDetailsMapInMemory);

        if (responseFromFetchUsageInfoService.contains("ERROR")) {
            usageHistoryResponse.setErrorString(responseFromFetchUsageInfoService);

            return new ResponseEntity<UsageHistoryResponse>(
                    usageHistoryResponse,
                    HttpStatus.BAD_REQUEST);
        }

        List<List<String>> usageHistoryList = new ArrayList<>();
        List<List<String>> usageHistoryListFiltered = new ArrayList<>();

        String usageType = usageHistoryRequest.getUsageType();

        String userID = usageHistoryRequest.getUserID();
        usageHistoryList = usageDetailsMapInMemory.get(userID);

        String startDate = usageHistoryRequest.getStartDate();
        Date datePassed = null;
        try {
            datePassed = new SimpleDateFormat("yyyy/MM/dd").parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //filter the map for the lists which have a date greater than or equal to the passed date.
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

        usageHistoryResponse.setUserID(userID);
        usageHistoryResponse.setUsageDetailsList(usageHistoryListFiltered);

        return new ResponseEntity<UsageHistoryResponse>(usageHistoryResponse,
                HttpStatus.OK);
    }
}
