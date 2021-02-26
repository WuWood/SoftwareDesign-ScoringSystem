package servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pojo.User;
import util.Constants;
import service.user.UserService;
import service.user.UserServiceImpl;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet{
    //接受用户参数、调用业务层、转发视图
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO 自动生成的方法存根

        System.out.println("正在登陆 ============ " );
        System.out.println("。。。" );
        System.out.println("。。" );
        //获取用户名和密码
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        //调用service方法，进行用户匹配
        UserService userService = new UserServiceImpl();
        User user = userService.login(username,password);
        if(null != user){//登录成功
            System.out.println("登录成功 ============ " );
            //放入session
            req.getSession().setAttribute(Constants.USER_SESSION,user);
        }else{
            System.out.println("登录失败 ============ " );
            //页面跳转（login.jsp）带出提示信息--转发
            req.setAttribute("error", "用户名或密码不正确");
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO 自动生成的方法存根
        doGet(req, resp);
    }
}
