package com.example.demo.login;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Controller
// @RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;

    /* 로그인 뷰 */
    @GetMapping("/login")
    public String login(LoginForm loginForm,
            @AuthenticationPrincipal User userDetails) {
        /* 이미 로그인된 사용자일 경우 인덱스 페이지로 강제이동. */
        if (userDetails != null) {
            log.info(userDetails.getUsername() + "님이 로그인 페이지로 이동을 시도함. -> index 페이지로 강제 이동 함.");
            return "redirect:/";
        }
        return "login_form";
    }

    /* 로그인 API */
    @PostMapping("/api/login")
    public String login(@Valid LoginForm loginForm,
            BindingResult bindingResult,
            HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            return "login_form";
        }

        try {
            loginService.login(loginForm, response);
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("loginFailed", e.getMessage());
            return "login_form";
        }

        return "redirect:/";
    }

    /* 로그아웃 API */
    @GetMapping("/api/logout")
    public String logout(@CookieValue(value = "Authorization", defaultValue = "", required = false) Cookie jwtCookie,
            HttpServletResponse response) {

        /* jwt 쿠키를 가지고와서 제거한다. */
        jwtCookie.setValue(null);
        jwtCookie.setMaxAge(0);
        jwtCookie.setPath("/");

        response.addCookie(jwtCookie);

        return "redirect:/";
    }
}
