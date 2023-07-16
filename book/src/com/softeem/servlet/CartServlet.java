package com.softeem.servlet;

import com.softeem.bean.Book;
import com.softeem.bean.CartItem;
import com.softeem.service.BookService;
import com.softeem.service.Cart;
import com.softeem.service.impl.BookServiceImpl;
import com.softeem.utils.BaseServlet;
import com.softeem.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CartServlet", value = "/CartServlet")
public class CartServlet extends BaseServlet {
    /**
     * 加入购物车
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void addItem(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        // 获取请求的参数 商品编号
        HttpSession session = request.getSession();
        BookService bookService = new BookServiceImpl();
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        // 调用bookService.queryBookById(id):Book 得到图书的信息
        Book book = bookService.queryBookById(id);
        // 把图书信息，转换成为CartItem 商品项
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());
        // 从session域中取出cart,如果null则表达式无商品信息,否则有
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        session.setAttribute("lastName",book.getName());
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
            System.out.println(" name= " + book.getName());
        }
        cart.addItem(cartItem);
//        System.out.println(cart);
        System.out.println("请求头 Referer 的值：" + request.getHeader("Referer"));

// 重定向回原来商品所在的地址页面
        response.sendRedirect(request.getHeader("Referer"));
    }

    /**
     * 删除商品项
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        System.out.println("id = " + id);
        HttpSession session = request.getSession();
        //获取购物车对象
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            cart.deleteItem(id);
            // 重定向回原来购物车展示页面
            response.sendRedirect(request.getHeader("Referer"));
        }

    }

    /**
     * 清空购物车
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1 获取购物车对象
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart != null) {
            // 清空购物车
            cart.clear();
            // 重定向回原来购物车展示页面
            response.sendRedirect(request.getHeader("Referer"));
        }
    }

    protected void updateCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        int count = WebUtils.parseInt(request.getParameter("count"), 1);
        System.out.println("count = " + count);
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            cart.updateCount(id,count);
        }
        response.sendRedirect(request.getHeader("Referer"));
    }
}
