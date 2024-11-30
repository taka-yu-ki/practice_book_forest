package jp.co.feeps.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.feeps.dto.UserDTO;
import jp.co.feeps.entity.User;
import jp.co.feeps.form.UserLoginForm;
import jp.co.feeps.repository.UserRepository;

@Service
public class AuthService {
	@Autowired
	private UserRepository userRepository;

	public UserDTO loginCheck(UserLoginForm form) {
		int userId = form.getUserId();
		String password = form.getPassword();

		Optional<User> userOpt = userRepository.findByUserIdAndPassword(userId, password);
		User user = (User) userOpt.orElseThrow(() -> new RuntimeException("ログインIDもしくはパスワードが違います。"));

		UserDTO userDTO = new UserDTO();
		userDTO.setUserId(user.getUserId());
		userDTO.setUserName(user.getUserName());
		userDTO.setPassword(user.getPassword());
		userDTO.setEmail(user.getEmail());

		return userDTO;
	}
}
