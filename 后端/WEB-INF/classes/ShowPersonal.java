import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.*;

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
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/bearcome?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8";
    
    // ���ݿ���û��������룬��Ҫ�����Լ�������
    static final String USER = "root";
    static final String PASS = "qertyiop1a";
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
            int level = Integer.parseInt(request.getSession().getAttribute("level").toString());

            String sql;
            String score = null;
            String rank = null;
            sql = "";

            //list
//            String[] array = new String[2];
            List<String[]> list = new ArrayList<String[]>();

            if(level == 1)
            {
                String SelectIdSql = "SELECT score FROM competitor where userid=?;";
                pstmt = conn.prepareStatement(SelectIdSql);
                pstmt.setInt(1,userid);
                ResultSet SelectIdRs = pstmt.executeQuery();
                while (SelectIdRs.next())
                {
                    String ScoreJson = SelectIdRs.getString("score");
                    Gson gson = new Gson();
                    Type userListType = new TypeToken<ArrayList<String[]>>(){}.getType();
                    list = gson.fromJson(ScoreJson, userListType);
                }
            }

            if(level == 1)
            {
                sql = "SELECT partake FROM competitor where userid=?";
            }
            else if(level == 2)
            {
                sql = "SELECT GROUP_CONCAT(partake,createpartake) AS partake FROM judge where userid=?;";
            }
            // ִ�� SQL ��ѯ
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
                    /*�����ȡƽ���ֺ�����
                    if(level == 1)
                    {
                        String id2 = "$." + id;
                        sql = "SELECT JSON_EXTRACT(`score`," + id2 + ") AS score;";

                        if(rs.first())
                        {
                            while(rs.next())
                            {
                                score = rs.getString("score");
                                rank = rs.getString("rank");
                            }
                        }

                    }*/
                    if(level==1)
                    {
                        for(int i = 0;i < list.size(); i ++){
                            if(Objects.equals(list.get(i)[0], id))
                            {
                                score=list.get(i)[1];
                                rank=list.get(i)[2];
                            }
                        }
                    }

                    if(score != null)
                    {
                        out.println("{\"id\":\"" + id +
                        "\",\"name\":\"" + name +
                        "\",\"description\":\"" + description +
                        "\",\"score\":\"" + score +
                        "\",\"rank\":\"" + rank +
                        "\"};");
                    }
                    else
                    {
                        out.println("{\"id\":\"" + id +
                        "\",\"name\":\"" + name +
                        "\",\"description\":\"" + description +
                        "\"};");
                    }
                }
            }
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