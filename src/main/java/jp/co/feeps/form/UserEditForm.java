package jp.co.feeps.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEditForm {
	private String userName;
	private String password;
	private String email;
}