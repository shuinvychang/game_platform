package com.shuinvy.game_platform.aspect;

import com.shuinvy.game_platform.dto.BaseRequest;
import com.shuinvy.game_platform.dto.OperationLogRequest;
import com.shuinvy.game_platform.model.User;
import com.shuinvy.game_platform.service.OperationLogService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tools.jackson.databind.ObjectMapper;


@Aspect
@Component
public class OperationLogAspect {

    @Autowired
    private OperationLogService operationLogService;

    @Around("@annotation(logging)")
    public Object log(
            ProceedingJoinPoint joinPoint,
            Logging logging
    ) throws Throwable {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        User adminUser =
                (User) authentication.getPrincipal();
        Integer userId = adminUser.getId();
        String username = adminUser.getUsername();

        ObjectMapper mapper = new ObjectMapper();
        String requestString = "";
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof BaseRequest request) {
                requestString = mapper.writeValueAsString(request);
                break;
            }
        }

        Object response;
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        try {
            response = joinPoint.proceed();
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if (errorMessage != null && errorMessage.length() > 254) {
                errorMessage = errorMessage.substring(0, 254);
            }
            // Only request, no response, so log error for response here
            operationLogService.create(new OperationLogRequest(
                    userId,
                    username,
                    methodMapping(request.getMethod()),
                    request.getRequestURI(),
                    requestString,
                    errorMessage
            ));
            throw e;
        }

        String responseJson = "";
        if (response instanceof ResponseEntity<?> entity) {
            Object body = entity.getBody();
            responseJson = mapper.writeValueAsString(body);
        }

        operationLogService.create(new OperationLogRequest(
                userId,
                username,
                methodMapping(request.getMethod()),
                request.getRequestURI(),
                requestString,
                responseJson,
                logging.memo()
        ));

        return response;
    }

    private Integer methodMapping(String method) {
        return switch (method) {
            case "POST" -> 1;
            case "PUT" -> 2;
            case "DELETE" -> 3;
            default -> 0;
        };
    }

}
