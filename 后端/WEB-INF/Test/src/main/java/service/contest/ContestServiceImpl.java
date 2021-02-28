package service.contest;

import dao.BaseDao;
import dao.contest.ContestDao;
import dao.contest.ContestDaoImpl;
import dao.user.UserDao;
import dao.user.UserDaoImpl;
import pojo.Contest;

import java.sql.Connection;
import java.sql.SQLException;

public class ContestServiceImpl implements ContestService {
    //业务层都会调用dao层.所以我们要引入Dao层（重点）
    //只处理对应业务

    private ContestDao contestDao;
    public ContestServiceImpl(){ contestDao = new ContestDaoImpl(); }

    @Override
    public boolean add(Contest contest) {
        // TODO Auto-generated method stub

        boolean flag = false;
        Connection connection = null;
        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);//开启JDBC事务管理
            int updateRows = contestDao.add(connection,contest);
            connection.commit();
            if(updateRows > 0){
                flag = true;
                System.out.println("add success!");
            }else{
                System.out.println("add failed!");
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                System.out.println("rollback==================");
                connection.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }finally{
            //在service层进行connection连接的关闭
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }
}
