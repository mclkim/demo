package com.example.demo.login;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.security.UserDetailsImpl;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.user.UserSecurityService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Controller
public class LoginController {
  private final JwtUtils jwtUtils;
  private final UserSecurityService userSecurityService;

  @PostMapping("/signin")
  public String authenticateUser(@Valid LoginRequest loginRequest,
      BindingResult bindingResult,
      HttpServletResponse response) {

    if (bindingResult.hasErrors()) {
      return "login_form";
    }
    
    try {
      UserDetailsImpl userDetails = userSecurityService.login(loginRequest, response);

      ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

      /* 토큰을 쿠키로 발급 및 응답에 추가 */
      Cookie cookie = new Cookie(jwtCookie.getName(), jwtCookie.getValue());
      cookie.setMaxAge(7 * 24 * 60 * 60); // 7일 동안 유효
      cookie.setPath("/");
      // cookie.setDomain("localhost");
      cookie.setSecure(false);
      response.addCookie(cookie);
    } catch (Exception e) {
      e.printStackTrace();
      bindingResult.reject("signinFailed", e.getMessage());
      return "login_form";
    }

    return "redirect:/";
  }

  @GetMapping("/signout")
  @PostMapping("/signout")
  public String logoutUser(HttpServletResponse response) {
    ResponseCookie jwtCookie = jwtUtils.getCleanJwtCookie();
    // ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
    // .body(new MessageResponse("You've been signed out!"));

    Cookie cookie = new Cookie(jwtCookie.getName(), null);
    response.addCookie(cookie);

    return "redirect:/";
  }
}
