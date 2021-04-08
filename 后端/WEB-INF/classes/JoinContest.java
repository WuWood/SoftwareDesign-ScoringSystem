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
    // JDBC �����������ݿ� URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/bearcome?useUnicode=true&characterEncoding=UTF-8&userSSL=false&serverTimezone=GMT%2B8";
    
    // ���ݿ���û��������룬��Ҫ�����Լ�������
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
        // ������Ӧ��������
        response.setContentType("text/html;charset=UTF-8");
        try{
            // ע�� JDBC ������
            Class.forName(JDBC_DRIVER);
            
            // ��һ������
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            if(request.getParameter("id") != null)
            {

                int id = Integer.parseInt(request.getParameter("id"));
                HttpSession session = request.getSession();
                int userid = Integer.parseInt(session.getAttribute("userid").toString());
                int level = Integer.parseInt(session.getAttribute("level").toString());

                // ִ�� SQL ��ѯ
                String sql;
                sql = "SELECT * FROM contest where id=?;";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,id);
                rs = pstmt.executeQuery();

                while(rs.next())
                {
                // ͨ���ֶμ���
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

                                out.println(1); //����ɹ�
                            }
                            else out.println(2); //�Ѿ�����
                        }
                    }else out.println(3); //�޷�����
                }
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