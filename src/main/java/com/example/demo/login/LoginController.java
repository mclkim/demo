package com.example.demo.login;

import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.config.jwt.JwtUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Controller
public class LoginController {
  // @Autowired(required = true) // required를 false로 하여 주입 안받아도 예외처리 하지 않도록 함.
  private final AuthenticationManager authenticationManager;
  // private final CustomAuthenticationManager authenticationManager;

  // @Autowired
  private final JwtUtils jwtUtils;

  @PostMapping("/signin")
  public String authenticateUser(@Valid LoginRequest loginRequest,
      BindingResult bindingResult,
      HttpServletResponse response) {

    if (bindingResult.hasErrors()) {
      return "login_form";
    }

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    log.error("userDetails -----> {}", userDetails);

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

    log.error("{}", jwtCookie.getName());
    log.error("{}", jwtCookie.getValue());
    log.error("{}", jwtCookie.toString());

    /* 토큰을 쿠키로 발급 및 응답에 추가 */
    Cookie cookie = new Cookie(jwtCookie.getName(), jwtCookie.getValue());
    cookie.setMaxAge(7 * 24 * 60 * 60); // 7일 동안 유효
    cookie.setPath("/");
    // cookie.setDomain("localhost");
    cookie.setSecure(false);

    response.addCookie(cookie);

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
