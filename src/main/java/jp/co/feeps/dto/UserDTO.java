package jp.co.feeps.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	private int userId;
	private String userName;
	private String password;
	private String email;
}
