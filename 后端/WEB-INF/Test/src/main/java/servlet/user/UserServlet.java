package servlet.user;

import pojo.User;
import service.user.UserService;
import service.user.UserServiceImpl;
import util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method.equals("Register") && method != null) {
            this.Register(req, resp);
        } else if (method.equals("Logout") && method != null) {
            this.Logout(req, resp);
        }
        //添加方法else if
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    //注册
    private void Register(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("add()================");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String level = request.getParameter("level");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setLevel(Integer.parseInt(level));

        UserService userService = new UserServiceImpl();
        if(userService.add(user)){
//            response.sendRedirect(request.getContextPath()+"/jsp/user.do?method=query");
            System.out.println("注册成功================");
        }else{
//            request.getRequestDispatcher("useradd.jsp").forward(request, response);
            System.out.println("注册失败================");
        }
    }

    //注销
    private void Logout(HttpServletRequest req, HttpServletResponse resp)
    {
        System.out.println("已退出，请重新登陆" );
        //清除session
        req.getSession().removeAttribute(Constants.USER_SESSION);
    }
}
