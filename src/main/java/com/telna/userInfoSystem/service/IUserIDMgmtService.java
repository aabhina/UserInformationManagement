package com.telna.userInfoSystem.service;

import com.telna.userInfoSystem.model.UsageDetails;
import com.telna.userInfoSystem.model.UsageHistory;
import com.telna.userInfoSystem.model.User;
import com.telna.userInfoSystem.model.UserDetails;

import java.util.List;
import java.util.Map;

public interface IUserIDMgmtService {
    String generateUserID(User user, List<UserDetails> userDetailsListInMemory);

    String validateUsageInfoRequest(UsageDetails usageDetails, Map<String, List<List<String>>> usageDetailsMapInMemory);

    String validateFetchUsageInfoRequest(UsageHistory usageHistory, Map<String, List<List<String>>> usageDetailsMapInMemory);
}
