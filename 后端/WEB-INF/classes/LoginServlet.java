import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql:///bearcome?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "";



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        //判断是否登录成功
        boolean IsLogin=false;


        // 设置响应内容类型
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        try{
            // 注册 JDBC 驱动器
            Class.forName(JDBC_DRIVER);
            // 打开一个连接
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            //获取用户名和密码
            String username = request.getParameter("name");
            String password = request.getParameter("password");
            //MD5加密
            String code = MD5Utils.stringToMD5(password);

            // 执行 SQL 查询
            String sql;
            sql = "select * from users where name=?;";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                // 通过字段检索
                if (rs.getString("password").equals(code)) {
                    request.getSession().setAttribute("userid", rs.getString("userid"));
                    request.getSession().setAttribute("level", rs.getString("level"));
                    out.write("登录成功 ============ ");
                    IsLogin=true;
                    break;
                }
            }

            if(IsLogin==false)
            {
                out.write("登录失败 ============ ");
            }

            // 完成后关闭
            System.out.print(request.getSession().getAttribute("userid"));
            System.out.print(request.getSession().getAttribute("level"));
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
