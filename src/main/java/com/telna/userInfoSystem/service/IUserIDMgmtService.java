package com.telna.userInfoSystem.service;

import com.telna.userInfoSystem.model.User;
import com.telna.userInfoSystem.model.UserDetails;

import java.util.List;

public interface IUserIDMgmtService {
    String generateUserID(User user, List<UserDetails> userDetailsListInMemory);

    void updateUsageInfo();
}
