package dao.contest;

import pojo.Contest;
import pojo.User;

import java.sql.Connection;

public interface ContestDao {
    //创建比赛信息
    public int add(Connection connection, Contest contest)throws Exception;
}
