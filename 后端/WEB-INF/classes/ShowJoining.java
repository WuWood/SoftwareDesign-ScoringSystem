import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Servlet implementation class JoinContest
 */
@WebServlet("/ShowJoining")
public class ShowJoining extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/bearcome?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "qertyiop1a";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowJoining() {
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
        // 设置响应内容类型
        response.setContentType("text/html;charset=UTF-8");
        try{
            // 注册 JDBC 驱动器
            Class.forName(JDBC_DRIVER);

            // 打开一个连接
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
//            int level=1;
//            int userid=1;
//            //获取session
            HttpSession session = request.getSession();
            int level = Integer.parseInt(session.getAttribute("level").toString());
            int userid = Integer.parseInt(session.getAttribute("userid").toString());
//            判断等级为1
            if(level==1)
            {
                // 执行 SQL 查询
                String max;
                max = "SELECT max(id) as id FROM contest;";
                pstmt = conn.prepareStatement(max);
                ResultSet rs = pstmt.executeQuery();
                // 展开结果集数据库
                while (rs.next())
                {
                    // 通过字段检索
                    String maxid = rs.getString("id");
                    int x=0;
                    for (int i=1;i<=Integer.parseInt(maxid);i++)
                    {
                        String getuseridS;
                        getuseridS="SELECT id,userid FROM contest where id=?";
                        pstmt = conn.prepareStatement(getuseridS);
                        pstmt.setInt(1,i);
                        ResultSet guserid = pstmt.executeQuery();
                        while (guserid.next())
                        {
                            String userids=guserid.getString("userid");

                            if(userids==null)
                            {
                                break;

                            }
                            String [] puserid = userids.split(",");
                            for(int a=0;a<puserid.length;a++)
                            {
                                if(userid==Integer.parseInt(puserid[a]))
                                {
                                    x++;
                                    break;
                                }
                            }
                        }
                        guserid.close();
                    }

                    out.println(x);
                }
                // 完成后关闭
                rs.close();
                pstmt.close();
                conn.close();
            }

//            判断等级为2或者3
            if(level==2||level==3)
            {
                // 执行 SQL 查询
                String max;
                max = "SELECT max(id) as id FROM contest;";
                pstmt = conn.prepareStatement(max);
                ResultSet rs = pstmt.executeQuery();
                // 展开结果集数据库
                while (rs.next())
                {
                    // 通过字段检索
                    String maxid = rs.getString("id");
                    int x=0;
                    for (int i=1;i<=Integer.parseInt(maxid);i++)
                    {
                        String getjudgeid;
                        getjudgeid="SELECT id,judgeid FROM contest where id=?";
                        pstmt = conn.prepareStatement(getjudgeid);
                        pstmt.setInt(1,i);
                        ResultSet gjudges = pstmt.executeQuery();
                        while (gjudges.next())
                        {
                            String judgeids=gjudges.getString("judgeid");
                            if(judgeids==null)
                            {
                                break;

                            }
                            String [] pjudgeid = judgeids.split(",");
                            for(int a=0;a<pjudgeid.length;a++)
                            {
                                if(userid==Integer.parseInt(pjudgeid[a]))
                                {
                                    x++;
                                    break;
                                }
                            }
                        }
                        gjudges.close();
                    }

                    out.println(x);
                }
                // 完成后关闭
                rs.close();
                pstmt.close();
                conn.close();
            }
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