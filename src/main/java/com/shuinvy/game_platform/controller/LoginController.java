package com.shuinvy.game_platform.controller;

import com.shuinvy.game_platform.dto.LoginRequest;
import com.shuinvy.game_platform.model.User;
import com.shuinvy.game_platform.security.CustomUserDetail;
import com.shuinvy.game_platform.service.JwtService;
import com.shuinvy.game_platform.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Login Controller", description = "登入驗證相關的 API")
public class LoginController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Operation(summary = "登入", description = "透過帳號密碼進行登入動作，並回傳JWT Token。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳出JWT Token"),
            @ApiResponse(responseCode = "401", description = "登入驗證失敗", content = @Content)
    })
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Incorrect username or password");
        }
        User userObj = userService.getByUsername(user.getUsername());
        CustomUserDetail  customUserDetail = new CustomUserDetail(userObj);
        return jwtService.createLoginAccessToken(customUserDetail);
    }
}
