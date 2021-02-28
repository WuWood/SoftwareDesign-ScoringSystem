package service.user;

import pojo.User;

public interface UserService {
    //用户登录
    public User login(String userCode, String password);

    //增加用户信息
    public boolean add(User user);
}
