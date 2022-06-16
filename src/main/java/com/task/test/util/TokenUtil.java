package com.task.test.util;


import com.task.test.dto.profile.UserDetails;
import com.task.test.enums.ProfileRole;
import com.task.test.exp.MethodNotAllowedExc;

import javax.servlet.http.HttpServletRequest;

public class TokenUtil {
    public static UserDetails getCurrentUser(HttpServletRequest request) {
        UserDetails currentUser = (UserDetails) request.getAttribute("currentUser");
        if (currentUser == null) {
            throw new MethodNotAllowedExc("Method not allowed");
        }
        return currentUser;
    }

    public static UserDetails getCurrentUser(HttpServletRequest request, ProfileRole role) {
        UserDetails currentUser = (UserDetails) request.getAttribute("currentUser");
        if (currentUser == null || !currentUser.getRole().equals(role)) {
            throw new MethodNotAllowedExc("Method not allowed");
        }

        return currentUser;
    }
}
