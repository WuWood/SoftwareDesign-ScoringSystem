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
 * Servlet implementation class JoinContest
 */
@WebServlet("/JoinContest")
public class JoinContest extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/bearcome?useUnicode=true&characterEncoding=UTF-8&userSSL=false&serverTimezone=GMT%2B8";
    
    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = ""; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JoinContest() {
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
        PrintWriter out = response.getWriter();
        // 设置响应内容类型
        response.setContentType("text/html;charset=UTF-8");
        try{
            // 注册 JDBC 驱动器
            Class.forName(JDBC_DRIVER);
            
            // 打开一个连接
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            if(request.getParameter("id") != null)
            {

                int id = Integer.parseInt(request.getParameter("id"));
                HttpSession session = request.getSession();
                int userid = Integer.parseInt(session.getAttribute("userid").toString());
                int level = Integer.parseInt(session.getAttribute("level").toString());

                // 执行 SQL 查询
                String sql;
                sql = "SELECT * FROM contest where id=?;";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,id);
                rs = pstmt.executeQuery();

                while(rs.next())
                {
                // 通过字段检索
                    Date time = new Date();
                    Date starttime = rs.getTimestamp("starttime");
                    Date endtime = rs.getTimestamp("endtime");
                    int currentmembers = rs.getInt("currentmembers");
                    int maxmembers = rs.getInt("maxmembers");
                    String contestuser = rs.getString("userid");

                    if(time.getTime() >= starttime.getTime() && time.getTime() <= endtime.getTime() && currentmembers < maxmembers)
                    {
                        sql = "SELECT partake FROM competitor where userid=?;";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1,userid);
                        rs = pstmt.executeQuery();
                        int Flag = 1;
                        while(rs.next())
                        {
                            String [] partake = rs.getString("partake").split(";");
                            for(String name : partake){
                                if(name == "") break;
                                if(Integer.parseInt(name) == id) Flag = 0;
                            }
                            if(Flag == 1)
                            {

                                sql = "UPDATE contest set currentmembers=(currentmembers+1) where id=?;";
                                pstmt = conn.prepareStatement(sql);
                                pstmt.setInt(1,id);
                                pstmt.executeUpdate();
                                
                                sql = "UPDATE contest set userid=? where id=?;";
                                pstmt = conn.prepareStatement(sql);
                                String userid2 = contestuser + Integer.toString(userid)+";";
                                pstmt.setString(1,userid2);
                                pstmt.setInt(2,id);
                                pstmt.executeUpdate();                               

                                sql = "REPLACE competitor set partake=? where userid=?;";
                                pstmt = conn.prepareStatement(sql);
                                String id2 = rs.getString("partake") + Integer.toString(id)+";";
                                pstmt.setString(1,id2);
                                pstmt.setInt(2,userid);
                                pstmt.executeUpdate();

                                out.println(1); //加入成功
                            }
                            else out.println(2); //已经加入
                        }
                    }else out.println(3); //无法加入
                }
            }
            // 完成后关闭
            rs.close();
            pstmt.close();
            conn.close();             
        } catch(SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch(Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 最后是用于关闭资源的块
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