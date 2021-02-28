package dao.contest;

import dao.BaseDao;
import pojo.Contest;
import pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ContestDaoImpl implements ContestDao {
    @Override
    public int add(Connection connection, Contest contest) throws Exception {
        // TODO Auto-generated method stub
        PreparedStatement pstm = null;
        int updateRows = 0;
        if(null != connection){
            String sql = "insert into contest (name,description,starttime,endtime,maxmembers,currentmembers,userid)values(?,?,?,?,?,?,?)";
            Object[] params = {contest.getName(),contest.getDescription(),contest.getStartTime(),contest.getEndTime(),contest.getMaxMembers(),contest.getCurrentMembers(),contest.getUserId()};
            updateRows = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return updateRows;
    }
}
