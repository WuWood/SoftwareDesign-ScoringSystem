import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Servlet implementation class ExamineJudges
 */
@WebServlet("/ExamineJudges")
public class ExamineJudges extends HttpServlet {
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
    public ExamineJudges() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        PrintWriter out = response.getWriter();
        ResultSet rs = null;
        // 设置响应内容类型
        response.setContentType("text/html;charset=UTF-8");
        try{
            // 注册 JDBC 驱动器
            Class.forName(JDBC_DRIVER);
            // 打开一个连接
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            if(request.getParameter("action") != null)
            {
                String action = request.getParameter("action");
                String sql;
                if(action.equals("show")) //显示申请评委的信息
                {
                    /*if(session.getAttribute("level") == 3){*/
                    sql = "SELECT * from examine;";
                    pstmt = conn.prepareStatement(sql);
                    rs = pstmt.executeQuery();
                    while(rs.next())
                    {
                        int userid = rs.getInt("userid");
                        String description = rs.getString("description");
                        sql = "SELECT name from users where userid=?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1,userid);
                        rs = pstmt.executeQuery();
                        while(rs.next())
                        {
                            String name = rs.getString("name");
                            out.println("{\"userid\":\"" + userid +
                            "\",\"name\":\"" + name +
                            "\",\"description\":\"" + description +
                            "\"};");
                        }
                    }
                }
                else if(action.equals("request")) //用户申请评委
                {
                    String description = request.getParameter("description");
                    /*int userid = session.getAttribute("id");*/
                    int userid = 1;
                    /*if(userid != null && session.getAttribute("level") == 1){*/
                    sql = "SELECT * from examine where userid=?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1,userid);
                    rs = pstmt.executeQuery();
                    if(!rs.first())
                    {
                        sql = "INSERT INTO examine(userid,description) VALUES(?,?);";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1,userid);
                        pstmt.setString(2,description);
                        pstmt.executeUpdate();

                        out.println(1);   
                    }
                    else out.println(2);                 
                }
                else if(action.equals("accept")) //管理员批准评委申请
                {
                    /*if(session.getAttribute("level") == 3){*/
                    int userid = Integer.parseInt(request.getParameter("userid"));
                    sql = "SELECT * FROM examine where userid=?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1,userid);
                    rs = pstmt.executeQuery();
                    while(rs.next())
                    {
                        String description2 = rs.getString("description");
                        if(description2 != "")
                        {
                            String can = request.getParameter("can");
                            if(can.equals("yes"))
                            {
                                sql = "UPDATE users SET level=2 where userid=?";
                                pstmt = conn.prepareStatement(sql);
                                pstmt.setInt(1,userid);
                                pstmt.executeUpdate();

                                out.println(1); //通过审核
                            }
                            else if(can.equals("no"))
                            {
                                out.println(2); //拒绝通过
                            } 
                            sql = "DELETE FROM examine WHERE userid=?";
                            pstmt = conn.prepareStatement(sql);
                            pstmt.setInt(1,userid);
                            pstmt.executeUpdate();   
                        }
                        else out.println(3); //已经被处理
                    }
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