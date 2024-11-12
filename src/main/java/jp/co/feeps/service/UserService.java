package jp.co.feeps.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.feeps.dto.UserDTO;
import jp.co.feeps.entity.User;
import jp.co.feeps.form.UserLoginForm;
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
}