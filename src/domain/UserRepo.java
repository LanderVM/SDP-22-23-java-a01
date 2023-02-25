package domain;

import repository.UserMapper;

public class UserRepo {
	
	private UserMapper userMapper;
	
	public UserRepo (UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	public User requestUser (String accountName) {
		return userMapper.getUser(accountName);
	}
	
}
