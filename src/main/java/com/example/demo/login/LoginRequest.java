package com.example.demo.login;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
	@NotBlank(message = "사용자ID는 필수항목입니다.")
	private String username;

	@NotBlank(message = "비밀번호는 필수항목입니다.")
	private String password;

}
