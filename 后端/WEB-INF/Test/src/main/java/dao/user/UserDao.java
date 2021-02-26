package dao.user;

import pojo.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserDao {
    //得到要登录的用户
    public User getLoginUser(Connection connection, String username) throws SQLException, Exception;

    //增加用户信息
    public int add(Connection connection, User user)throws Exception;
}
