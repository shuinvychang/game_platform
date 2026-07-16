package com.shuinvy.game_platform.common;

import jakarta.servlet.http.HttpServletRequest;

public class IPHandler {

    public static String getClientIp(HttpServletRequest request) {
        String header = request.getHeader("X-Forwarded-For");
        if (header != null && !header.isBlank()) {
            String[] ips = header.split(",");
            return ips[0].trim();
        }

        String fallback = request.getHeader("X-Real-IP");
        if (fallback != null && !fallback.isBlank()) {
            return fallback.trim();
        }

        String ip = request.getRemoteAddr();

        if ("0:0:0:0:0:0:0:1".equals(ip) ||
                "::1".equals(ip)) {
            return "127.0.0.1";
        }

        return request.getRemoteAddr();
    }
}
