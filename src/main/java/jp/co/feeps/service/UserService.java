package jp.co.feeps.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.feeps.dto.UserDTO;
import jp.co.feeps.dto.UserRegisterDTO;
import jp.co.feeps.entity.User;
import jp.co.feeps.form.UserEditForm;
import jp.co.feeps.form.UserRegisterForm;
import jp.co.feeps.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public UserRegisterDTO registUser(UserRegisterForm form) {
		String userName = form.getUserName();
		String password = form.getPassword();
		String email = form.getEmail();

		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		user.setEmail(email);

		userRepository.save(user);

		// ユーザーを登録後に自動生成された userId を取得
		int userId = user.getUserId();

		UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
		userRegisterDTO.setUserId(userId);

		return userRegisterDTO;
	}

	public UserDTO updateUser(int userId, UserEditForm form) {
		Optional<User> userOpt = userRepository.findByUserId(userId);

		User user = (User) userOpt.orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));

		String userName = form.getUserName();
		String password = form.getPassword();
		String email = form.getEmail();

		user.setUserName(userName);
		user.setPassword(password);
		user.setEmail(email);

		userRepository.save(user);

		UserDTO userDTO = new UserDTO();
		userDTO.setUserId(userId);
		userDTO.setUserName(userName);
		userDTO.setEmail(email);

		return userDTO;
	}
}