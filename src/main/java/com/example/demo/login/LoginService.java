package com.example.demo.login;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.config.jwt.JwtUtil;
import com.example.demo.user.SiteUser;
import com.example.demo.user.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /* 로그인 */
    @Transactional
    public void login(LoginForm requestDto, HttpServletResponse response) {

        // Optional<Member> optionalMember =
        // memberRepository.findByMemberName(requestDto.getMemberName());
        Optional<SiteUser> _siteUser = this.userRepository.findByusername(requestDto.getUsername());

        if (_siteUser.isEmpty()) {
            log.warn("회원이 존재하지 않음");
            throw new IllegalArgumentException("회원이 존재하지 않음");
        }

        // Member member = optionalMember.get();
        SiteUser siteUser = _siteUser.get();

        /* 비밀번호 다름. */
        if (!passwordEncoder.matches(requestDto.getPassword(), siteUser.getPassword())) {
            log.warn("비밀번호가 일치하지 않습니다.");
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // List<GrantedAuthority> authorities = new ArrayList<>();
        // if ("admin".equals(siteUser.getUsername())) {
        //     authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        // } else {
        //     authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        // }

        /* 토큰을 쿠키로 발급 및 응답에 추가 */
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER,
                jwtUtil.createToken(siteUser.getUsername(), null));
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7일 동안 유효
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setSecure(false);

        response.addCookie(cookie);

    }
}
