package com.softeem.servlet;

import com.softeem.bean.User;
import com.softeem.service.UserService;
import com.softeem.service.impl.UserServiceImpl;
import com.softeem.utils.BaseServlet;
import com.softeem.utils.CookieUtils;
import com.softeem.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

@WebServlet(name = "UserServlet", value = "/UserServlet")
public class UserServlet extends BaseServlet {

    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        WebUtils.copyParamToBean(parameterMap, user);
        UserService userService = new UserServiceImpl();

        try {
            User flag = userService.login(user);
            if (flag == null) {
                request.setAttribute("msg", "账号名或密码不正确");
                request.setAttribute("username", user.getUsername());
                request.setAttribute("password", user.getPassword());
                request.getRequestDispatcher("/pages/user/login.jsp").forward(request, response);
            } else {
                Cookie nameCookie = new Cookie("username", flag.getUsername());
                Cookie passCookie = new Cookie("password", flag.getPassword());
                nameCookie.setMaxAge(60 * 60 * 24 * 7);
                passCookie.setMaxAge(60 * 60 * 24 * 7);
                response.addCookie(nameCookie);
                response.addCookie(passCookie);
                HttpSession session = request.getSession();//会话作用域
                session.setAttribute("user", flag);//用户登录成功后的个人信息保存到session会话域中
                request.setAttribute("msg", "欢迎回来！");
                if (request.getParameter("BYUrl") != null && !request.getParameter("BYUrl").equals("")) {
                    request.getRequestDispatcher(request.getParameter("BYUrl")).forward(request, response);
                } else {
                    request.getRequestDispatcher("/pages/user/success.jsp").forward(request, response);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        // 获取Session 中的验证码
        String token = (String) session.getAttribute(KAPTCHA_SESSION_KEY);
        // 删除 Session 中的验证码
        session.removeAttribute(KAPTCHA_SESSION_KEY);

        String code = request.getParameter("code");//验证码
        System.out.println("用户提交的验证码 = " + code);
        System.out.println("session中的验证码 = " + token);

        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        WebUtils.copyParamToBean(parameterMap, user);

        request.setAttribute("u", user);//这儿是为了回显
        UserService userService = new UserServiceImpl();
        try {
            if (token.equalsIgnoreCase(code)) {
                if (!userService.existsUsername(user.getUsername())) {
                    userService.registUser(user);
                    session.setAttribute("user", user);
                    request.setAttribute("msg", "注册成功!");
                    request.getRequestDispatcher("/pages/user/success.jsp").forward(request, response);
                } else {
                    request.setAttribute("msg", "用户名已存在请更换");
                    request.getRequestDispatcher("/pages/user/regist.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("msg", "验证码不正确");
                request.getRequestDispatcher("/pages/user/regist.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	1、销毁Session 中用户登录的信息（或者销毁Session）
        HttpSession session = request.getSession();
        session.invalidate();
        Cookie nameCookie = CookieUtils.findCookie("username",request.getCookies());
        Cookie passCookie = CookieUtils.findCookie("password",request.getCookies());
        if(nameCookie != null){
            nameCookie.setMaxAge(0);
            response.addCookie(nameCookie);
        }
        if(passCookie != null){
            passCookie.setMaxAge(0);
            response.addCookie(passCookie);
        }
//	2、重定向到首页（或登录页面）。
        response.sendRedirect("index.jsp");
    }
}

