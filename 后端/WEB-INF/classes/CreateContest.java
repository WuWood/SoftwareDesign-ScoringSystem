import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Servlet implementation class CreateContest
 */
@WebServlet("/CreateContest")
public class CreateContest extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // JDBC �����������ݿ� URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/bearcome?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8";
    
    // ���ݿ���û��������룬��Ҫ�����Լ�������
    static final String USER = "root";
    static final String PASS = "";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateContest() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        // ������Ӧ��������
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try{
            // ע�� JDBC ������
            Class.forName(JDBC_DRIVER);
            
            // ��һ������
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            HttpSession session = request.getSession();
            int level = Integer.parseInt(session.getAttribute("level").toString());
            int userid = Integer.parseInt(session.getAttribute("userid").toString());
            if(level >= 2)
            {
                String name = request.getParameter("name");
                String desc = request.getParameter("desc");
                int row;
                Timestamp starttime = Timestamp.valueOf(request.getParameter("starttime"));
                Timestamp endtime = Timestamp.valueOf(request.getParameter("endtime"));
                int max = Integer.parseInt(request.getParameter("max"));
                String userid2 = String.valueOf(userid) + ",";

                if(name != "" && desc != "" && starttime != null && endtime != null && max > 0 && starttime.getTime() < endtime.getTime())
                {
                    String sql = "SELECT name from contest where name=?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, name);
                    rs = pstmt.executeQuery();

                    if (!rs.first())
                    {
                        sql = "INSERT INTO contest(name,description,starttime,endtime,maxmembers,creatorid) VALUES(?,?,?,?,?,?)";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, name);
                        pstmt.setString(2, desc);
                        pstmt.setTimestamp(3, starttime);
                        pstmt.setTimestamp(4, endtime);
                        pstmt.setInt(5, max);
                        pstmt.setString(6, userid2);
                        row = pstmt.executeUpdate();

                        if (row > 0)
                        {
                            sql = "SELECT id from contest where name=?";
                            pstmt = conn.prepareStatement(sql);
                            pstmt.setString(1, name);
                            rs = pstmt.executeQuery();

                            while (rs.next())
                            {
                                int id = rs.getInt("id");
                                String contest = "contest" + String.valueOf(id);
                                sql =
                                        "CREATE TABLE IF NOT EXISTS " + contest +
                                                "(`userid` int(16) ," +
                                                "PRIMARY KEY (`userid`)" +
                                                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 " + "COLLATE=utf8mb4_unicode_ci;";
                                pstmt = conn.prepareStatement(sql);
                                pstmt.executeUpdate();

                                sql = "SELECT createpartake from judge where userid=?";
                                pstmt = conn.prepareStatement(sql);
                                pstmt.setInt(1, userid);
                                rs = pstmt.executeQuery();           
                                while(rs.next())
                                {
                                    String partake = rs.getString("createpartake");
                                    String partake2 = partake + id + ",";
                                    sql = "UPDATE judge set createpartake=? where userid="+Integer.toString(userid);
                                    pstmt = conn.prepareStatement(sql);
                                    pstmt.setString(1, partake2);
                                    row = pstmt.executeUpdate();
                                }

                                sql = "INSERT INTO " + contest +"(userid) VALUES(?)";
                                pstmt = conn.prepareStatement(sql);
                                pstmt.setInt(1, userid);
                                pstmt.executeUpdate();

                                out.println(1); //�����ɹ�
                            }
                        }
                    }else out.print(3); //�����Ѵ���
                }else out.print(4); //��������
            }


            // ��ɺ�ر�
            rs.close();
            pstmt.close();
            conn.close();
        } catch(SQLException se) {
            // ���� JDBC ����
            se.printStackTrace();
        } catch(Exception e) {
            // ���� Class.forName ����
            e.printStackTrace();
        }finally{
            // ��������ڹر���Դ�Ŀ�
            try{
                if(pstmt!=null)
                pstmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null)
                conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
       
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}