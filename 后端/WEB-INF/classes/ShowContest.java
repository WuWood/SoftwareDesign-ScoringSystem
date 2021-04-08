import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class ShowContest
 */
@WebServlet("/ShowContest")
public class ShowContest extends HttpServlet {
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
    public ShowContest() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        // ������Ӧ��������
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try{
            // ע�� JDBC ������
            Class.forName(JDBC_DRIVER);
            
            // ��һ������
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // ִ�� SQL ��ѯ
            String sql;
            sql = "SELECT * FROM contest;";
            pstmt = conn.prepareStatement(sql);
            //pstmt.setInt(1,1);
            ResultSet rs = pstmt.executeQuery();

            // չ����������ݿ�
            while(rs.next()){
                // ͨ���ֶμ���
                String id  = rs.getString("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String starttime = rs.getString("starttime");
                String endtime = rs.getString("endtime");
                String currentmembers = rs.getString("currentmembers");
                String maxmembers = rs.getString("maxmembers");

                // �������
                out.println("{\"id\":\"" + id +
                "\",\"name\":\"" + name +
                "\",\"description\":\"" + description +
                "\",\"starttime\":\"" + starttime +
                "\",\"endtime\":\"" + endtime +
                "\",\"currentmembers\":\"" + currentmembers +
                "\",\"maxmembers\":\"" + maxmembers +
                "\"};");
            }

            int level = Integer.parseInt(request.getSession().getAttribute("level").toString());
            if(level > 1)
            {
                out.println("isJudge");
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