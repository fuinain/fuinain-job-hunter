package com.example.config;

import com.example.domain.Permission;
import com.example.domain.Role;
import com.example.domain.User;
import com.example.service.UserService;
import com.example.util.SecurityUtil;
import com.example.util.error.IdInvalidException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.List;

public class PermissionInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;

    @Override
    @Transactional
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response, Object handler)
            throws Exception {

        String path = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();
        System.out.println(">>> RUN preHandle");
        System.out.println(">>> path= " + path);
        System.out.println(">>> httpMethod= " + httpMethod);
        System.out.println(">>> requestURI= " + requestURI);

        //Check permission
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : null;
        if(email != null && !email.isEmpty()) {
            User user = this.userService.handleGetUserByUserName(email);
            if (user != null) {
                Role role = user.getRole();
                if (role != null){
                    List<Permission> permissions = role.getPermissions();
                    boolean isAllowed = permissions.stream().anyMatch(
                            item -> item.getApiPath().equals(path) && item.getMethod().equals(httpMethod)
                    );

                    if (isAllowed == false){
                        throw new IdInvalidException("ban khong co quyen truy cap chuc nang nay");
                    }
                }
            }
        }

        return true;
    }
}