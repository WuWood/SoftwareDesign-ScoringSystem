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
 * Servlet implementation class ExamineJudges
 */
@WebServlet("/ExamineJudges")
public class ExamineJudges extends HttpServlet {
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
        // ������Ӧ��������
        response.setContentType("text/html;charset=UTF-8");
        try{
            // ע�� JDBC ������
            Class.forName(JDBC_DRIVER);
            // ��һ������
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            if(request.getParameter("action") != null)
            {
                int type = Integer.parseInt(request.getParameter("type"));
                String action = request.getParameter("action");
                String sql;
                int userid = -1;
                String description = "";
                int id = -1;
                String nickname;
                int level = 0;
                String partake;
                String partake2 = "";
                int index = -1;
                HttpSession session = request.getSession();
                if(action.equals("show")) //��ʾ������Ϣ
                {
                    if(Integer.parseInt(session.getAttribute("level").toString()) == 3)
                    {
                        if(type == 2)
                        {
                            userid = Integer.parseInt(session.getAttribute("userid").toString());
                            sql = "SELECT createpartake from judge where userid=?";
                            pstmt = conn.prepareStatement(sql);
                            pstmt.setInt(1,userid);
                            rs = pstmt.executeQuery();
                            while(rs.next())
                            {
                                String createpartake = rs.getString("createpartake");
                                String createpartake2 = createpartake.substring(0,createpartake.length() - 1);
                                sql = "SELECT * from examine where type=2 and FIND_IN_SET(id,?)";
                                pstmt = conn.prepareStatement(sql);
                                pstmt.setString(1,createpartake2);
                                rs = pstmt.executeQuery();
                                while(rs.next())
                                {
                                    userid = rs.getInt("userid");
                                    description = rs.getString("description");
                                    id = rs.getInt("id");
                                    index = rs.getInt("index1");
                                }
                            }
                        }
                        else
                        {
                            sql = "SELECT * from examine where type=1;";
                            pstmt = conn.prepareStatement(sql);
                            rs = pstmt.executeQuery();
                            while(rs.next())
                            {
                                userid = rs.getInt("userid");
                                description = rs.getString("description");
                                index = rs.getInt("index1");
                            }
                        }
                        sql = "SELECT name from users where userid=?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1,userid);
                        rs = pstmt.executeQuery();
                        while(rs.next())
                        {
                            if(id != -1 && userid !=-1 && description != "" && index != -1) //��ֵ�������κ����壬���ƹ�java�ı�����
                            {
                                String name = rs.getString("name");
                                if(type == 2)
                                {
                                    sql = "SELECT name from contest where id=?";
                                    pstmt = conn.prepareStatement(sql);
                                    pstmt.setInt(1,id);
                                    rs = pstmt.executeQuery();
                                    while(rs.next())
                                    {
                                        String contestname = rs.getString("name");
                                        out.println("{\"index\":\"" + index + 
                                        "\",\"userid\":\"" + userid +
                                        "\",\"name\":\"" + name +
                                        "\",\"description\":\"" + description +
                                        "\",\"id\":\"" + id +
                                        "\",\"contestname\":\"" + contestname +
                                        "\"};");
                                    }                
                                }
                                else
                                {
                                    out.println("{\"index\":\"" + index + 
                                    "\",\"userid\":\"" + userid +
                                    "\",\"name\":\"" + name +
                                    "\",\"description\":\"" + description +
                                    "\"};");
                                }
                            }
                        }
                    }
                }
                else if(action.equals("request")) //�û�����
                {
                    description = request.getParameter("description");
                    userid = Integer.parseInt(session.getAttribute("userid").toString());
                    if(userid != -1 && Integer.parseInt(session.getAttribute("level").toString()) == 1)
                    {
                        if(type == 2)
                        {
                            id = Integer.parseInt(request.getParameter("id"));
                            sql = "SELECT * from examine where userid=? and type=? and id=?";
                            pstmt = conn.prepareStatement(sql);
                            pstmt.setInt(1,userid);
                            pstmt.setInt(2,type);
                            pstmt.setInt(3,id);
                        }
                        else
                        {
                            sql = "SELECT * from examine where userid=? and type=?";
                            pstmt = conn.prepareStatement(sql);
                            pstmt.setInt(1,userid);
                            pstmt.setInt(2,type);
                        }
                        rs = pstmt.executeQuery(); 
                        if(!rs.first())
                        {
                            if(type == 2 && level == 2)
                            {
                                sql = "INSERT INTO examine(userid,description,type,id) VALUES(?,?,?,?)";
                                pstmt = conn.prepareStatement(sql);
                                pstmt.setInt(1,userid);
                                pstmt.setString(2,description);
                                pstmt.setInt(3,2);
                                pstmt.setInt(4,id);
                                pstmt.executeUpdate();
                            }
                            else if(type == 1 && level == 1)
                            {
                                sql = "INSERT INTO examine(userid,description,type) VALUES(?,?,?);";
                                pstmt = conn.prepareStatement(sql);
                                pstmt.setInt(1,userid);
                                pstmt.setString(2,description);
                                pstmt.setInt(3,1);
                                pstmt.executeUpdate();
                            }
                            else out.println(5); //�����ύ
                            out.println(1); //�ύ�ɹ�
                        }
                        else out.println(2); //�Ѿ��ύ
                    }
                }
                else if(action.equals("accept")) //�������
                {
                    if(Integer.parseInt(session.getAttribute("level").toString()) == 3)
                    {
                        index = Integer.parseInt(request.getParameter("index"));
                        sql = "SELECT * FROM examine where index1=? and type=?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1,index);
                        pstmt.setInt(2,type);
                        rs = pstmt.executeQuery();
                        while(rs.next())
                        {
                            userid = rs.getInt("userid");
                            String description2 = rs.getString("description");
                            id = rs.getInt("id");
                            if(description2 != "")
                            {
                                String can = request.getParameter("can");
                                if(can.equals("yes"))
                                {
                                    if(type == 1)
                                    {
                                        sql = "UPDATE users SET level=2 where userid=?";
                                        pstmt = conn.prepareStatement(sql);
                                        pstmt.setInt(1,userid);
                                        pstmt.executeUpdate();

                                        sql = "INSERT INTO judge(userid) VALUES(?);";
                                        pstmt = conn.prepareStatement(sql);
                                        pstmt.setInt(1,userid);
                                        pstmt.executeUpdate();
                                    }
                                    else
                                    {
                                        sql = "SELECT partake from judge where userid=?";
                                        pstmt = conn.prepareStatement(sql);
                                        pstmt.setInt(1,userid);
                                        rs = pstmt.executeQuery();
                                        while(rs.next())
                                        {
                                            partake = rs.getString("partake");
                                            partake2 = partake + id + ",";
                                            sql = "UPDATE judge SET partake=? where userid=?";
                                            pstmt = conn.prepareStatement(sql);
                                            pstmt.setString(1,partake2);
                                            pstmt.setInt(2,userid);
                                            pstmt.executeUpdate();
                                        }

                                        sql = "SELECT judgeid from contest where id=?";
                                        pstmt = conn.prepareStatement(sql);
                                        pstmt.setInt(1,id);
                                        pstmt.executeQuery();
                                        while(rs.next())
                                        {
                                            String judgeid = rs.getString("judgeid");
                                            String judgeid2 = judgeid + userid + ",";
                                            sql = "UPDATE contest SET judgeid=? where id=?";
                                            pstmt = conn.prepareStatement(sql);
                                            pstmt.setString(1,judgeid2);
                                            pstmt.setInt(2,id);
                                            pstmt.executeUpdate();
                                        }

                                        String contest = "contest" + id;
                                        sql = "INSERT INTO " + contest + "(userid) VALUES(?)";
                                        pstmt = conn.prepareStatement(sql);
                                        pstmt.setInt(1,userid);
                                        pstmt.executeUpdate();
                                    }
                                    out.println(1); //ͨ�����
                                }
                                else if(can.equals("no"))
                                {
                                    out.println(2); //�ܾ�ͨ��
                                } 
                                sql = "DELETE FROM examine WHERE index1=? and type=?";
                                pstmt = conn.prepareStatement(sql);
                                pstmt.setInt(1,index);
                                pstmt.setInt(2,type);
                                pstmt.executeUpdate();   
                            }
                            else out.println(3); //�Ѿ�������
                        }
                    }
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