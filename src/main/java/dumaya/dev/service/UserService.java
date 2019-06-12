package dumaya.dev.service;

import dumaya.dev.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}
