package servlet.contest;

import pojo.Contest;
import pojo.User;
import service.contest.ContestService;
import service.contest.ContestServiceImpl;
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

public class ContestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method.equals("Create") && method != null) {
            this.Create(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    //创建比赛
    private void Create(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("add()================");
//        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
//        String StartTime = request.getParameter("StartTime");
        String EndTime = request.getParameter("EndTime");
        String MaxMembers = request.getParameter("MaxMembers");
//        String CurrentMembers = request.getParameter("CurrentMembers");
//        String UserId = request.getParameter("UserId");

        Contest contest = new Contest();
        contest.setName(name);
        contest.setDescription(description);
        contest.setStartTime(new Date());
        try {
            //24小时制
            contest.setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(EndTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        contest.setMaxMembers(Integer.parseInt(MaxMembers));
        contest.setCurrentMembers(0);
        contest.setUserId(null);

        ContestService contestService = new ContestServiceImpl();
        if(contestService.add(contest)){
            System.out.println("创建比赛成功================");
        }else{
            System.out.println("创建比赛失败================");
        }
    }
}
