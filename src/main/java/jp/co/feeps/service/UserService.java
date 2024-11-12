package jp.co.feeps.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.feeps.dto.UserDTO;
import jp.co.feeps.dto.UserRegisterDTO;
import jp.co.feeps.entity.User;
import jp.co.feeps.form.UserLoginForm;
import jp.co.feeps.form.UserRegisterForm;
import jp.co.feeps.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public UserDTO loginCheck(UserLoginForm form) {
		int userId = form.getUserId();
		String password = form.getPassword();

		Optional<User> userOpt = userRepository.findByUserIdAndPassword(userId, password);

		// ユーザーが見つからない場合、例外をスロー
		User user = userOpt.orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));

		UserDTO userDTO = new UserDTO(user.getUserName(), user.getEmail());

		return userDTO;
	}

	public UserRegisterDTO registUser(UserRegisterForm form) {
		String userName = form.getUserName();
		String password = form.getPassword();
		String email = form.getEmail();

		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		user.setEmail(email);

		userRepository.save(user);

		int userId = user.getUserId();

		UserRegisterDTO userRegisterDTO = new UserRegisterDTO(userId);

		return userRegisterDTO;
	}
}