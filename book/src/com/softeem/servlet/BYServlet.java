package com.softeem.servlet;

import com.softeem.utils.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "BYServlet", value = "/BYServlet")
public class BYServlet extends BaseServlet {

    protected void mytest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("我是BYServlet.mytest方法执行完成....");

//        try {
            int i = 10 / 0;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }


}
