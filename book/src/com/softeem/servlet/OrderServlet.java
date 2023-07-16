package com.softeem.servlet;

import com.softeem.bean.Order;
import com.softeem.bean.OrderItem;
import com.softeem.bean.User;
import com.softeem.service.Cart;
import com.softeem.service.OrderItemService;
import com.softeem.service.OrderService;
import com.softeem.service.impl.OrderItemServiceImpl;
import com.softeem.service.impl.OrderServiceImpl;
import com.softeem.utils.BaseServlet;
import com.softeem.utils.Page;
import com.softeem.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "OrderServlet", value = "/OrderServlet")
public class OrderServlet extends BaseServlet {

    protected void itemList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        OrderItemService orderItemService = new OrderItemServiceImpl();
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"),1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"),4);
        String orderId = request.getParameter("orderId");
        try{
            Page<OrderItem> page = orderItemService.page(pageNo,pageSize,orderId);
            page.setUrl("OrderServlet?action=itemList&orderId="+orderId+"&");
            request.setAttribute("page",page);
            request.getRequestDispatcher("/pages/manager/item.jsp").forward(request,response);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    protected void listOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        OrderService orderService = new OrderServiceImpl();
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"),1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"),4);
        try{
            Page<Order> page = orderService.page(pageNo,pageSize);
            page.setUrl("OrderServlet?action=listOrder&");
            request.setAttribute("page",page);
            request.getRequestDispatcher("/pages/order/order.jsp").forward(request,response);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //生成订单
    protected void createOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Cart cart = (Cart)session.getAttribute("cart");
        User user = (User)session.getAttribute("user");
        if(user==null)
        {
            request.setAttribute("msg","登陆超时请重新登陆");
            request.getRequestDispatcher("/pages/user/login.jsp").forward(request,response);
        }
        OrderService orderService = new OrderServiceImpl();
        try{
            //生成订单并且返回订单号
            String orderId = orderService.createOrder(cart, user.getId());
//            request.setAttribute("orderId",orderId);
//            request.getRequestDispatcher("/pages/cart/checkout.jsp").forward(request,response);
            //为什么选择重定向跳转，而没有选择服务器转发？
            //为了防止表单重复提交
            session.setAttribute("orderId",orderId);
            response.sendRedirect(request.getContextPath()+"/pages/cart/checkout.jsp");
            //重定向跳转前面不要加/
            //因为它会变成http://localhost:8080/pages/cart/checkout.jsp
//            response.sendRedirect("pages/cart/checkout.jsp");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
