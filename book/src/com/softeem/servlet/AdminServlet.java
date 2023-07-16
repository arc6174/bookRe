package com.softeem.servlet;

import com.softeem.bean.Admin;
import com.softeem.service.AdminService;
import com.softeem.service.impl.AdminServiceImpl;
import com.softeem.utils.BaseServlet;
import com.softeem.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet(name = "AdminServlet", value = "/AdminServlet")
public class AdminServlet extends BaseServlet {
    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Admin admin = new Admin();
        WebUtils.copyParamToBean(parameterMap, admin);
        AdminService adminService = new AdminServiceImpl();
        try {
            Admin flag = adminService.login(admin);
            if (flag == null) {
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                HttpSession session = request.getSession();//会话作用域
                session.setAttribute("admin", flag);//用户登录成功后的个人信息保存到session会话域中
                request.getRequestDispatcher("/pages/manager/manager.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
