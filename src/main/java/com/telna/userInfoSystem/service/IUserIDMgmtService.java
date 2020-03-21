package com.telna.userInfoSystem.service;

import com.telna.userInfoSystem.model.UsageDetails;
import com.telna.userInfoSystem.model.UsageHistoryRequest;
import com.telna.userInfoSystem.model.User;
import com.telna.userInfoSystem.model.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface IUserIDMgmtService {
    String generateUserID(User user, List<UserDetails> userDetailsListInMemory);

    String validateUsageInfoRequest(UsageDetails usageDetails, Map<String, List<List<String>>> usageDetailsMapInMemory);

    void updateUsageDetailsInMemory(UsageDetails usageDetails,
                               Map<String, List<List<String>>> usageDetailsMapInMemory);

    String validateFetchUsageInfoRequest(UsageHistoryRequest usageHistoryRequest, Map<String, List<List<String>>> usageDetailsMapInMemory);
}
