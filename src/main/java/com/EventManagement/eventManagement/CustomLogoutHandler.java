package com.EventManagement.eventManagement;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UrlPathHelper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// public class CustomLogoutHandler {
    
// }

@Service
public class CustomLogoutHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
                

            UrlPathHelper helper=new UrlPathHelper();
            String ip=helper.getContextPath(request);
            //same as in custom login user handler we can find roles and lout to a page 

            response.sendRedirect(ip + "/login");
            // response.sendRedirect(ip + "/");
    }

    
}