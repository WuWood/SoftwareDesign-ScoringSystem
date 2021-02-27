package dao.user;

import dao.BaseDao;
import pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    //持久层只做查询数据库的内容
    @Override
    //得到要登录的用户
    public User getLoginUser(Connection connection, String username) throws Exception{
        //准备三个对象
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;
        //判断是否连接成功
        if(null != connection){
            String sql = "select * from users where username=?";
            Object[] params = {username};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            if(rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return user;
    }

    //增加用户信息
    public int add(Connection connection, User user) throws Exception {
        // TODO Auto-generated method stub
        PreparedStatement pstm = null;
        int updateRows = 0;
        if(null != connection){
            String sql = "insert into users (username,password,level)values(?,?,?)";
            Object[] params = {user.getUsername(),user.getPassword(),user.getLevel()};
            updateRows = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return updateRows;
    }
}
