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
 * Servlet implementation class ShowPersonal
 */
@WebServlet("/ShowPersonal")
public class ShowPersonal extends HttpServlet {
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
    public ShowPersonal() {
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

            int userid = Integer.parseInt(request.getSession().getAttribute("userid").toString());

            // ִ�� SQL ��ѯ
            String sql;
            sql = "SELECT partake FROM competitor where userid=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,userid);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                String partake = rs.getString("partake");
                String partake2 = partake.substring(0,partake.length()-1);
                
                sql = "SELECT id,name,description from contest where FIND_IN_SET(id,?);";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,partake2);
                rs = pstmt.executeQuery();

                while(rs.next()){                
                    String id  = rs.getString("id");
                    String name = rs.getString("name");
                    String description = rs.getString("description");

                    out.println("{\"id\":\"" + id +
                    "\",\"name\":\"" + name +
                    "\",\"description\":\"" + description +
                    "\"};");
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